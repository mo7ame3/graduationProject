package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.client.creatOrder.CreateOrder
import com.example.graduationproject.model.shared.register.Register
import com.example.graduationproject.model.worker.home.WorkerHome
import com.example.graduationproject.model.worker.orderDetails.GetOrderDetails
import com.example.graduationproject.network.GraduationApi
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class WorkerRepository @Inject constructor(private val api: GraduationApi) {

    private val getHome = WrapperClass<WorkerHome, Boolean, Exception>()
    private val getOrderDetails = WrapperClass<GetOrderDetails, Boolean, Exception>()

    //-----------------------
    suspend fun getHome(
        craftId: String,
        authorization: String
    ): WrapperClass<WorkerHome, Boolean, Exception> {

        try {
            getHome.data = api.getWorkerHome(craftId, authorization)
        }  catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            Log.d("TAG", "getHomeWorker: $message")
            getHome.data = WorkerHome(status = status, message = message)

        }catch (e: SocketTimeoutException) {
            getHome.e = e
            Log.d("TAG", "getHomeWorker: $e")

        }

        return getHome
    }

    suspend fun getOrderDetails(
        craftId: String, authorization: String,
        orderId: String
    ): WrapperClass<GetOrderDetails, Boolean, Exception> {
        try {
            getOrderDetails.data = api.getOrderDetails(
                craftId = craftId,
                authorization = authorization,
                orderId = orderId
            )
        }
         catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            Log.d("TAG", "getOrderDetails: $message")
            getOrderDetails.data = GetOrderDetails(status = status, message = message)

        } catch (e: SocketTimeoutException) {
            getOrderDetails.e = e
            Log.d("TAG", "getOrderDetails: $e")

        }

        return getOrderDetails
    }

}