package com.example.mobilefinalproject.model

import com.google.gson.annotations.SerializedName
import kotlin.time.Duration

class Lesson(
    val id: Int,
    val course_id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("image_path")
    val img: String,
    val name: String,
    val description: String,
    var videos: List<Video>,
    @SerializedName("course")
    val course: Course
//    val document: List<Document> = Document.getDoc(),
){
    fun extractVideoId(url: String): String {
        return when {
            url.contains("youtube.com/watch") -> {
                val parts = url.split("?v=")
                if (parts.size > 1) parts[1].split("&")[0] else ""
            }
            url.contains("youtu.be") -> {
                val parts = url.split("/")
                if (parts.size > 1) parts.last() else ""
            }
            url.contains("youtube.com/embed") -> {
                val parts = url.split("/embed/")
                if (parts.size > 1) parts[1].split("?")[0] else ""
            }
            else -> ""
        }
    }
}


data class LessonResponse(
    val course: Course,
    val lesson: Lesson,
    val teacher: Teacher
)
