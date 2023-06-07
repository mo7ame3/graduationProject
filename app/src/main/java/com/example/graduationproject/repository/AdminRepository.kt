package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.admin.createCraft.CreateNewCraft
import com.example.graduationproject.model.admin.deleteCraft.Delete
import com.example.graduationproject.model.admin.updateCraft.UpdateCraft
import com.example.graduationproject.network.GraduationApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.lang.NullPointerException
import java.net.SocketTimeoutException
import javax.inject.Inject

class AdminRepository @Inject constructor(private val api: GraduationApi) {
    private val createNewCraft = WrapperClass<CreateNewCraft, Boolean, Exception>()
    private val updateCraft = WrapperClass<UpdateCraft, Boolean, Exception>()
    private val delete = WrapperClass<Delete, Boolean, Exception>()


    suspend fun createNewCraft(authorization: String, name: RequestBody, image: MultipartBody.Part)
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
            Log.d("TAG", "createNewCraft: $e")
            createNewCraft.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "createNewCraft: $e")
            createNewCraft.e = e

        }
        return createNewCraft
    }

    suspend fun updateCraft(
        authorization: String,
        name: RequestBody? = null,
        image: MultipartBody.Part? = null,
        craftId: String
    ): WrapperClass<UpdateCraft, Boolean, Exception> {
        try {
            //addNewUser.loading = true
            updateCraft.data = api.updateCraft(
                authorization = authorization,
                name = name,
                image = image,
                craftId = craftId
            )
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            updateCraft.data = UpdateCraft(status = status, message = message)
        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "updateCraftError: $e")
            updateCraft.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "updateCraftError: $e")
            updateCraft.e = e
        }
        return updateCraft
    }

    suspend fun deleteCraft(
        authorization: String,
        craftId: String
    ): WrapperClass<Delete, Boolean, Exception> {
        try {
            //addNewUser.loading = true
            delete.data = api.deleteCraft(
                craftId = craftId,
                authorization = authorization
            )
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            delete.data = Delete(status = status, message = message)
        }
        catch (e : NullPointerException){
            delete.data = Delete(status = "success")
        }
        catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "deleteCraft: $e")
            delete.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "deleteCraft: $e")
            delete.e = e
        }
        return delete
    }


}