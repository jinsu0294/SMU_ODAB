package com.example.smu_quiz_2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smu_quiz_2.data_class.UserDataClass
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity:AppCompatActivity() {

    private var smuOdabAPI = SmuOdabAPI()
    private var smuInfoRetrofit = smuOdabAPI.smuInfoRetrofit()
    private var smuOdabInterface = smuInfoRetrofit.create(SmuOdabInterface::class.java)

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var currentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()


        btnlogin.setOnClickListener {
            signInWithGoogle()

        }
    }

    public override fun onStart() {
        super.onStart()
        currentUser = auth.currentUser.toString()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)

            }
        }
    }

    @SuppressLint("CheckResult")
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val user = application as User
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // TODO:: OK 로그인 및 사용자 등록 setUser() @POST /user
                    val email = auth.currentUser?.email
                    if (email != null) {
                        user.setId(email)
                    }

                    smuOdabInterface.setUser(UserDataClass(user.getId().toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ wrong ->
                            Log.e("123123",wrong.toString())
                        }, { error ->
                            Log.e("123123","false "+user.getId().toString())
                            error.printStackTrace()
                        }, {
                            Log.d("UserDataClass", "complete::UserDataClass")
                        })
                    Log.d(TAG, "signInWithCredential:success")
                    val intent = Intent(this, UserFolderActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        private const val TAG = "TAG"
        private const val RC_SIGN_IN = 9001
    }

}