package com.example.graduationproject.network

import com.example.graduationproject.constant.Constant
import com.example.graduationproject.model.Register
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton


@Singleton
interface GraduationApi {


    //register

    @POST(Constant.REGISTER)
    suspend fun register(
        @Body registerInput: Map<String, String>
    ): Register
}