package com.example.smu_quiz_2.data_class

import android.widget.Checkable

class Quiz(
    var title:String,
    var contents:String,
    var quiz_1:String,
    var quiz_2:String,
    var quiz_3:String,
    var quiz_4:String,
    var answer:Int,
    var explanation:String,
    var isChecked: Boolean
)