package com.example.graduationproject.network

import com.example.graduationproject.constant.Constant
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.model.login.Login
import com.example.graduationproject.model.register.Register
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton


@Singleton
interface GraduationApi {


    //register
    @POST(Constant.REGISTER)
    suspend fun register(
        @Body registerInput: Map<String, String>
    ): Register


    @POST(Constant.LOGIN)
    suspend fun login(
        @Body loginInput: Map<String, String>
    ): Login

    @GET(Constant.GETALLCRAFTS)
    suspend fun getAllCrafts(
        @Header("Authorization") authorization:String
    ): GetAllCrafts
}