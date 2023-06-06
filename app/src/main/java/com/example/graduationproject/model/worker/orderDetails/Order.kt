package com.example.graduationproject.model.worker.orderDetails

data class Order(
    val _id: String,
    val avatar: String? = null,
    val cloudinary_id: String,
    val createdDate: String,
    val description: String,
    val orderDifficulty: String,
    val title: String,
    val user: User
)