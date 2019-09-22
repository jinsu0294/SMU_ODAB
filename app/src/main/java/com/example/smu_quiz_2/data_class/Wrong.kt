package com.example.smu_quiz_2.data_class

import android.net.Uri
import java.net.URL

data class Wrong(
    var wrong_id: Int,
    var image: String,
    var title: String,
    var text: String,
    var email: String,
    var Management_id: Int
)