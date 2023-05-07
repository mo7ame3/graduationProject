package com.example.graduationproject.screens.admin.query.jobs

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(private val adminRepository: AdminRepository) :
    ViewModel() {

    suspend fun getAllCrafts(token: String): WrapperClass<GetAllCrafts, Boolean, Exception> {
        return adminRepository.getAllCrafts(token)
    }


}