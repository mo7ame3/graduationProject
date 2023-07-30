package com.example.graduationproject.model.shared.profile

data class User(
    val _id: String,
    val address: String,
    val avatar: String? = null,
    val id: String,
    val name: String,
    val bio: String? = null,
    val rate: Int? = null,
)