package com.example.graduationproject.screens.admin.query.crafts.edit

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.admin.deleteCraft.DeleteCraft
import com.example.graduationproject.model.admin.updateCraft.UpdateCraft
import com.example.graduationproject.model.shared.getCraft.GetCraft
import com.example.graduationproject.repository.AdminRepository
import com.example.graduationproject.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AdminEditCraftViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val adminRepository: AdminRepository,
) : ViewModel() {

    suspend fun getOneCraft(
        authorization: String,
        craftId: String
    ): WrapperClass<GetCraft, Boolean, Exception> {
        return sharedRepository.getOneCraft(authorization, craftId)
    }

    suspend fun updateCraft(
        authorization: String,
        name: RequestBody? = null,
        image: MultipartBody.Part? = null,
        craftId: String
    ): WrapperClass<UpdateCraft, Boolean, Exception> {
        return adminRepository.updateCraft(
            authorization = authorization,
            name = name,
            image = image,
            craftId = craftId
        )
    }

    suspend fun deleteCraft(
        authorization: String,
        craftId: String
    ): WrapperClass<DeleteCraft, Boolean, Exception> {
        return adminRepository.deleteCraft(
            authorization = authorization,
            craftId = craftId
        )
    }

}