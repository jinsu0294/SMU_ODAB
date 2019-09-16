package com.example.smu_quiz_2.data_class

data class Quiz_smu(
    var quiz_id: Int,
    var email: String,
    var title: String,
    var text: String,
    var choice_1: Char,
    var choice_2: Char,
    var choice_3: Char,
    var choice_4: Char,
    var answer: Int,
    var explain: String,
    var Management_id: Int
)