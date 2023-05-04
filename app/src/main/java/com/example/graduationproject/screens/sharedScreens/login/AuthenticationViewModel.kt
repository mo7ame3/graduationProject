package com.example.graduationproject.screens.sharedScreens.login

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.login.Login
import com.example.graduationproject.model.register.Register
import com.example.graduationproject.repository.GraduationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val graduationRepository: GraduationRepository) :
    ViewModel() {

    suspend fun register(
        name: String,
        email: String,
        address: String,
        role: String,
        myCraft: String,
        password: String,
        passwordConfirm: String
    ): WrapperClass<Register, Boolean, Exception> {
        return graduationRepository.addNewUser(
            register = mapOf(
                "name" to name,
                "email" to email,
                "address" to address,
                "myCraft" to myCraft,
                "role" to role,
                "password" to password,
                "passwordConfirm" to passwordConfirm
            )
        )
    }

    suspend fun login(
        email: String,
        password: String,
    ): WrapperClass<Login, Boolean, Exception> {
        return graduationRepository.addLoggedInUser(
            login = mapOf(
                "email" to email,
                "password" to password,
            )
        )
    }
}