package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.shared.register.Register
import com.example.graduationproject.model.worker.home.WorkerHome
import com.example.graduationproject.network.GraduationApi
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class WorkerRepository@Inject constructor(private val api: GraduationApi) {

    private val getHome = WrapperClass<WorkerHome, Boolean, Exception>()

    //-----------------------
    suspend fun getHome(craftId:String,authorization:String):WrapperClass<WorkerHome,Boolean,Exception>{

        try {
            getHome.data = api.getWorkerHome(craftId, authorization)
        }
        catch (e:Exception){
            getHome.e=e
            Log.d("TAG", "getHome: $e")
        }
        catch (e: SocketTimeoutException){
            getHome.e=e
            Log.d("TAG", "getHome: $e")

        }

        return getHome
    }

}