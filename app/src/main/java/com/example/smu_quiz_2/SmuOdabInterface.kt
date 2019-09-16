package com.example.smu_quiz_2


import com.example.smu_quiz_2.data_class.Quiz
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.*


interface SmuOdabInterface {


    @POST("/user")
    fun setUser(@Body value: User): Flowable<User>

    @GET("/user")
    fun getUser(): Observable<List<User>>

    @POST("/folder/problem")
    fun createQuiz(@Body value: Quiz): Flowable<Quiz>

    @FormUrlEncoded
    @HTTP(method = "DELETE",path = "{/folder/problem/{id}}",hasBody = true)
    fun deleteQuiz(@Path("id") id: String, @Body value: User): Flowable<User>

///folder/problem

    /*
    @GET("/quiz/mocktest?subject=Database&subject=operation_system")
    fun test(): Observable<List<Quiz>>

    @GET("/quiz/mocktest")
    fun getMocktest(@QueryMap option: Map<String, String>): Observable<List<Quiz>>

    @GET("/register/wrong")
    fun getWorngNum(@Query("email") email: String): Observable<List<Wrong>>

    @GET("/register/bookmark")
    fun getBookMarkNum(@Query("email") email: String): Observable<List<Wrong>>

    @GET("/register/detail")
    fun getWorngDetail(@Query("pr_id") wrongNum: Int): Observable<List<Quiz>>

    @GET("/quiz/request")
    fun getDailyQuiz(@Query("subject") subject: String): Observable<List<Quiz>>

    @POST("/register/wrong")
    fun setWrongQuiz(@Body value: Wrong): Flowable<Wrong>

    @POST("/register/bookmark")
    fun setBookMark(@Body value: Wrong): Flowable<Wrong>

    @POST("/register/user_list")
    fun setUser(@Body value: User): Flowable<User>

    @FormUrlEncoded
    @HTTP(method = "DELETE",path = "{/register/bookmark/{id}}",hasBody = true)
    fun deleteFavoirty(@Path("id") id: String, @Body value: User): Flowable<User>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/register/wrong/{id}", hasBody = true)
    fun deleteWrong(@Path("id") id: String,@Body value: User): Flowable<User>
*/

}
