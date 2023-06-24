package com.example.graduationproject.screens.worker.myOffers

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.worker.getMyOffer.GetMyOffer
import com.example.graduationproject.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyOffersViewModel @Inject constructor(private val workerRepository: WorkerRepository) :
    ViewModel() {

    suspend fun getMyOffers(
        authorization: String,
        userId: String
    ): WrapperClass<GetMyOffer, Boolean, Exception> {
        return workerRepository.getMyOffer(authorization = authorization, userId = userId)
    }
}