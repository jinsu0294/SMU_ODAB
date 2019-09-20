package com.example.smu_quiz_2.data_class

import okhttp3.MultipartBody
import retrofit2.http.Url
import java.lang.reflect.Field
import java.text.DateFormat
import java.text.Format

data class CreateWrong(
    var email: String,
    var image: Url?,
    var title: String,
    var text: String,
    var Management_id: Int
)