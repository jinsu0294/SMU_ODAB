package com.example.smu_quiz_2

import android.app.Application
import android.graphics.Bitmap
import com.example.smu_quiz_2.data_class.Folder
import com.example.smu_quiz_2.data_class.Odab
import com.example.smu_quiz_2.data_class.Quiz

class User: Application() {

    var user: String? = null
    var ispermission:Boolean? = null

    var folderList = arrayListOf<Folder>()

    var odablist = arrayListOf<Odab>()

    var quizlist = arrayListOf<Quiz>()

    var photo: Bitmap? = null

    fun setPermission(permission:Boolean?){
        this.ispermission = permission
    }
    fun getPermission():Boolean?{
        return this.ispermission
    }

    fun setphoto(bitmap: Bitmap){
        this.photo = bitmap
    }
    // userId 저장
    fun setId(userId:String){
        this.user = userId
    }

    // userId 가져오기
    fun getId(): String?{
        return this.user
    }

    // folder list 추가
    fun add(folderTitle:String){
        this.folderList.add(Folder(folderTitle))
    }

    fun getfolder():String?{
        var name:String? = null
        for (i in 0..folderList.size-1){
            name = "${name} + ${folderList[i].title}"
        }
        return name
    }

    // odab list 추가
    fun addodab(title:String, textcontents:String, image:Bitmap?){
        this.odablist.add(Odab(title, textcontents, image))
    }

    fun getodab():String?{
        var name:String? = null
        for (i in 0..odablist.size-1){
            name = "${name} + ${odablist[i].title}"
        }
        return name
    }

    // quiz list 추가
    fun addQuiz(title:String, contents:String, quiz_1:String, quiz_2:String, quiz_3:String, quiz_4:String, answer:Int, explanation:String, checkable:Boolean){
        this.quizlist.add(
            Quiz(
                title,
                contents,
                quiz_1,
                quiz_2,
                quiz_3,
                quiz_4,
                answer,
                explanation,
                checkable
            )
        )
    }

}