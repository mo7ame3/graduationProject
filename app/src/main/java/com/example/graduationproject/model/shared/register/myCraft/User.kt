package com.example.graduationproject.model.shared.register.myCraft

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
    val reports: List<Any>,
    val role: String,
    val tokens: List<Any>,
    val updatedAt: String
)