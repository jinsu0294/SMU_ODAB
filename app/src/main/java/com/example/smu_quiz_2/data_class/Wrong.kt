package com.example.smu_quiz_2.data_class

import java.net.URL

data class Wrong(
    var wrong_id: Int,
    var email: String,
    var image: URL,
    var title: String,
    var text: String,
    var Management_id: Int
)