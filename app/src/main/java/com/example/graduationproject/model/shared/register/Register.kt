package com.example.graduationproject.model.shared.register

data class Register(
    val data: Data? = null,
    val status: String,
    val token: String? = null,
    val message: String? = null
)