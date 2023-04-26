package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.Register
import com.example.graduationproject.network.GraduationApi
import javax.inject.Inject

class GraduationRepository @Inject constructor(private val api: GraduationApi) {


    private val addNewUser = WrapperClass<Register, Boolean, Exception>()

    suspend fun addNewUser(register: Map<String, String>)
            : WrapperClass<Register, Boolean, Exception> {
        try {
            addNewUser.loeading = true
            addNewUser.data = api.register(register)
            addNewUser.loeading = false
        } catch (e: Exception) {
            Log.d("TAG", "register: ${e.message}")
            addNewUser.e = e
        }
        return addNewUser
    }


}