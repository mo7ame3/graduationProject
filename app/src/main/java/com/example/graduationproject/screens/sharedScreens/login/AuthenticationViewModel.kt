package com.example.graduationproject.screens.sharedScreens.login

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.login.Login
import com.example.graduationproject.model.register.Register
import com.example.graduationproject.model.register.myCraft.MyCraft
import com.example.graduationproject.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    suspend fun register(
        name: String,
        email: String,
        address: String,
        role: String,
        password: String,
        passwordConfirm: String
    ): WrapperClass<Register, Boolean, Exception> {
        return authenticationRepository.addNewUser(
            register = mapOf(
                "name" to name,
                "email" to email,
                "address" to address,
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
        return authenticationRepository.addLoggedInUser(
            login = mapOf(
                "email" to email,
                "password" to password,
            )
        )
    }

    suspend fun workerChooseCraft(
        token: String,
        myCraft: String,
        workerId: String,
    ): WrapperClass<MyCraft, Boolean, Exception> {
        return authenticationRepository.workerChooseCraft(
            token = token, myCraft = mapOf(
                "myCraft" to myCraft
            ), workerId = workerId
        )
    }
}
