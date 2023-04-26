package com.example.graduationproject.model

data class User(
    //date
    val createdAt: String,
    val email: String,
    val id: Int,
    val imageURL: String,
    val name: String,
    val password: String,
    //date
    val passwordChangedAt: String,
    val passwordConfirm: Any,
    val passwordResetCode: Any,
    val passwordResetExpire: Any,
    //photo
    val phone: Int,
    //date
    val updatedAt: String
)