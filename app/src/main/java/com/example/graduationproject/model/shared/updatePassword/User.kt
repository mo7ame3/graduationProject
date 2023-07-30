package com.example.graduationproject.model.shared.updatePassword

data class User(
    val ImagesOfWorks: List<Any>,
    val __v: Int,
    val _id: String,
    val address: String,
    val avatar: String,
    val bio: String,
    val cloudinary_id: String,
    val createdAt: String,
    val email: String,
    val id: String,
    val isAdmin: Boolean,
    val myCraft: String,
    val name: String,
    val offers: List<Any>,
    val orders: List<Any>,
    val password: String,
    val passwordChangedAt: String,
    val rate: Int,
    val reports: List<Any>,
    val role: String,
    val tokens: List<Any>,
    val updatedAt: String
)