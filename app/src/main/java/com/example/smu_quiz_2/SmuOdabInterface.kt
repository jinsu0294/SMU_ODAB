package com.example.smu_quiz_2


import com.example.smu_quiz_2.data_class.*
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.*


interface SmuOdabInterface {
    //이메일등록
    @POST("/user/")
    fun setUser(@Body value: UserDataClass): Flowable<UserDataClass>

    //유저들이메일불러오기
    @GET("/user")
    fun getUser(): Observable<List<UserDataClass>>

    //폴더생성
    @POST("/folder/list")
    fun createFolder(@Body value: CreateFolder): Observable<CreateFolder>

    //폴더조회 x
    //폴더리스트조회 - 이메일로
    @GET("/folder/search")
    fun getFolderList(@Query("email") email: String?): Observable<List<FolderList>>


    //폴더삭제
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "{/folder/list/{folder_id}}", hasBody = true)
    fun deleteFolder(@Path("id") id: String)

    //퀴즈생성
    @POST("/folder/problem/")
    fun createQuiz(@Body value: CreateQuiz): Observable<CreateQuiz>

    //퀴즈삭제
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "{/folder/problem/{quiz_id}}", hasBody = true)
    fun deleteQuiz(@Path("quiz_id") id: String)

    //퀴즈 리스트조회
    @GET("/folder/detail_quiz")
    fun getQuizList(@Query("Management_id") Management_id: Int?): Observable<List<QuizList>>

    //퀴즈상세조회
    @GET("/folder/select")
    fun getQuiz(@Query("quiz_id") quiz_id: String?): Observable<List<Quiz>>

    //문제리스트선택해서 풀기
    //quiz_id=1
    @GET("/folder/select")
    fun getSelectQuizList(@QueryMap option: Map<String, Int>): Observable<Quiz>


    //오답노트생성
    @POST("/folder/wrong")
    fun createWrong(@Body value: CreateWrong): Observable<CreateWrong>

    //오답노트 리스트조회
    @GET("/folder/wrong/detail_wrong")
    fun getWrongList(@Query("Management_id") forder_id: Int): Observable<List<WrongList>>

    //오답노트상세조회
    @GET("/folder/wrong/{wrong_id}")
    fun getWrongDetail(): Observable<Wrong>

    //오답노트수정 x
    //오답노트삭제
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "{/folder/wrong/{wrong_id}}", hasBody = true)
    fun deleteWrong(@Path("wrong_id") id: String)


}
