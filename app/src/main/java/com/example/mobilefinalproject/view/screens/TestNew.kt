//package com.example.mobilefinalproject.view.screens
//
//import android.annotation.SuppressLint
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.selection.selectable
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.LinearProgressIndicator
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.RadioButtonDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.mobilefinalproject.model.Question
//import com.example.mobilefinalproject.model.TestState
//import com.example.mobilefinalproject.navigation.NavigationItem
//import com.example.mobilefinalproject.view.components.CircularProgressIndicatorEx
//import com.example.mobilefinalproject.viewmodel.CourseViewModel
//
//@Composable
//fun MultipleChoiceTestScreen(
//    navController: NavController,
//    courseViewModel: CourseViewModel,
//    lessonID: Int,
//) {
//    val test by courseViewModel.test.collectAsState()
//    val questions by courseViewModel.questions.collectAsState()
//    LaunchedEffect(key1 = Unit) {
//        courseViewModel.fetchTest(lessonID)
//    }
//    Log.e("questions", questions.toString())
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        if (questions.isEmpty()) {
//            CircularProgressIndicatorEx()
//        } else {
//            test?.let {
//                QuestionsScreen(
//                    testID = it.id,
//                    lessonID,
//                    questions = questions,
//                    navController,
//                    courseViewModel
//                )
//            }
//        }
//
//
//    }
//}
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun QuestionsScreen(
//    testID: Int,
//    lessonID: Int,
//    questions: List<Question>,
//    navController: NavController,
//    courseViewModel: CourseViewModel
//) {
//
//    val testStates by courseViewModel.testStates.collectAsState()
//    val testState = testStates[testID.toString()] ?: TestState(0, emptyMap(), 0f, false)
//    val currentQuestionIndex = testState.currentQuestionIndex
//    val selectedAnswers = testState.selectedAnswers
//    val progress = testState.progress
//    val isSubmit = testState.isSubmitted
//    val isEnabled = testState.isEnabled
//
//    var selectedAnswer by remember {
//        mutableStateOf("")
//    }
//    val answeredQuestions = remember { mutableSetOf<Int>() }
////    val selectedAnswers = remember { mutableStateMapOf<Int, String>() }
//
//
//    var isClose by remember {
//        mutableStateOf(false)
//    }
//    var endText by remember {
//        mutableStateOf("Next")
//    }
////    var progress by remember {
////        mutableFloatStateOf(0f)
////    }
//    val indexStyle = MaterialTheme.typography.titleLarge.toSpanStyle().copy(
//        fontWeight = FontWeight.Bold
//    )
//    val totalStyle = MaterialTheme.typography.titleLarge.toSpanStyle()
//    val text = buildAnnotatedString {
//        withStyle(style = indexStyle) {
//            append("${currentQuestionIndex + 1} ")
//        }
//        withStyle(style = totalStyle) {
//            append("of ${questions.size}")
//        }
//    }
//
//
//    Scaffold(topBar = {
//        Column(modifier = Modifier.fillMaxWidth()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp, vertical = 20.dp)
//            ) {
//                Text(
//                    text = text, style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.align(Alignment.Center)
//                )
//
//                IconButton(
//                    onClick = {
//                        if (isSubmit) {
//                            navController.navigate("${NavigationItem.Lesson.route}/${lessonID}")
//                        } else {
//                            isClose = true
//                        }
//
//                    },
//                    modifier = Modifier
//                        .align(Alignment.CenterEnd)
//                ) {
//                    Icon(
//                        Icons.Filled.Close,
//                        contentDescription = "",
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//
//            }
//            LinearProgressIndicator(
//                progress = progress,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp, vertical = 16.dp)
//            )
//
//
//        }
//    }, content = {
//        Column {
//            LazyColumn(
//                modifier = Modifier, contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
//            ) {
//                item(
//                    key = currentQuestionIndex
//                ) {
//                    Spacer(modifier = Modifier.height(140.dp))
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(MaterialTheme.colorScheme.secondaryContainer)
//
//                    ) {
//                        Text(
//                            text = questions[currentQuestionIndex].text,
//                            style = MaterialTheme.typography.titleLarge,
//                            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 24.dp, horizontal = 16.dp)
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    val radioOptions = questions[currentQuestionIndex].answers
//
//                    val (selectedOption, onOptionSelected) = remember(selectedAnswer) {
//                        mutableStateOf(selectedAnswers[currentQuestionIndex] ?: selectedAnswer)
//                    }
//
//
//
//                    Column(modifier = Modifier) {
//                        radioOptions.forEach { answer ->
//                            val onClickHandle = {
//                                onOptionSelected(answer.id)
//                                selectedAnswer = answer.id
//                                if (currentQuestionIndex !in answeredQuestions) {
//                                    answeredQuestions.add(currentQuestionIndex)
//                                    courseViewModel.updateProgress(
//                                        testID.toString(),
//                                        (answeredQuestions.size) / questions.size.toFloat()
//                                    )
//                                }
//                                courseViewModel.updateSelectedAnswers(
//                                    testID.toString(),
//                                    currentQuestionIndex,
//                                    answer.id
//                                )
//                            }
//
//
//                            val optionSelected = answer.id == selectedOption
//
//                            val answerBackgroundColor = if (optionSelected) {
//                                if (isSubmit) {
//                                    if (selectedAnswers[currentQuestionIndex] == questions[currentQuestionIndex].correctAnswerId) {
//                                        Color(0xFFD4EDDA)
//                                    } else {
//                                        Color(0xFFF8D7DA)
//                                    }
//                                } else {
//                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
//                                }
//
//                            } else {
//                                if (isSubmit) {
//                                    if (answer.id == questions[currentQuestionIndex].correctAnswerId) {
//                                        Color(0xFFD4EDDA)
//                                    } else {
//                                        MaterialTheme.colorScheme.background
//                                    }
//                                } else {
//                                    MaterialTheme.colorScheme.background
//                                }
//
//                            }
//
////
//
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .selectable(
//                                        selected = optionSelected,
//                                        onClick = onClickHandle,
//                                        enabled = isEnabled
//                                    )
//                                    .background(answerBackgroundColor)
//                                    .clip(RoundedCornerShape(4.dp))
//                                    .border(
//                                        1.dp,
//                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
//                                        RoundedCornerShape(4.dp)
//                                    ),
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Text(
//                                    modifier = Modifier
//                                        .weight(4f)
//                                        .padding(vertical = 16.dp, horizontal = 16.dp),
//                                    text = answer.text,
//                                    style = MaterialTheme.typography.titleMedium
//                                )
//
//                                RadioButton(
//                                    modifier = Modifier.weight(1f),
//                                    selected = optionSelected,
//                                    onClick = onClickHandle,
//                                    colors = RadioButtonDefaults.colors(
//                                        selectedColor = MaterialTheme.colorScheme.primary
//                                    )
//                                )
//                            }
//                            Spacer(modifier = Modifier.height(16.dp))
//
//                        }
//                    }
//                }
//            }
//
//
//            Spacer(modifier = Modifier.height(20.dp))
//            if (isSubmit) {
//
//                if (selectedAnswers[currentQuestionIndex] == questions[currentQuestionIndex].correctAnswerId) {
//
//                    Column(
//                        modifier = Modifier.padding(horizontal = 20.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(MaterialTheme.colorScheme.secondaryContainer)
//                                .clip(RoundedCornerShape(4.dp))
//                                .border(
//                                    1.dp,
//                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
//                                    RoundedCornerShape(4.dp)
//                                ),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Text(
//                                modifier = Modifier.padding(
//                                    vertical = 16.dp,
//                                    horizontal = 16.dp
//                                ),
//                                text = "Congratulations. You answered correctly!!",
//                                style = MaterialTheme.typography.titleMedium,
//                                color = MaterialTheme.colorScheme.onSecondaryContainer
//                            )
//                        }
//                    }
//                } else {
//                    Column(
//                        modifier = Modifier.padding(horizontal = 20.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(MaterialTheme.colorScheme.errorContainer)
//                                .clip(RoundedCornerShape(4.dp))
//                                .border(
//                                    1.dp,
//                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
//                                    RoundedCornerShape(4.dp)
//                                ),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Text(
//                                modifier = Modifier.padding(
//                                    vertical = 16.dp,
//                                    horizontal = 16.dp
//                                ),
//                                text = "Sorry. Your answer is wrong!!",
//                                style = MaterialTheme.typography.titleMedium,
//                                color = MaterialTheme.colorScheme.onErrorContainer
//                            )
//                        }
//                    }
//
//                }
//                Spacer(modifier = Modifier.height(30.dp))
//
//
//            }
//
//            if (isClose) {
//                AlertDialog(
//                    onDismissRequest = {
//                        isClose = false
//                    },
//                    title = {
//                        Text(text = "Confirm exit")
//                    },
//                    text = {
//                        Text("You have not completed the test. Are you sure you want to exit this page?")
//                    },
//                    confirmButton = {
//                        Button(
//                            onClick = {
//                                navController.navigate("${NavigationItem.Lesson.route}/${lessonID}")
//                            }
//                        ) {
//                            Text("Exit")
//                        }
//                    },
//                    dismissButton = {
//                        Button(
//                            onClick = {
//                                isClose = false
//                            }
//                        ) {
//                            Text("Cancel")
//                        }
//                    }
//                )
//            }
//        }
//
//
//    }, bottomBar = {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 20.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//
//            OutlinedButton(modifier = Modifier
//                .weight(1f)
//                .height(48.dp), onClick = {
//                if (currentQuestionIndex == 0) {
//                    if (progress == 1f) {
//                        navController.navigate("${NavigationItem.Lesson.route}/${lessonID}")
//                    } else {
//                        isClose = true
//                    }
//
//                } else {
//                    // progress = (currentQuestionIndex + 1) / questions.size.toFloat()
//                    courseViewModel.updateCurrentQuestionIndex(
//                        testID.toString(),
//                        maxOf(
//                            currentQuestionIndex - 1,
//                            0
//                        )
//                    )
//
//                }
//
//
//            }) {
//                Text(text = "Back")
//            }
//            Spacer(modifier = Modifier.width(16.dp))
//            Button(modifier = Modifier
//                .weight(1f)
//                .height(48.dp),
//                onClick = {
//                    if (currentQuestionIndex == questions.size - 1) {
//                        if (progress == 1f) {
//                            if (isSubmit && currentQuestionIndex == questions.size - 1) {
//                                endText = "Exit"
//                            }
//                            courseViewModel.updateSubmitted(testID.toString())
//                            courseViewModel.updateEnabled(testID.toString())
//                            isClose = false
//                            endText = "Next"
//                            courseViewModel.updateCurrentQuestionIndex(testID.toString(), 0)
//                            //   navController.navigate("${NavigationItem.Lesson.route}/${lessonID}")
//                        } else {
//                            isClose = true
//                        }
//                    } else {
//                        //  progress = (currentQuestionIndex + 1) / questions.size.toFloat()
//                        courseViewModel.updateCurrentQuestionIndex(
//                            testID.toString(),
//                            minOf(
//                                currentQuestionIndex + 1,
//                                questions.size - 1
//                            )
//                        )
//
//
//                    }
//
//
//                }) {
//                if (currentQuestionIndex == questions.size - 1) {
//                    endText = "Finish"
//                }
//                Text(text = endText)
//            }
//        }
//
//
//    })
//
//    Log.e("Question Index:", currentQuestionIndex.toString())
//}
//
//
//
//
//
//
//
