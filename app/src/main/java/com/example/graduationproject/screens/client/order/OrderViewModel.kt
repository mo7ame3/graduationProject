package com.example.graduationproject.screens.client.order

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.craftList.CraftList
import com.example.graduationproject.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val sharedRepository: SharedRepository) :
    ViewModel() {
    suspend fun getCraftList(): WrapperClass<CraftList, Boolean, Exception> {
        return sharedRepository.getCraftList()

    }
}