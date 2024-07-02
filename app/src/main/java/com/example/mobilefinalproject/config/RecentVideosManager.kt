package com.example.mobilefinalproject.config

import android.content.Context
import com.example.mobilefinalproject.model.Lesson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



    class RecentVideosManager(private val context: Context) {

        private val gson = Gson()

        fun addRecentLessonForUser(userId: String, lesson: Lesson) {
            val sharedPreferences = context.getSharedPreferences("recent_videos_prefs_$userId", Context.MODE_PRIVATE)
            val recentLessons = getRecentLessonsForUser(userId).toMutableList()

            val existingIndex = recentLessons.indexOfFirst { it.id == lesson.id }
            if (existingIndex != -1) {
                recentLessons.removeAt(existingIndex)
            }

            recentLessons.add(0, lesson)
            if (recentLessons.size > MAX_RECENT_VIDEOS) {
                recentLessons.removeAt(recentLessons.size - 1)
            }

            saveRecentLessonsForUser(userId, recentLessons)
        }

        fun getRecentLessonForUser(userId: String): Lesson? {
            val recentLessons = getRecentLessonsForUser(userId)
            return recentLessons.firstOrNull()
        }

        fun getRecentLessonsForUser(userId: String): List<Lesson> {
            val sharedPreferences = context.getSharedPreferences("recent_videos_prefs_$userId", Context.MODE_PRIVATE)
            val recentLessonsJson = sharedPreferences.getString(RECENT_LESSONS_KEY, null)
            return if (recentLessonsJson != null) {
                gson.fromJson(recentLessonsJson, object : TypeToken<List<Lesson>>() {}.type)
            } else {
                emptyList()
            }
        }

        private fun saveRecentLessonsForUser(userId: String, recentLessons: List<Lesson>) {
            val sharedPreferences = context.getSharedPreferences("recent_videos_prefs_$userId", Context.MODE_PRIVATE)
            val recentLessonsJson = gson.toJson(recentLessons)
            sharedPreferences.edit().putString(RECENT_LESSONS_KEY, recentLessonsJson).apply()
        }

        companion object {
            private const val MAX_RECENT_VIDEOS = 10
            private const val RECENT_LESSONS_KEY = "recent_lessons"
        }
    }




