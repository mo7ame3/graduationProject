package com.example.graduationproject.repository

import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.Register
import com.example.graduationproject.network.GraduationApi
import retrofit2.HttpException
import javax.inject.Inject

class GraduationRepository @Inject constructor(private val api: GraduationApi) {


    private val addNewUser = WrapperClass<Register, Boolean, Exception>()

    suspend fun addNewUser(register: Map<String, String>)
            : WrapperClass<Register, Boolean, Exception> {
        try {
            addNewUser.loeading = true
            addNewUser.data = api.register(register)
        } catch (e: HttpException) {
            addNewUser.loeading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            addNewUser.data = Register(status = status, message = message)
        } catch (e: Exception) {
            addNewUser.loeading = false
            addNewUser.e = e
        }
        return addNewUser
    }


}