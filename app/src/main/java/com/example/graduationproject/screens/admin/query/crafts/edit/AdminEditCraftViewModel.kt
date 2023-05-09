package com.example.graduationproject.screens.admin.query.crafts.edit

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.getCraft.GetCraft
import com.example.graduationproject.repository.AdminRepository
import com.example.graduationproject.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminEditCraftViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val adminRepository: AdminRepository
) : ViewModel() {

    suspend fun getOneCraft(
        authorization: String,
        craftId: String
    ): WrapperClass<GetCraft, Boolean, Exception> {
        return sharedRepository.getOneCraft(authorization, craftId)
    }



}