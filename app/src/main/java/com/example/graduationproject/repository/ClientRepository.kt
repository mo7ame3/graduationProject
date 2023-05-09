package com.example.graduationproject.repository

import android.util.Log
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.getAllCrafts.GetAllCrafts
import com.example.graduationproject.network.GraduationApi
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ClientRepository @Inject constructor(private val api: GraduationApi) {

}