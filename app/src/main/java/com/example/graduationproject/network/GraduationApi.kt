package com.example.graduationproject.network

import com.example.graduationproject.constant.Constant
import com.example.graduationproject.model.admin.createCraft.CreateNewCraft
import com.example.graduationproject.model.admin.deleteCraft.Delete
import com.example.graduationproject.model.shared.getAllCrafts.GetAllCrafts
import com.example.graduationproject.model.shared.login.Login
import com.example.graduationproject.model.shared.register.Register
import com.example.graduationproject.model.shared.register.myCraft.MyCraft
import com.example.graduationproject.model.admin.updateCraft.UpdateCraft
import com.example.graduationproject.model.client.creatOrder.CreateOrder
import com.example.graduationproject.model.client.getMyOrder.GetMyOrder
import com.example.graduationproject.model.shared.craftList.CraftList
import com.example.graduationproject.model.shared.getCraft.GetCraft
import com.example.graduationproject.model.shared.getCraftOfWorker.GetCraftOfWorker
import com.example.graduationproject.model.worker.createOffer.CreateOffer
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

    @GET(Constant.CRAFTOFWORKER + "/{craftID}")
    suspend fun getCraftOfWorker(
        @Path("craftID") craftId: String,
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
    @POST(Constant.CREATEOFFER)
    suspend fun createOffer(
        @Header("Authorization") authorization: String,
        @Body offerBody: Map<String, String>
    ): CreateOffer

}