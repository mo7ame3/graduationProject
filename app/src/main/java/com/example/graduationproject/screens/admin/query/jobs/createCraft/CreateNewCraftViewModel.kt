package com.example.graduationproject.screens.admin.query.jobs.createCraft

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.admin.createCraft.CreateNewCraft
import com.example.graduationproject.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CreateNewCraftViewModel @Inject constructor(private val repository: AdminRepository) :
    ViewModel() {

    suspend fun createNewCraft(
        token: String,
        name: RequestBody,
        image: MultipartBody.Part
    ): WrapperClass<CreateNewCraft, Boolean, Exception> {
        return repository.createNewCraft(
            authorization = token,
            name = name,
            image = image
        )
    }

}