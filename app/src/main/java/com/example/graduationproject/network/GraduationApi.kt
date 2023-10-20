package com.example.graduationproject.network

import com.example.graduationproject.constant.Constant
import com.example.graduationproject.model.admin.createCraft.CreateNewCraft
import com.example.graduationproject.model.admin.deleteCraft.Delete
import com.example.graduationproject.model.admin.updateCraft.UpdateCraft
import com.example.graduationproject.model.client.creatOrder.CreateOrder
import com.example.graduationproject.model.client.getMyOrder.GetMyOrder
import com.example.graduationproject.model.client.offerOfAnOrder.GetOfferOfAnOrder
import com.example.graduationproject.model.client.updateOrder.UpdateOrder
import com.example.graduationproject.model.shared.craftList.CraftList
import com.example.graduationproject.model.shared.getAllCrafts.GetAllCrafts
import com.example.graduationproject.model.shared.getCraft.GetCraft
import com.example.graduationproject.model.shared.getCraftOfWorker.GetCraftOfWorker
import com.example.graduationproject.model.shared.login.Login
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.model.shared.register.Register
import com.example.graduationproject.model.shared.register.myCraft.MyCraft
import com.example.graduationproject.model.shared.updateOffer.UpdateOffer
import com.example.graduationproject.model.shared.updatePassword.UpdatePassword
import com.example.graduationproject.model.shared.updateProfileData.UpdateProfileData
import com.example.graduationproject.model.shared.updateProflePhoto.UpdateProfilePhoto
import com.example.graduationproject.model.worker.createOffer.CreateOffer
import com.example.graduationproject.model.worker.getMyOffer.GetMyOffer
import com.example.graduationproject.model.worker.home.WorkerHome
import com.example.graduationproject.model.worker.orderDetails.GetOrderDetails
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    //updateCraft
    @Multipart
    @PATCH(Constant.CRAFT + "/{craftID}")
    suspend fun updateCraft(
        @Path("craftID") craftId: String,
        @Header("Authorization") authorization: String,
        @Part image: MultipartBody.Part? = null,
        @Part("name") name: RequestBody? = null,
    ): UpdateCraft

    //deleteCraft
    @DELETE(Constant.DELETECRAFT + "/{craftID}")
    suspend fun deleteCraft(
        @Path("craftID") craftId: String,
        @Header("Authorization") authorization: String,
    ): Delete

    //Create Order
    @Multipart
    @POST("api/v1/crafts/{craftID}/orders")
    suspend fun createOrder(
        @Path("craftID") craftId: String,
        @Header("Authorization") authorization: String,
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("orderDifficulty") orderDifficulty: RequestBody,
        @Part("description") description: RequestBody
    ): CreateOrder

    //Get worker Home

    @GET("api/v1/crafts/{craftId}/orders")
    suspend fun getWorkerHome(
        @Path("craftId") craftId: String,
        @Header("Authorization") authorization: String
    ): WorkerHome

    //Get Craft List use in Register and Client Order Home

    @GET(Constant.CRAFTLIST)
    suspend fun getCraftList(): CraftList

    //Get Craft Of Worker

    @GET(Constant.CRAFTOFWORKER + "/{workerId}")
    suspend fun getCraftOfWorker(
        @Path("workerId") workerId: String,
    ): GetCraftOfWorker

    @GET("api/v1/crafts/{craftId}/orders/{orderId}")
    suspend fun getOrderDetails(
        @Path("craftId") craftId: String,
        @Path("orderId") orderId: String,
        @Header("Authorization") authorization: String
    ): GetOrderDetails

    //Get My Order
    @GET("api/v1/crafts/{craftId}/orders/myorders")
    suspend fun getMyOrder(
        @Path("craftId") craftId: String,
        @Header("Authorization") authorization: String
    ): GetMyOrder

    //Delete Order
    @DELETE("api/v1/crafts/{craftId}/orders/{orderId}")
    suspend fun deleteOrder(
        @Path("craftId") craftId: String,
        @Path("orderId") orderId: String,
        @Header("Authorization") authorization: String
    ): Delete

    //Create Offer
    @POST(Constant.CREATEOFFER + "/{orderId}")
    suspend fun createOffer(
        @Header("Authorization") authorization: String,
        @Path("orderId") orderId: String,
        @Body offerBody: Map<String, String>
    ): CreateOffer

    //Get OrderOfAnCraft
    @GET(Constant.OFFEROFANORDER + "/{orderId}")
    suspend fun getOfferOfAnOrder(
        @Header("Authorization") authorization: String,
        @Path("orderId") orderId: String
    ): GetOfferOfAnOrder


    //updateOffer
    @PATCH(Constant.UPDATEOFFER + "/{offerID}")
    suspend fun updateOffer(
        @Path("offerID") offerId: String,
        @Header("Authorization") authorization: String,
        @Body updateBody: Map<String, String>
    ): UpdateOffer

    //Get OrderOfAnCraft
    @GET(Constant.GETPROFILE + "/{userId}")
    suspend fun getProfile(
        @Path("userId") userId: String,
        @Header("Authorization") authorization: String,
    ): GetProfile

    //update Order
    @PATCH("api/v1/crafts/{craftId}/orders/{orderId}")
    suspend fun updateOrder(
        @Path("craftId") craftId: String,
        @Path("orderId") orderId: String,
        @Header("Authorization") authorization: String,
        @Body updateOrderBody: Map<String, String>
    ): UpdateOrder

    //Update Profile Setting
    @PATCH(Constant.GETPROFILE + "/{userId}")
    suspend fun updateProfileData(
        @Path("userId") userId: String,
        @Header("Authorization") authorization: String,
        @Body updateProfileBody: Map<String, String>
    ): UpdateProfileData


    //update profile photo
    @Multipart
    @PATCH(Constant.UPDATEPROFILEPHOTO + "/{userId}")
    suspend fun updateProfilePhoto(
        @Path("userId") userId: String,
        @Part image: MultipartBody.Part,
        @Header("Authorization") authorization: String
    ): UpdateProfilePhoto


    //get my offer
    @GET(Constant.GETMYOFFER + "/{userId}")
    suspend fun getMyOffer(
        @Path("userId") userId: String,
        @Header("Authorization") authorization: String
    ): GetMyOffer

    //update Password

    @PATCH(Constant.UPDATEPASSWORD)
    suspend fun updatePassword(
        @Header("Authorization") authorization: String,
        @Body updatePasswordBody: Map<String, String>
    ): UpdatePassword
}