package com.example.smu_quiz_2

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity:AppCompatActivity(){
    private lateinit var currentUser: String
    private lateinit var auth: FirebaseAuth

    var login:Runnable = Runnable {
        val intent = Intent(applicationContext,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    var userfolder:Runnable = Runnable {
        val intent = Intent(applicationContext,UserFolderActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ivSplash.setImageResource(R.drawable.anim)

        val animation = ivSplash.drawable as AnimationDrawable
        if(animation != null){
            animation.start()
        }else{
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
        }

        auth = FirebaseAuth.getInstance()

        currentUser = auth.currentUser.toString()

        var handler = Handler()
        
        if(currentUser != null){
            handler.postDelayed(userfolder, 4000)
        }else{
            handler.postDelayed(login, 3000)
        }

    }
}