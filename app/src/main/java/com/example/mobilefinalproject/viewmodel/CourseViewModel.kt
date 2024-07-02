package com.example.mobilefinalproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilefinalproject.model.CartResponse
import com.example.mobilefinalproject.model.Comment
import com.example.mobilefinalproject.model.CommentRequest
import com.example.mobilefinalproject.model.CommentResponse
import com.example.mobilefinalproject.model.Course
import com.example.mobilefinalproject.model.CourseResponse
import com.example.mobilefinalproject.model.Document
import com.example.mobilefinalproject.model.DocumentResponse
import com.example.mobilefinalproject.model.Lesson
import com.example.mobilefinalproject.model.LessonResponse
import com.example.mobilefinalproject.model.PaymentResponse
import com.example.mobilefinalproject.model.Question

import com.example.mobilefinalproject.model.ReplyCommentRequest
import com.example.mobilefinalproject.model.Teacher
import com.example.mobilefinalproject.model.Test
import com.example.mobilefinalproject.model.TestResponse
import com.example.mobilefinalproject.model.TestState
import com.example.mobilefinalproject.model.Video
import com.example.mobilefinalproject.model.VideoResponse
import com.example.mobilefinalproject.service.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseViewModel : ViewModel() {

    private val _coursesRelatedByCate = MutableStateFlow<List<Course>>(emptyList())
    val coursesRelatedByCate = _coursesRelatedByCate.asStateFlow()

    private val _infoCourse = MutableStateFlow<Course?>(null)
    val infoCourse = _infoCourse.asStateFlow()

    private val _courseOfLesson = MutableStateFlow<Course?>(null)
    val courseOfLesson = _courseOfLesson.asStateFlow()

    private val _lessons = MutableStateFlow<List<Lesson>>(emptyList())
    val lessons = _lessons.asStateFlow()

    private val _lessonsOfCourse = MutableStateFlow<List<Lesson>>(emptyList())
    val lessonsOfCourse = _lessonsOfCourse.asStateFlow()

    private val _documents = MutableStateFlow<List<Document>>(emptyList())
    val documents = _documents.asStateFlow()

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments = _comments.asStateFlow()

    private val _commentByID = MutableStateFlow<Comment?>(null)
    val commentByID = _commentByID.asStateFlow()

    private val _replyComments = MutableStateFlow<List<Comment>>(emptyList())
    val replyComments = _replyComments.asStateFlow()

    private val _allReplyComments = MutableStateFlow<List<Comment>>(emptyList())
    val allReplyComments = _allReplyComments.asStateFlow()



    private val _infoLesson = MutableStateFlow<Lesson?>(null)
    val infoLesson = _infoLesson.asStateFlow()



    private val _coursesOfCart = MutableStateFlow<List<Course>>(emptyList())
    val coursesOfCart = _coursesOfCart.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion = _currentQuestion.asStateFlow()

    private val _test = MutableStateFlow<Test?>(null)
    val test = _test.asStateFlow()

    private val _cartTotal = MutableStateFlow(0.00)
    val cartTotal = _cartTotal.asStateFlow()

    private val _messageDeleteState = MutableStateFlow<String?>(null)
    val messageDeleteState=_messageDeleteState.asStateFlow()

    private val _messageAddState = MutableStateFlow<String?>(null)
    val messageAddState=_messageAddState.asStateFlow()

    private val _isOpenDialog = MutableStateFlow<Boolean>(false)
    val isOpenDialog=_isOpenDialog.asStateFlow()

    private val _isMyCourse = MutableStateFlow(false)
    val isMyCourse=_isMyCourse.asStateFlow()

    private val _isDeleteComment = MutableStateFlow(false)
    val isDeleteComment=_isDeleteComment.asStateFlow()

    private val _isDeleteReplyComment = MutableStateFlow(false)
    val isDeleteReplyComment=_isDeleteReplyComment.asStateFlow()

    private val _isShowComment = MutableStateFlow(false)
    val isShowComment=_isShowComment.asStateFlow()

    private val _isShowReplyComment = MutableStateFlow(false)
    val isShowReplyComment=_isShowReplyComment.asStateFlow()

    private val _idRootComment = MutableStateFlow<Int>(0)
    val idRootComment=_idRootComment.asStateFlow()

    private val _isCompleteLesson = MutableStateFlow<Int>(0)
    val isCompleteLesson = _isCompleteLesson.asStateFlow()

    private val _countOfCart = MutableStateFlow(0)
    val countOfCart = _countOfCart.asStateFlow()

    private val _totalComments = MutableStateFlow(0)
    val totalComments = _totalComments.asStateFlow()


    private val _isMyCart = MutableStateFlow(false)
    val isMyCart=_isMyCart.asStateFlow()

    fun showCommentSelection(){
        _isShowComment.value=true
    }
    fun hideCommentSelection(){
        _isShowComment.value=false
    }
    fun showReplyCommentSelection(){
        _isShowReplyComment.value=true
    }
    fun hideReplyCommentSelection(){
        _isShowReplyComment.value=false
    }
    fun sendIdRootComment(id: Int){
        _idRootComment.value=id
    }
    fun fetchInfoCourseById(id: Int) {
        _infoCourse.value=null
        _lessons.value= emptyList()
        viewModelScope.launch {
            val call: Call<CourseResponse> = ApiService.apiService.getInfoCourse(id)
            call.enqueue(object : Callback<CourseResponse> {
                override fun onResponse(
                    call: Call<CourseResponse>,
                    response: Response<CourseResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        _coursesRelatedByCate.value =
                            courseResponse?.coursesRelatedByCategory ?: emptyList()
                        _lessons.value = courseResponse?.lessons ?: emptyList()
                        _infoCourse.value = courseResponse?.infoCourse!!
                        Log.e("info-course-response", courseResponse.toString())
                        Log.e("info-course-data-log", _infoCourse.value.toString())
                        Log.e(
                            "info-course-related-data-log",
                            _coursesRelatedByCate.value.toString()
                        )
                        Log.e("info-course-lesson-data-log", _lessons.value.toString())


                    } else {
                        Log.e("course-log-error", response.toString())
                    }
                }

                override fun onFailure(call: Call<CourseResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun fetchLesson(lessonID: Int) {
        _infoLesson.value=null
        viewModelScope.launch {
            val call: Call<LessonResponse> = ApiService.apiService.getInfoLesson(lessonID)
            call.enqueue(object : Callback<LessonResponse> {
                override fun onResponse(
                    call: Call<LessonResponse>,
                    response: Response<LessonResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        _infoLesson.value=courseResponse?.lesson!!
                        _courseOfLesson.value=courseResponse.course
                        Log.e("c-response-lesson-data-log", courseResponse.toString())
                    }
                    Log.e("response-lesson-data-log", response.toString())
                    Log.e("info-lesson-data-log", _infoLesson.value.toString())
                }

                override fun onFailure(call: Call<LessonResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }


    fun fetchDocument(lessonID: Int) {
        _documents.value= emptyList()
        viewModelScope.launch {
            val call: Call<DocumentResponse> = ApiService.apiService.getDocuments(lessonID)
            call.enqueue(object : Callback<DocumentResponse> {
                override fun onResponse(
                    call: Call<DocumentResponse>,
                    response: Response<DocumentResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            _documents.value=courseResponse.documents
                        }
                        Log.e("documents-data-log", courseResponse.toString())
                    }
                }

                override fun onFailure(call: Call<DocumentResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun fetchComment(lessonID: Int) {
        _comments.value= emptyList()
        _totalComments.value=0
        viewModelScope.launch {
            val call: Call<VideoResponse> = ApiService.apiService.getComment(lessonID)
            call.enqueue(object : Callback<VideoResponse> {
                override fun onResponse(
                    call: Call<VideoResponse>,
                    response: Response<VideoResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            _comments.value=courseResponse.comments
                            _totalComments.value= courseResponse.totalComments
                        }
                        Log.e("comments-data-log $lessonID", courseResponse.toString())
                    }
                }

                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }
    fun fetchCommentByID(commentID: Int) {
        _commentByID.value= null
        viewModelScope.launch {
            val call: Call<CommentResponse> = ApiService.apiService.getCommentByID(commentID)
            call.enqueue(object : Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>,
                    response: Response<CommentResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            _commentByID.value=courseResponse.comment
                        }
                        Log.e("commentID-data-log", courseResponse.toString())
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    Log.e("API Error comment by id", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun fetchReplyComment(commentID: Int) {
        _replyComments.value= emptyList()
        viewModelScope.launch {
            val call: Call<CommentResponse> = ApiService.apiService.getReplyComment(commentID)
            call.enqueue(object : Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>,
                    response: Response<CommentResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            _replyComments.value=courseResponse.replyComments
                        }
                        Log.e("reply-comments-data-log", _replyComments.value.toString())
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    Log.e("API Error reply cmt ", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun fetchAllReplyComments(lessonID: Int) {
        _allReplyComments.value= emptyList()
        viewModelScope.launch {
            val call: Call<CommentResponse> = ApiService.apiService.getAllReplyComments(lessonID)
            call.enqueue(object : Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>,
                    response: Response<CommentResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            _allReplyComments.value=courseResponse.allReplyComments
                        }
                        Log.e("all-reply-comments-data-log", _allReplyComments.value.toString())
                    }
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    Log.e("API Error reply cmt ", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun deleteComment(lessonID: Int, commentID:Int) {
        _isDeleteComment.value= false
        viewModelScope.launch {
            val call: Call<CommentResponse> = ApiService.apiService.deleteComment(commentID)
            call.enqueue(object : Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>,
                    response: Response<CommentResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            _isDeleteComment.value=true
                            fetchComment(lessonID)
                            Log.e("suc-delete-comments-log", courseResponse.toString())
                        }else{
                            _isDeleteComment.value=false
                        }
                    }
                    Log.e("delete-comments-log", response.toString())
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun deleteReplyComment(replyCommentID: Int, commentID:Int, lessonID: Int) {
        _isDeleteReplyComment.value= false
        viewModelScope.launch {
            val call: Call<CommentResponse> = ApiService.apiService.deleteReplyComment(replyCommentID)
            call.enqueue(object : Callback<CommentResponse> {
                override fun onResponse(
                    call: Call<CommentResponse>,
                    response: Response<CommentResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            _isDeleteReplyComment.value=true
                            fetchComment(lessonID)
                            fetchReplyComment(commentID)
                            Log.e("Comment-id",commentID.toString())
                            Log.e("suc-delete-reply-comments-log", courseResponse.toString())
                        }else{
                            _isDeleteReplyComment.value=false
                        }
                    }
                    Log.e("delete-reply-comments-log", response.toString())
                }

                override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun addComment(
        lessonID: Int,
        userID: Int,
        commentRequest: CommentRequest
    ) {
        viewModelScope.launch {
            val call: Call<Comment> = ApiService.apiService.addComment(lessonID, userID, commentRequest)
            call.enqueue(object : Callback<Comment> {
                override fun onResponse(
                    call: Call<Comment>,
                    response: Response<Comment>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            Log.e("add-comments-data-log", courseResponse.toString())
                        }
                        fetchComment(lessonID)
                        Log.e("comments-data-log", courseResponse.toString())
                    }
                }

                override fun onFailure(call: Call<Comment>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun addReplyComment(
        commentID: Int,
        userID: Int,
        replyCommentRequest: ReplyCommentRequest,
        lessonID: Int
    ) {
        viewModelScope.launch {
            val call: Call<Comment> = ApiService.apiService.addReplyComment(commentID, userID, replyCommentRequest)
            call.enqueue(object : Callback<Comment> {
                override fun onResponse(
                    call: Call<Comment>,
                    response: Response<Comment>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            Log.e("add-reply-comments-data-log", courseResponse.toString())
                        }
                        fetchReplyComment(commentID)
                        fetchComment(lessonID)
                        fetchAllReplyComments(lessonID)
                        Log.e("comments-reply-data-log", courseResponse.toString())
                    }
                }

                override fun onFailure(call: Call<Comment>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    private val _isPaymentSuccess = MutableStateFlow<Boolean>(false)
    val isPaymentSuccess = _isPaymentSuccess.asStateFlow()

    private val _orderID = MutableStateFlow<Int?>(null)
    val orderID = _orderID.asStateFlow()

    fun payment(userID: Int,courseID: Int){
        _isPaymentSuccess.value=false
        viewModelScope.launch {
            try {
                ApiService.apiService.payment(userID,courseID).enqueue(object :
                    Callback<PaymentResponse> {
                    override fun onResponse(
                        call: Call<PaymentResponse>,
                        response: Response<PaymentResponse>
                    ) {
                        if (response.isSuccessful) {
                            val courseResponse = response.body()
                            if (courseResponse != null) {
                                _isPaymentSuccess.value=true
                                _orderID.value=courseResponse.orderID
                            }

                            Log.e("Payment", courseResponse.toString())
                        } else {
                            _isPaymentSuccess.value=false
                            Log.e("API Error Payment", "Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                        Log.d("Payment-error", "Payment", t)

                    }
                })
            } catch (e: Exception) {
                Log.d("Logging", "Payment Error", e)

            }
        }
    }

    fun deleteDialogPayment(){
        _isPaymentSuccess.value=false
    }


    fun updateStatus(lessonID: Int){
        _isOpenDialog.value=false
        viewModelScope.launch {
            val call: Call<Lesson> = ApiService.apiService.updateStatus(lessonID)
            call.enqueue(object : Callback<Lesson> {
                override fun onResponse(call: Call<Lesson>, response: Response<Lesson>) {
                   fetchLesson(lessonID)
                }

                override fun onFailure(call: Call<Lesson>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }

            })
        }
    }

    fun fetchCart(userID:Int){
        _coursesOfCart.value= emptyList()
        viewModelScope.launch {
            val call: Call<CartResponse> = ApiService.apiService.getCart(userID)
            call.enqueue(object : Callback<CartResponse> {
                override fun onResponse(
                    call: Call<CartResponse>,
                    response: Response<CartResponse>
                ) {
                    if (response.isSuccessful) {
                        val cartResponse = response.body()
                        if (cartResponse != null) {
                            _coursesOfCart.value=cartResponse.courses
                            _cartTotal.value=cartResponse.total
                        }
                        Log.e("cart-response", cartResponse.toString())
                        Log.e(
                            "info-courseCart-data-log",
                            _infoLesson.value.toString()
                        )
                    } else {
                        Log.e("lesson-log-error", response.toString())
                    }
                }

                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun isMyCart(userID: Int,courseID: Int){
        viewModelScope.launch {
            try {
                ApiService.apiService.isMyCart(userID,courseID).enqueue(object :
                    Callback<CourseResponse> {
                    override fun onResponse(
                        call: Call<CourseResponse>,
                        response: Response<CourseResponse>
                    ) {
                        if (response.isSuccessful) {
                            val courseResponse = response.body()
                            if (courseResponse != null) {
                                _isMyCart.value=courseResponse.isMyCart
                            }
                            Log.e("Is My Cart", _isMyCourse.value.toString())
                            Log.e("Response Is My Cart", courseResponse.toString())
                        } else {
                            Log.e("API Error Is My Cart", "Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<CourseResponse>, t: Throwable) {
                        Log.d("is-my-cart-error", "My Cart Error", t)

                    }
                })
            } catch (e: Exception) {
                Log.d("Logging", "Is My Cart Error", e)

            }
        }
    }

    fun getCountCart(userID:Int){
        _coursesOfCart.value= emptyList()
        viewModelScope.launch {
            val call: Call<CartResponse> = ApiService.apiService.getCountOfCart(userID)
            call.enqueue(object : Callback<CartResponse> {
                override fun onResponse(
                    call: Call<CartResponse>,
                    response: Response<CartResponse>
                ) {
                    if (response.isSuccessful) {
                        val cartResponse = response.body()
                        if (cartResponse != null) {
                            _countOfCart.value=cartResponse.count
                        }
                        Log.e("count-cart-response", cartResponse.toString())

                    } else {
                        Log.e("error-count-cart-response", response.toString())
                    }
                }

                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun deleteCart(userID:Int, courseID:Int){
        _isOpenDialog.value=false
        viewModelScope.launch {
            val call: Call<CartResponse> = ApiService.apiService.deleteCart(userID, courseID)
            call.enqueue(object : Callback<CartResponse> {
                override fun onResponse(
                    call: Call<CartResponse>,
                    response: Response<CartResponse>
                ) {
                    if (response.isSuccessful) {
                        val cartResponse = response.body()
                        Log.e("cart-response", cartResponse.toString())
                        if (cartResponse != null) {
                            _messageDeleteState.value=cartResponse.messageRemove
                            fetchCart(userID)
                            getCountCart(userID)
                            _isOpenDialog.value=true
                        }
                        Log.e("messageDeleteState", messageDeleteState.toString())
                    } else {
                        _isOpenDialog.value=true
                        Log.e("delete-cart-log-error", response.toString())
                    }
                }

                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun deleteDialog(){
        _isOpenDialog.value=false
    }

    fun addToCart(
        userID:Int,
        courseID:Int,

    ){
        _isOpenDialog.value=false
        viewModelScope.launch {

            val call: Call<CartResponse> = ApiService.apiService.addToCart(userID, courseID)
            call.enqueue(object : Callback<CartResponse> {
                override fun onResponse(
                    call: Call<CartResponse>,
                    response: Response<CartResponse>
                ) {
                    if (response.isSuccessful) {
                        val cartResponse = response.body()
                        Log.e("cart-response", cartResponse.toString())
                        if (cartResponse != null) {
                            _messageAddState.value=cartResponse.messageAddToCart
                            fetchCart(userID)
                            getCountCart(userID)
                            _isOpenDialog.value=true
                        }
                        Log.e("messageAddState", messageDeleteState.toString())
                    } else {
                        _isOpenDialog.value=true
                        Log.e("add-to-cart-log-error", response.toString())
                    }
                }

                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }
    }

    fun isMyCourse(userID: Int,courseID: Int){
        viewModelScope.launch {
            try {
                ApiService.apiService.isMyCourses(userID,courseID).enqueue(object :
                    Callback<CourseResponse> {
                    override fun onResponse(
                        call: Call<CourseResponse>,
                        response: Response<CourseResponse>
                    ) {
                        if (response.isSuccessful) {
                            val courseResponse = response.body()
                            if (courseResponse != null) {
                                _isMyCourse.value=courseResponse.isMyCourse
                            }
                            Log.e("Is My Course", _isMyCourse.value.toString())
                            Log.e("Response Is My Course", courseResponse.toString())
                        } else {
                            Log.e("API Error Is My Course", "Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<CourseResponse>, t: Throwable) {
                        Log.d("is-my-courses-error", "My Courses Error", t)

                    }
                })
            } catch (e: Exception) {
                Log.d("Logging", "Is My Courses Error", e)

            }
        }
    }


    fun fetchTest(lessonID: Int) {
        viewModelScope.launch {
            val call: Call<TestResponse> = ApiService.apiService.getTest(lessonID)
            call.enqueue(object : Callback<TestResponse> {
                override fun onResponse(
                    call: Call<TestResponse>,
                    response: Response<TestResponse>
                ) {
                    if (response.isSuccessful) {
                        val courseResponse = response.body()
                        if (courseResponse != null) {
                            _test.value=courseResponse.test
                            _questions.value=courseResponse.questions

                        }
                        Log.e("lesson-test-response", courseResponse.toString())
                        Log.e(
                            "info-test-data-log",
                            _test.value.toString()
                        )
                        Log.e("test-questions", _questions.value.toString())

                    } else {
                        Log.e("lesson-test-log-error", response.toString())
                    }
                }

                override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                    Log.e("API Error", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
        }

    }


    private val _testStates = MutableStateFlow<Map<String, TestState>>(emptyMap())
    val testStates = _testStates.asStateFlow()

    fun updateCurrentQuestionIndex(testId: String, index: Int) {
        _testStates.value = _testStates.value.toMutableMap().apply {
            val testState = this[testId] ?: TestState()
            this[testId] = testState.copy(currentQuestionIndex = index)
        }
    }

    fun updateProgress(testId: String, progress: Float) {
        _testStates.value = _testStates.value.toMutableMap().apply {
            val testState = this[testId] ?: TestState()
            this[testId] = testState.copy(progress = progress)
        }
    }

    fun updateSubmitted(testId: String) {
        _testStates.value = _testStates.value.toMutableMap().apply {
            val testState = this[testId] ?: TestState()
            this[testId] = testState.copy(isSubmitted = true)
        }
    }
    fun updateEnabled(testId: String) {
        _testStates.value = _testStates.value.toMutableMap().apply {
            val testState = this[testId] ?: TestState()
            this[testId] = testState.copy(isEnabled = false)
        }
    }

    fun updateSelectedAnswers(testId: String, index: Int, answer: String) {
        _testStates.value = _testStates.value.toMutableMap().apply {
            val testState = this[testId] ?: TestState()
            val updatedAnswers = testState.selectedAnswers.toMutableMap().apply {
                put(index, answer)
            }
            this[testId] = testState.copy(
                selectedAnswers = updatedAnswers,
                progress = updatedAnswers.size / _questions.value.size.toFloat()
            )
        }
    }

    private val _selectedItem = MutableStateFlow(0)
    val selectedItem = _selectedItem.asStateFlow()

    fun updateSelectedItem(index:Int){
        _selectedItem.value=index
    }

}