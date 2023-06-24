package com.example.graduationproject.screens.sharedScreens.profileSetting

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.model.shared.updatePassword.UpdatePassword
import com.example.graduationproject.model.shared.updateProfileData.UpdateProfileData
import com.example.graduationproject.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileSettingViewModel @Inject constructor(private val sharedRepository: SharedRepository) :
    ViewModel() {

    suspend fun getProfile(
        userId: String,
        authorization: String
    ): WrapperClass<GetProfile, Boolean, Exception> {
        return sharedRepository.getProfile(userId = userId, authorization = authorization)
    }

    suspend fun updateProfileSetting(
        userId: String,
        authorization: String,
        name: String,
        address: String,
        bio: String
    ): WrapperClass<UpdateProfileData, Boolean, Exception> {
        return sharedRepository.updateProfileData(
            userId = userId,
            authorization = authorization,
            updateProfileBody = mapOf(
                "name" to name,
                "address" to address,
                "bio" to bio
            )
        )
    }

    suspend fun updatePassword(
        authorization: String,
        oldPassword: String,
        password: String,
        passwordConfirm: String
    ): WrapperClass<UpdatePassword, Boolean, Exception> {
        return sharedRepository.updatePassword(
            authorization = authorization,
            updatePasswordBody = mapOf(
                "passwordCurrent" to oldPassword,
                "password" to password,
                "passwordConfirm" to passwordConfirm
            )
        )
    }
}