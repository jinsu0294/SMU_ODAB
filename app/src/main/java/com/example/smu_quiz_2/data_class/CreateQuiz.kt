package com.example.smu_quiz_2.data_class

data class CreateQuiz(
    var email: String,
    var title: String,
    var text: String,
    var choice_1: String,
    var choice_2: String,
    var choice_3: String,
    var choice_4: String,
    var answer: Int,
    var explain: String,
    var Management_id: Int
)