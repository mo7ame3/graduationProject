package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.craftList.CraftList
import com.example.graduationproject.model.shared.getAllCrafts.GetAllCrafts
import com.example.graduationproject.model.shared.getCraft.GetCraft
import com.example.graduationproject.model.shared.getCraftOfWorker.GetCraftOfWorker
import com.example.graduationproject.network.GraduationApi
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class SharedRepository @Inject constructor(private val api: GraduationApi) {

    private val getAllCrafts = WrapperClass<GetAllCrafts, Boolean, Exception>()
    private val getOneCrafts = WrapperClass<GetCraft, Boolean, Exception>()
    private val getCraftList = WrapperClass<CraftList, Boolean, Exception>()
    private val getCraftOfWorker = WrapperClass<GetCraftOfWorker, Boolean, Exception>()


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


    suspend fun getOneCraft(authorization: String, craftId: String)
            : WrapperClass<GetCraft, Boolean, Exception> {
        try {
            //addNewUser.loading = true
            getOneCrafts.data = api.getCraft(authorization = authorization, craftId = craftId)
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            getOneCrafts.data = GetCraft(status = status, message = message)

        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "getAllCrafts: $e")
            getOneCrafts.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "getAllCrafts: $e")
            getOneCrafts.e = e

        }
        return getOneCrafts
    }


    suspend fun getCraftList()
            : WrapperClass<CraftList, Boolean, Exception> {
        try {
            getCraftList.data = api.getCraftList()
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            getCraftList.data = CraftList(status = status, message = message)
        } catch (e: Exception) {
            Log.d("TAG", "getCraftList: $e")
            getCraftList.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "getCraftList: $e")
            getCraftList.e = e
        }
        return getCraftList
    }

    suspend fun getCraftOfWorker(workerId: String)
    : WrapperClass<GetCraftOfWorker , Boolean , Exception>{
        try {
            getCraftOfWorker.data = api.getCraftOfWorker(workerId = workerId)
        }
        catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            getCraftOfWorker.data = GetCraftOfWorker(status = status, message = message)
        } catch (e: Exception) {
            Log.d("TAG", "getCraftOfWorker: $e")
            getCraftOfWorker.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "getCraftOfWorker: $e")
            getCraftOfWorker.e = e
        }
        return getCraftOfWorker
    }

}