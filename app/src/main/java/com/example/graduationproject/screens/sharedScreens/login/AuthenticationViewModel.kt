package com.example.graduationproject.screens.sharedScreens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.craftList.CraftList
import com.example.graduationproject.model.shared.getCraftOfWorker.GetCraftOfWorker
import com.example.graduationproject.model.shared.login.Login
import com.example.graduationproject.model.shared.register.Register
import com.example.graduationproject.model.shared.register.myCraft.MyCraft
import com.example.graduationproject.repository.AuthenticationRepository
import com.example.graduationproject.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val sharedRepository: SharedRepository
) :
    ViewModel() {

    val craftList: MutableState<WrapperClass<CraftList, Boolean, Exception>> = mutableStateOf(
        WrapperClass()
    )

    init {
        getCraftList()
    }


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


    private fun getCraftList(){
    viewModelScope.launch {
        craftList.value = sharedRepository
            .getCraftList()
    }
    }


    suspend fun getCraftOfWorker(
        workerId : String
    ) : WrapperClass<GetCraftOfWorker , Boolean , Exception>{
        return sharedRepository.getCraftOfWorker(workerId = workerId)
    }

}
