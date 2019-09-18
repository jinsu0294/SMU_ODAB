package com.example.smu_quiz_2.data_class

import android.net.Uri
import java.net.URL

data class Wrong(
    var wrong_id: Int,
    var email: String,
    var image: Uri,
    var title: String,
    var text: String,
    var Management_id: Int
)