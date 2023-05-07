package com.example.graduationproject.network

import com.example.graduationproject.constant.Constant
import com.example.graduationproject.model.admin.createCraft.CreateNewCraft
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.model.login.Login
import com.example.graduationproject.model.register.Register
import com.example.graduationproject.model.register.myCraft.MyCraft
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import javax.inject.Singleton


@Singleton
interface GraduationApi {

    //register
    @POST(Constant.REGISTER)
    suspend fun register(
        @Body registerInput: Map<String, String>
    ): Register

    //Worker choose Craft
    @PUT(Constant.MYCRAFT + "/{workerId}")
    suspend fun workerChooseCraft(
        @Path("workerId") workerId: String,
        @Header("Authorization") authorization: String,
        @Body myCraft: Map<String, String>

    ): MyCraft

    //Login
    @POST(Constant.LOGIN)
    suspend fun login(
        @Body loginInput: Map<String, String>
    ): Login

    //GET CRAFT
    @GET(Constant.GETALLCRAFTS)
    suspend fun getAllCrafts(
        @Header("Authorization") authorization: String,
    ): GetAllCrafts

    //Admin create craft
    @POST(Constant.CREATECRAFT)
    suspend fun adminCreateCraft(
        @Header("Authorization") authorization: String,
        @Body newCraft: Map<String, String>
    ): CreateNewCraft
}