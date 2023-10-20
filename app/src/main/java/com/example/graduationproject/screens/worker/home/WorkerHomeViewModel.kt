package com.example.graduationproject.screens.worker.home

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.worker.home.WorkerHome
import com.example.graduationproject.repository.WorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkerHomeViewModel @Inject constructor(private val workerRepository: WorkerRepository) :
    ViewModel() {

    suspend fun getHome(
        craftId: String,
        authorization: String
    ): WrapperClass<WorkerHome, Boolean, Exception> {
        return workerRepository.getHome(craftId = craftId, authorization = authorization)
    }


}