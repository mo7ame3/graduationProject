package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.model.login.Login
import com.example.graduationproject.model.register.Register
import com.example.graduationproject.network.GraduationApi
import retrofit2.HttpException
import javax.inject.Inject

class GraduationRepository @Inject constructor(private val api: GraduationApi) {


    private val addNewUser = WrapperClass<Register, Boolean, Exception>()
    private val addLoggedInUser = WrapperClass<Login, Boolean, Exception>()
    private val getAllCrafts = WrapperClass<GetAllCrafts, Boolean, Exception>()

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
        }
        return addLoggedInUser
    }

    suspend fun getAllCrafts(authorization: String)
            : WrapperClass<GetAllCrafts, Boolean, Exception> {
        try {
            //addNewUser.loading = true
            getAllCrafts.data = api.getAllCrafts(authorization = authorization)
        }catch (e: HttpException) {
            //addNewUser.loading = true
            val error = e.response()?.errorBody()?.string()
//            val status = error!!.split("status")[1].split(":")[1].split("\"")[1]
//            val message = error.split("message")[1].split("\":")[1]
            Log.d("TAG", "getAllCrafts: $error")
            addLoggedInUser.e = e

        }
        catch (e: Exception) {
            //addNewUser.loading = false
            Log.d("TAG", "getAllCrafts: $e")
            getAllCrafts.e = e
        }
        return getAllCrafts
    }


}