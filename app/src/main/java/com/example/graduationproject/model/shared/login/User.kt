package com.example.graduationproject.model.shared.login

data class User(
    val __v: Int,
    val rate: Int? = 0,
    val bio: String? = null,
    val _id: String,
    val address: String,
    val createdAt: String,
    val email: String,
    val id: String,
    val isAdmin: Boolean,
    val myCraft: String,
    val name: String,
    val offers: List<Any>,
    val orders: List<Any>,
    val password: String,
    val reports: List<Any>,
    val role: String,
    val tokens: List<Any>,
    val updatedAt: String,
    val avatar: String? = null,
)