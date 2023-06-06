package com.example.graduationproject.screens.worker.problemDetails

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.worker.orderDetails.GetOrderDetails
import com.example.graduationproject.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkerProblemDetailsViewModel @Inject constructor(private val workerRepository: WorkerRepository) :
    ViewModel() {
    suspend fun getOrderDetails(
        craftId: String, authorization: String,
        orderId: String
    ): WrapperClass<GetOrderDetails, Boolean, Exception> {
        return workerRepository.getOrderDetails(
            craftId = craftId,
            authorization = authorization,
            orderId = orderId
        )
    }
}