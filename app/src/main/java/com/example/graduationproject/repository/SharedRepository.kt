package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.craftList.CraftList
import com.example.graduationproject.model.shared.getAllCrafts.GetAllCrafts
import com.example.graduationproject.model.shared.getCraft.GetCraft
import com.example.graduationproject.model.shared.getCraftOfWorker.GetCraftOfWorker
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.model.shared.updateOffer.UpdateOffer
import com.example.graduationproject.model.shared.updatePassword.UpdatePassword
import com.example.graduationproject.model.shared.updateProfileData.UpdateProfileData
import com.example.graduationproject.model.shared.updateProflePhoto.UpdateProfilePhoto
import com.example.graduationproject.network.GraduationApi
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class SharedRepository @Inject constructor(private val api: GraduationApi) {

    private val getAllCrafts = WrapperClass<GetAllCrafts, Boolean, Exception>()
    private val getOneCrafts = WrapperClass<GetCraft, Boolean, Exception>()
    private val getCraftList = WrapperClass<CraftList, Boolean, Exception>()
    private val getCraftOfWorker = WrapperClass<GetCraftOfWorker, Boolean, Exception>()
    private val updateOffer = WrapperClass<UpdateOffer, Boolean, Exception>()
    private val getProfile = WrapperClass<GetProfile, Boolean, Exception>()
    private val updateProfilePhoto = WrapperClass<UpdateProfilePhoto, Boolean, Exception>()
    private val updateProfileData = WrapperClass<UpdateProfileData, Boolean, Exception>()
    private val updatePassword = WrapperClass<UpdatePassword, Boolean, Exception>()


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
            : WrapperClass<GetCraftOfWorker, Boolean, Exception> {
        try {
            getCraftOfWorker.data = api.getCraftOfWorker(workerId = workerId)
        } catch (e: HttpException) {
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

    suspend fun updateOffer(offerId: String, authorization: String, updateBody: Map<String, String>)
            : WrapperClass<UpdateOffer, Boolean, Exception> {
        try {
            updateOffer.data = api.updateOffer(
                offerId = offerId,
                authorization = authorization,
                updateBody = updateBody
            )
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            updateOffer.data = UpdateOffer(status = status, message = message)
        } catch (e: Exception) {
            Log.d("TAG", "updateOffer: $e")
            updateOffer.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "updateOffer: $e")
            updateOffer.e = e
        }
        return updateOffer
    }

    suspend fun getProfile(userId: String, authorization: String)
            : WrapperClass<GetProfile, Boolean, Exception> {
        try {
            getProfile.data = api.getProfile(userId = userId, authorization = authorization)
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            getProfile.data = GetProfile(status = status, message = message)
        } catch (e: Exception) {
            Log.d("TAG", "getCraftOfWorker: $e")
            getProfile.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "getCraftOfWorker: $e")
            getProfile.e = e
        }
        return getProfile
    }

    suspend fun updateProfilePhoto(
        userId: String,
        authorization: String,
        image: MultipartBody.Part
    ): WrapperClass<UpdateProfilePhoto, Boolean, Exception> {
        try {
            updateProfilePhoto.data = api.updateProfilePhoto(
                userId = userId,
                authorization = authorization,
                image = image
            )
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            updateProfilePhoto.data = UpdateProfilePhoto(status = status, message = message)
        } catch (e: Exception) {
            Log.d("TAG", "getCraftOfWorker: $e")
            updateProfilePhoto.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "getCraftOfWorker: $e")
            updateProfilePhoto.e = e
        }
        return updateProfilePhoto
    }

    suspend fun updateProfileData(
        userId: String,
        authorization: String,
        updateProfileBody: Map<String, String>
    ): WrapperClass<UpdateProfileData, Boolean, Exception> {
        try {
            updateProfileData.data = api.updateProfileData(
                userId = userId,
                authorization = authorization,
                updateProfileBody = updateProfileBody
            )
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            updateProfileData.data = UpdateProfileData(status = status, message = message)
        } catch (e: Exception) {
            Log.d("TAG", "updateProfileData: $e")
            updateProfileData.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "updateProfileData: $e")
            updateProfileData.e = e
        }
        return updateProfileData
    }

    suspend fun updatePassword(
        authorization: String,
        updatePasswordBody: Map<String, String>
    ): WrapperClass<UpdatePassword, Boolean, Exception> {
        try {
            updatePassword.data = api.updatePassword(
                authorization = authorization,
                updatePasswordBody = updatePasswordBody
            )
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            updatePassword.data = UpdatePassword(status = status, message = message)
        } catch (e: Exception) {
            Log.d("TAG", "updatePassword: $e")
            updatePassword.e = e
        } catch (e: SocketTimeoutException) {
            Log.d("TAG", "updatePassword: $e")
            updatePassword.e = e
        }
        return updatePassword
    }
}