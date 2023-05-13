package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.login.Login
import com.example.graduationproject.model.shared.register.Register
import com.example.graduationproject.model.shared.register.myCraft.MyCraft
import com.example.graduationproject.network.GraduationApi
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val api: GraduationApi) {


    private val addNewUser = WrapperClass<Register, Boolean, Exception>()
    private val addLoggedInUser = WrapperClass<Login, Boolean, Exception>()
    private val workerChooseCraft = WrapperClass<MyCraft, Boolean, Exception>()

    suspend fun addNewUser(register: Map<String, String>)
            : WrapperClass<Register, Boolean, Exception> {
        try {
            // addNewUser.loading = true
            addNewUser.data = api.register(register)
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            addNewUser.data = Register(status = status, message = message)
        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "Register: $e")
            addNewUser.e = e
        }
        return addNewUser
    }

    suspend fun workerChooseCraft(workerId: String, myCraft: Map<String, String>, token: String)
            : WrapperClass<MyCraft, Boolean, Exception> {
        try {
            // addNewUser.loading = true
            workerChooseCraft.data =
                api.workerChooseCraft(workerId = workerId, myCraft = myCraft, authorization = token)
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            workerChooseCraft.data = MyCraft(status = status)
        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "workerChooseCraft: $e")
            workerChooseCraft.e = e
        }catch (e: SocketTimeoutException) {
            Log.d("TAG", "workerChooseCraft: $e")
            workerChooseCraft.e = e
        }
        return workerChooseCraft
    }

    suspend fun addLoggedInUser(login: Map<String, String>)
            : WrapperClass<Login, Boolean, Exception> {
        try {
            //addNewUser.loading = true
            addLoggedInUser.data = api.login(loginInput = login)
        } catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
            val message = error.split("message")[1].split("\":")[1]
            addLoggedInUser.data = Login(status = status, message = message)

        } catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "addLoggedInUser: $e")
            addLoggedInUser.e = e
        }catch (e: SocketTimeoutException) {
            Log.d("TAG", "addLoggedInUser: $e")
            addLoggedInUser.e = e
        }
        return addLoggedInUser
    }


}