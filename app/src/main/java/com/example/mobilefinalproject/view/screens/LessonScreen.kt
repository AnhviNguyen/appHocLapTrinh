package com.example.mobilefinalproject.view.screens



import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.mobilefinalproject.data.AndroidDownloader
import com.example.mobilefinalproject.config.RecentVideosManager
import com.example.mobilefinalproject.config.TimeAgo
import com.example.mobilefinalproject.model.Comment
import com.example.mobilefinalproject.model.CommentRequest
import com.example.mobilefinalproject.model.Document
import com.example.mobilefinalproject.model.Lesson
import com.example.mobilefinalproject.model.ReplyCommentRequest
import com.example.mobilefinalproject.navigation.NavigationItem
import com.example.mobilefinalproject.view.components.CircularProgressIndicatorEx
import com.example.mobilefinalproject.view.components.MyButton

import com.example.mobilefinalproject.viewmodel.CourseViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LessonDetailScreen(
    lessonID: Int,
    userID: Int,
    courseViewModel: CourseViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val lesson by courseViewModel.infoLesson.collectAsState()
    val documents by courseViewModel.documents.collectAsState()
    val comments by courseViewModel.comments.collectAsState()

    var newCommentText by remember {
        mutableStateOf("")
    }
    val showComments by courseViewModel.isShowComment.collectAsState()
    val showReplyComments by courseViewModel.isShowReplyComment.collectAsState()
    val totalComments by courseViewModel.totalComments.collectAsState()
    val lifecycleOwner= LocalLifecycleOwner.current

    LaunchedEffect(key1 = lessonID) {
        courseViewModel.fetchLesson(lessonID)
        courseViewModel.fetchComment(lessonID)
        courseViewModel.fetchDocument(lessonID)
        courseViewModel.hideCommentSelection()
        courseViewModel.hideReplyCommentSelection()
       // lesson?.let { courseViewModel.fetchInfoCourseById(it.course_id) }


    }
    val commentByID by courseViewModel.commentByID.collectAsState()
    val course by courseViewModel.courseOfLesson.collectAsState()
    val lessons = course?.lessons
    val lessonsOther = lessons?.filter { it.id!=lessonID }


    Column {
        if (lesson==null){
            CircularProgressIndicatorEx()
        }else{
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth(),
                factory = { context ->
                    YouTubePlayerView(context = context).apply {
                        lifecycleOwner.lifecycle.addObserver(this)

                        val youTubePlayerListener = object : AbstractYouTubePlayerListener() {

                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                val videoId = lesson?.extractVideoId(lesson!!.url)
                                if (videoId != null) {
                                    val sharedPreferences = context.getSharedPreferences("video_prefs", Context.MODE_PRIVATE)
                                    val lastPosition = sharedPreferences.getFloat(lessonID.toString(), 0f)
                                    youTubePlayer.loadVideo(videoId, lastPosition)
                                    val recentVideosManager = RecentVideosManager(context)
                                    recentVideosManager.addRecentLessonForUser(userID.toString(),
                                        lesson!!
                                    )
                                }

                            }

                            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                                val sharedPreferences = context.getSharedPreferences("video_prefs", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putFloat(lessonID.toString(), second)
                                editor.apply()
                            }
                        }
                        addYouTubePlayerListener(youTubePlayerListener)
                    }
                }
            )


            // Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(
                visible = showComments,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeOut(animationSpec = tween(durationMillis = 300))
            ) {
                CommentSelection(courseViewModel = courseViewModel, lessonID = lessonID, userID = userID)
            }

            AnimatedVisibility(
                visible = !showComments && !showReplyComments,
                enter = fadeIn(),
                exit = fadeOut()
            ) {

                lesson?.let {
                    lesson?.let {
                        if (lessonsOther != null) {
                            LessonContent(
                                lesson = it,
                                documents = documents, navController = navController,
                                lessonsOther,
                                comments,
                                courseViewModel ,
                                totalComments
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = showReplyComments,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeOut(animationSpec = tween(durationMillis = 300))
            ) {

                commentByID?.let {
                    CommentReplySelection(
                        courseViewModel = courseViewModel,
                        lessonID = lessonID,
                        comment = it,
                        userID = userID
                    )
                }

            }




        }

    }


}

@Composable
fun LessonContent(
    lesson: Lesson,
    documents:List<Document>,
    navController: NavController,
    lessons:List<Lesson>,
    comments:List<Comment>,
    courseViewModel: CourseViewModel,
    totalComments:Int
){

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp
            )
    ) {
        item {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = lesson.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight(700),
                    letterSpacing = 0.3.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = lesson.description,
                    style = MaterialTheme.typography.bodyMedium,
                    letterSpacing = 0.3.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow {
                    items(documents){doc->
                        DocumentDownloadCard(doc)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                MyButton(
                    text = "TEST NOW",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    onClick = {
                        navController.navigate("${NavigationItem.Test.route}/${lesson.id}")
                    },

                    )
                Spacer(modifier = Modifier.height(10.dp))
                Column (
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            courseViewModel.showCommentSelection()
                        }
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp,16.dp,16.dp,8.dp)
                    ) {
                        Text(
                            text = "Comment",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        if (totalComments!=0){
                            Text(
                                text = totalComments.toString(),
                                style = MaterialTheme.typography.titleSmall,

                                )
                        }

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp, 8.dp, 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = "",
                            onValueChange = {  },
                            modifier = Modifier
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.background),
                            placeholder = { Text("Add a comment") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Send
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = {

                                }
                            )
                        )
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_send),
                                contentDescription = "Send Comment"
                            )
                        }
                    }

                }

            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        items(lessons){lesson->
            Spacer(modifier = Modifier.height(16.dp))
            CardLesson(lesson = lesson) {
                navController.navigate("${NavigationItem.Lesson.route}/${lesson.id}")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Composable
fun CardLesson(
    lesson: Lesson,
    onItemClick: () -> Unit,
){
    val painter = rememberImagePainter(
        data = lesson.img
        )
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = onItemClick),
    ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = lesson.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(4.dp)
            )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentSelection(
    courseViewModel: CourseViewModel,
    lessonID: Int,
    userID: Int
) {
    val comments by courseViewModel.comments.collectAsState()
    var newCommentText by remember {
        mutableStateOf("")
    }
    Column (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Comment",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            courseViewModel.hideCommentSelection()
                        }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
        Divider()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(comments){ comment->
                CommentItem(comment,userID,lessonID,courseViewModel, isShowFeedback = true)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = newCommentText,
                onValueChange = { newCommentText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Add a comment") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (newCommentText.isNotEmpty()) {
                            courseViewModel.addComment(
                                lessonID,
                                userID,
                                CommentRequest(newCommentText,lessonID, userID)
                            )
                            newCommentText = ""
                        }
                    }
                )
            )
            IconButton(onClick = {
                if (newCommentText.isNotEmpty()) {
                    courseViewModel.addComment(
                        lessonID,
                        userID,
                        CommentRequest(newCommentText,lessonID, userID)
                    )

                    newCommentText = ""
                }
            }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_send),
                    contentDescription = "Send Comment"
                )
            }
        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentItem(
    comment: Comment,
    userID: Int,
    lessonID: Int,
    courseViewModel: CourseViewModel,
    isShowReply:Boolean = true,
    isShowFeedback:Boolean=false,
    isReplyComment:Boolean=false,
    rootComment:Comment?=null
) {
    val isDeleteSuccess by courseViewModel.isDeleteComment.collectAsState()
    var showToast by remember { mutableStateOf(false) }
    val painterReply = rememberAsyncImagePainter(
        model = "https://static-00.iconduck.com/assets.00/reply-icon-256x223-ows50x3e.png"
    )
    val painter = rememberAsyncImagePainter(
        model = "comment.user.img"
    )
    val painterError = rememberAsyncImagePainter(
        model = "https://cdn.sforum.vn/sforum/wp-content/uploads/2023/10/avatar-trang-4.jpg"
    )
    val replyComments = comment.replyComments
    var isConfirmDelete by remember {
        mutableStateOf(false)
    }

    val showReplyComments by courseViewModel.isShowReplyComment.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            if (painter.state is AsyncImagePainter.State.Error) {
                Image(
                    painter = painterError,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)

                )
            } else {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)

                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = comment.user.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TimeAgoText(dateTimeString = comment.time)
                    Spacer(modifier = Modifier.width(24.dp))
                    if (isShowReply){
                        Image(
                            painter = painterResource(id = com.example.mobilefinalproject.R.drawable.reply_cmt ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    courseViewModel.fetchCommentByID(comment.id)
                                    courseViewModel.hideCommentSelection()
                                    courseViewModel.showReplyCommentSelection()
                                    //                              courseViewModel.sendIdRootComment(comment.id)

                                    Log.e("send id cmt:", comment.id.toString())
                                }
                        )
                    }


                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = comment.content,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (isShowFeedback){
                    if (replyComments.isNotEmpty()){
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = replyComments.size.toString() + " feedback",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable {
                                courseViewModel.fetchCommentByID(comment.id)
                                courseViewModel.hideCommentSelection()
                                courseViewModel.showReplyCommentSelection()
                            }
                        )
                    }
                }






            }
        }
        Log.e("Comment-user-id ${comment.content}",comment.user.id.toString())
        Log.e("cmt-user-id",userID.toString())
        if (comment.user.id==userID){
            Column {
                Icon(
                    imageVector = Icons.Default.Delete ,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error ,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            isConfirmDelete = true
                        }
                )
            }
        }
        
        if (isConfirmDelete){
            AlertDialog(
                onDismissRequest = {
                    isConfirmDelete=false
                },
                title = {
                    Text(text = "Confirm deletion")
                },
                text = {
                    Text("Are you sure you want to delete this comment?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (isReplyComment){
                                if (rootComment != null) {
                                    Log.e("rootCmt",rootComment.toString())
                                    courseViewModel.deleteReplyComment(comment.id,rootComment.id,lessonID)
                                    showToast = true
                                }
                            }else{
                                courseViewModel.deleteComment(lessonID,comment.id)
                                showToast = true
                            }

                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            isConfirmDelete=false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )

        }
        val context = LocalContext.current
        if (showToast) {
            LaunchedEffect(Unit) {
                delay(200)
                Toast.makeText(
                    context,
                    "Deleted this comment successfully!",
                    Toast.LENGTH_LONG
                ).show()

                showToast = false
            }
        }



    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentReplySelection(
    courseViewModel: CourseViewModel,
    lessonID: Int,
    comment: Comment,
    userID: Int
) {
    val replyComments by courseViewModel.replyComments.collectAsState()
    var newCommentText by remember {
        mutableStateOf("")
    }
    Log.e("replyComments",replyComments.toString())
    LaunchedEffect(key1 = Unit) {
        courseViewModel.fetchReplyComment(comment.id)
    }
    Column (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Reply",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            courseViewModel.hideReplyCommentSelection()
                            courseViewModel.showCommentSelection()
                        }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
        Divider()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                CommentItem(comment = comment, userID = userID, lessonID = lessonID , courseViewModel = courseViewModel , isShowReply = false)
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
            }
            items(replyComments){commentItem->
                Column (
                    modifier = Modifier.padding(start = 50.dp)
                ) {
                    CommentItem(
                        comment = commentItem ,
                        userID = commentItem.user.id ,
                        lessonID = lessonID ,
                        courseViewModel = courseViewModel ,
                        isShowReply = false ,
                        rootComment = comment,
                        isReplyComment = true)
                }

            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = newCommentText,
                onValueChange = { newCommentText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Reply ...") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (newCommentText.isNotEmpty()) {
                            courseViewModel.addReplyComment(
                                comment.id,
                                userID,
                                ReplyCommentRequest(newCommentText,comment.id,userID),
                                lessonID
                            )
                            newCommentText = ""
                        }
                    }
                )
            )
            IconButton(onClick = {
                if (newCommentText.isNotEmpty()) {
                    courseViewModel.addReplyComment(
                        comment.id,
                        userID,
                        ReplyCommentRequest(newCommentText,comment.id,userID),
                        lessonID
                    )
                    newCommentText = ""
                }
            }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_send),
                    contentDescription = "Send Comment"
                )
            }
        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@Composable
fun TimeAgoText(dateTimeString: String) {
    val timeAgoString = remember { TimeAgo(dateTimeString) }

    Text(
        text = timeAgoString,
        style = MaterialTheme.typography.bodyMedium,

    )
}



@Composable
fun DocumentDownloadCard(
    document: Document
) {

    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .clickable {
                Toast
                    .makeText(
                        context,
                        "Document is being downloaded.",
                        Toast.LENGTH_LONG
                    )
                    .show()
                val downloader = AndroidDownloader(context)
                downloader.DownloadFile(document.url)
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp),


        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.Build,
                contentDescription = "Download icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = document.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${document.size} MB",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}









