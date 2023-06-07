package com.example.graduationproject.screens.client.postScreen

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.client.creatOrder.CreateOrder
import com.example.graduationproject.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val clientRepository: ClientRepository) :
    ViewModel() {


    suspend fun createOrder(
        image: MultipartBody.Part,
        title: RequestBody,
        description: RequestBody,
        orderDifficulty: RequestBody,
        token: String,
        craftId: String
    ): WrapperClass<CreateOrder, Boolean, Exception> {
        return clientRepository.createOrder(
            image = image,
            title = title,
            description = description,
            orderDifficulty = orderDifficulty,
            authorization = token,
            craftId = craftId
        )
    }

}