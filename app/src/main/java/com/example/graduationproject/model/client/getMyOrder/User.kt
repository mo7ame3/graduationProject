package com.example.graduationproject.model.client.getMyOrder

data class User(
    val _id: String,
    val address: String,
    val avatar: String ? = null,
    val id: String,
    val name: String
)