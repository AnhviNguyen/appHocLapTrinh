package com.example.mobilefinalproject.model


import com.google.gson.annotations.SerializedName


data class Test(
    val id: Int,
    val questions: List<Question>?= emptyList(),
    val title: String,
)

data class TestState(
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: Map<Int, String> = emptyMap(),
    val progress: Float = 0f,
    val isSubmitted: Boolean = false,
    val isEnabled: Boolean = true,
)

data class Question(
    val id:Int,
    val text: String,
    val answers: List<Answer>,
    @SerializedName("correctAns_id")
    val correctAnswerId: String,

)

data class Answer(
    val id: String,
    @SerializedName("answer")
    val text: String
)

data class TestResponse(
    val test: Test,
    val questions: List<Question>,
)
