package com.example.graduationproject.model.login

data class Login(
    val data: Data?=null,
    val status: String,
    val token: String?=null,
    val message: String?=null
)