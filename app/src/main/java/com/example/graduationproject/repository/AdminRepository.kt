package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.admin.createCraft.CreateNewCraft
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.network.GraduationApi
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class AdminRepository @Inject constructor(private val api: GraduationApi) {
    private val getAllCrafts = WrapperClass<GetAllCrafts, Boolean, Exception>()
    private val createNewCraft = WrapperClass<CreateNewCraft, Boolean, Exception>()

    suspend fun getAllCrafts(authorization: String)
            : WrapperClass<GetAllCrafts, Boolean, Exception> {
        try {
            //addNewUser.loading = true
            getAllCrafts.data = api.getAllCrafts(authorization = authorization)
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            getAllCrafts.data = GetAllCrafts(status = status, message = message)

        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "getAllCrafts: $e")
            getAllCrafts.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "getAllCrafts: $e")
            getAllCrafts.e = e

        }
        return getAllCrafts
    }

    suspend fun createNewCraft(authorization: String, name: String, image: MultipartBody.Part)
            : WrapperClass<CreateNewCraft, Boolean, Exception> {
        try {
            //addNewUser.loading = true
            createNewCraft.data = api.adminCreateCraft(authorization, name = name, image = image)
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            createNewCraft.data = CreateNewCraft(status = status, message = message)

        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "getAllCrafts: $e")
            createNewCraft.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "getAllCrafts: $e")
            createNewCraft.e = e

        }
        return createNewCraft
    }
}