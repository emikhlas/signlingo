package com.signlingo.quiz.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("soal")
    fun getQuiz(
        @Header("appkey") appkey: String,
    ): Call<QuizResponse>
}

data class QuizResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("imageurl")
    val imageurl: String,

    @field:SerializedName("label")
    val label: String
)