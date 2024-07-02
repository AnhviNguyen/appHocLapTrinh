//package com.example.mobilefinalproject.view.screens.auth
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.mobilefinalproject.model.Question
//import com.example.mobilefinalproject.viewmodel.CourseViewModel
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ReviewScreen(
//    navController: NavController,
//    courseViewModel: CourseViewModel,
//    lessonID: Int,
//) {
//    Scaffold(topBar = {
//        TopAppBar(title = { Text("Review Answers") })
//    }) {
//        LazyColumn(modifier = Modifier.padding(16.dp)) {
//            answeredQuestionsWithAnswers.forEach { (questionIndex, answerId) ->
//                val question = questions[questionIndex]
//                item {
//                    Text(
//                        text = "Question ${questionIndex + 1}: ${question.text}",
//                        style = MaterialTheme.typography.titleMedium,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                    val selectedAnswer = question.answers.firstOrNull { it.id == answerId }
//                    Text(
//                        text = "Your answer: ${selectedAnswer?.text ?: "No answer"}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = if (selectedAnswer?.id == question.correctAnswerId) Color.Green else Color.Red,
//                        modifier = Modifier.padding(vertical = 4.dp)
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//            }
//        }
//    }
//}