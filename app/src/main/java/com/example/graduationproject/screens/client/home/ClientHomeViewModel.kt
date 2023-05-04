package com.example.graduationproject.screens.client.home

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.repository.GraduationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ClientHomeViewModel @Inject constructor(private val graduationRepository: GraduationRepository):ViewModel() {

    suspend fun getAllCrafts(token:String):WrapperClass<GetAllCrafts,Boolean,Exception>{
        return graduationRepository.getAllCrafts(token)
    }


}