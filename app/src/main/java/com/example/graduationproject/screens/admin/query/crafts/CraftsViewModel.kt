package com.example.graduationproject.screens.admin.query.crafts

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.getAllCrafts.GetAllCrafts
import com.example.graduationproject.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CraftsViewModel @Inject constructor(private val sharedRepository: SharedRepository) :
    ViewModel() {

    suspend fun getAllCrafts(token: String): WrapperClass<GetAllCrafts, Boolean, Exception> {
        return sharedRepository.getAllCrafts(token)
    }

}