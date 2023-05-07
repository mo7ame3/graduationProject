package com.example.graduationproject.screens.client.home

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientHomeViewModel @Inject constructor(private val clientRepository: ClientRepository) :
    ViewModel() {

    suspend fun getAllCrafts(token: String): WrapperClass<GetAllCrafts, Boolean, Exception> {
        return clientRepository.getAllCrafts(token)
    }


}