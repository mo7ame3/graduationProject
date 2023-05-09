package com.example.graduationproject.network

import com.example.graduationproject.constant.Constant
import com.example.graduationproject.model.admin.createCraft.CreateNewCraft
import com.example.graduationproject.model.creatOrder.CreateNewOrder
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.model.getCraft.GetCraft
import com.example.graduationproject.model.login.Login
import com.example.graduationproject.model.register.Register
import com.example.graduationproject.model.register.myCraft.MyCraft
import com.example.graduationproject.model.updateCraft.UpdateCraft
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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


    //GET All CRAFT ("Admin and client")
    @GET(Constant.CRAFT)
    suspend fun getAllCrafts(
        @Header("Authorization") authorization: String,
    ): GetAllCrafts

    //Admin create craft

    @Multipart
    @POST(Constant.CRAFT)
    suspend fun adminCreateCraft(
        @Header("Authorization") authorization: String,
        //  @Part("name") name: String,
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part
    ): CreateNewCraft
    //Get Craft

    @GET(Constant.CRAFT + "/{craftId}")
    suspend fun getCraft(
        @Path("craftId") craftId: String,
        @Header("Authorization") authorization: String
    ): GetCraft

    //Create Order
    @Multipart
    @POST(Constant.CREATEORDER + "/{craftID}")
    suspend fun createOrder(
        @Path("craftID") craftId: String,
        @Header("Authorization") authorization: String,
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("orderDifficulty") orderDifficulty: RequestBody,
        @Part("description") description: RequestBody
    ): CreateNewOrder

    @Multipart
    @PATCH("api/v1/crafts/{craftID}")
    suspend fun updateCraft(
        @Path("craftID") craftId: String,
        @Header("Authorization") authorization: String,
        @Part image: MultipartBody.Part? = null,
        @Part("name") name: RequestBody? = null,
    ): UpdateCraft

}