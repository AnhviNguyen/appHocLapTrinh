package com.example.mobilefinalproject.model

import com.google.gson.annotations.SerializedName

data class Comment(
    val id:Int,
    val content:String,
    @SerializedName("student")
    val user:User,
    val lesson:Lesson,
    @SerializedName("created_at")
    val time:String,
    @SerializedName("reply_comments")
    val replyComments:List<Comment>,
)

data class CommentRequest (
    @SerializedName("content")
    var content: String,

    @SerializedName("lesson_id")
    var lessonID: Int,

    @SerializedName("student_id")
    var userID: Int
)

data class ReplyCommentRequest (
    @SerializedName("content")
    var content: String,

    @SerializedName("comment_id")
    var commentID: Int,

    @SerializedName("student_id")
    var userID: Int
)

data class AddCommentResponse (
    val comment:Comment,
)

data class CommentResponse (
    val comment: Comment,
    @SerializedName("message-remove")
    val messageRemove: String,
    @SerializedName("replyComments")
    val replyComments: List<Comment>,
    @SerializedName("allReplyComments")
    val allReplyComments: List<Comment>,

)






