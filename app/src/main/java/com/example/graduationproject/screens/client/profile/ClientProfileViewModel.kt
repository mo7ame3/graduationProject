package com.example.graduationproject.screens.client.profile

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.model.shared.updateProflePhoto.UpdateProfilePhoto
import com.example.graduationproject.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ClientProfileViewModel @Inject constructor(private val sharedRepository: SharedRepository) :
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
}