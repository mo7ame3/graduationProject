package com.example.graduationproject.screens.worker.profile

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.model.shared.updateProflePhoto.UpdateProfilePhoto
import com.example.graduationproject.model.worker.getMyOffer.GetMyOffer
import com.example.graduationproject.repository.SharedRepository
import com.example.graduationproject.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class WorkerProfileViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val workerRepository: WorkerRepository
) :
    ViewModel() {

    suspend fun getProfile(
        userId: String,
        authorization: String
    ): WrapperClass<GetProfile, Boolean, Exception> {
        return sharedRepository.getProfile(userId = userId, authorization = authorization)
    }

    suspend fun updateProfilePhoto(
        userId: String,
        authorization: String,
        image: MultipartBody.Part
    ): WrapperClass<UpdateProfilePhoto, Boolean, Exception> {
        return sharedRepository.updateProfilePhoto(
            userId = userId,
            authorization = authorization,
            image = image
        )
    }

    suspend fun getMyCompletedOffer(authorization: String , userId : String): WrapperClass<GetMyOffer, Boolean, Exception> {
        return workerRepository.getMyOffer(authorization = authorization , userId = userId)
    }
}