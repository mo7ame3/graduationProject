package com.example.graduationproject.screens.worker.profile

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.profile.GetProfile
import com.example.graduationproject.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkerProfileViewModel @Inject constructor(private val sharedRepository: SharedRepository) :
    ViewModel() {

    suspend fun getProfile(
        userId: String,
        authorization: String
    ): WrapperClass<GetProfile, Boolean, Exception> {
        return sharedRepository.getProfile(userId = userId, authorization = authorization)
    }
}