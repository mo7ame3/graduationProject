package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.client.creatOrder.CreateNewOrder
import com.example.graduationproject.network.GraduationApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ClientRepository @Inject constructor(private val api: GraduationApi) {

    private val createNewOrder: WrapperClass<CreateNewOrder, Boolean, Exception> = WrapperClass()
    suspend fun CreateOrder(
        image: MultipartBody.Part,
        title: RequestBody,
        description: RequestBody,
        orderDifficulty: RequestBody,
        token: String,
        craftId: String
    ): WrapperClass<CreateNewOrder, Boolean, Exception> {
        try {
            //addNewUser.loading = true
            createNewOrder.data = api.createOrder(
                image = image,
                title = title,
                description = description,
                orderDifficulty = orderDifficulty,
                authorization = token,
                craftId = craftId
            )
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            Log.d("TAG", "CreateOrder: $message")
            createNewOrder.data = CreateNewOrder(status = status, message = message)

        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "getAllCrafts: $e")
            createNewOrder.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "getAllCrafts: $e")
            createNewOrder.e = e

        }
        return createNewOrder
    }

}