package com.example.smu_quiz_2

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import com.example.smu_quiz_2.data_class.*
import java.io.File

class User: Application() {

    var user: String? = null
    var ispermission:Boolean? = null

    var folderList = arrayListOf<Folder>()

    var odablist = arrayListOf<Odab>()

    var quizlist = arrayListOf<Quiz>()


    lateinit var photo: Bitmap

//    lateinit var photoPath: String

//    lateinit var photoFileName: String

    var photoFile:File?=null

//    lateinit var photoUri:Uri




    fun setPermission(permission:Boolean?){
        this.ispermission = permission
    }
    fun getPermission():Boolean?{
        return this.ispermission
    }
    fun setphotofile(file:File?){
        this.photoFile=file
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
        this.folderList.add(Folder(-1,folderTitle,user.toString()))
    }

    fun getfolder():String?{
        var name:String? = null
        for (i in 0..folderList.size-1){
            name = "${name} + ${folderList[i].title}"
        }
        return name
    }

    // odab list 추가
    fun addodab(title:String, textcontents:String, image:Bitmap){
//        this.odablist.add(Wrong(1,"123",image,title, textcontents, 8))
    }

    fun getodab():String?{
        var name:String? = null
        for (i in 0..odablist.size-1){
            name = "${name} + ${odablist[i].title}"
        }
        return name
    }

    // quiz list 추가
    fun addQuiz(
        quiz_id:Int, email:String, title:String, text:String, choice_1: String, choice_2: String, choice_3:String, choice_4:String, answer:Int, explain:String,
        Management_id:Int, ischecked: Boolean){
        this.quizlist.add(
            Quiz(
                quiz_id,
                title,
                text,
                choice_1,
                choice_2,
                choice_3,
                choice_4,
                answer,
                explain,
                email,
                Management_id
            )
        )
    }

}