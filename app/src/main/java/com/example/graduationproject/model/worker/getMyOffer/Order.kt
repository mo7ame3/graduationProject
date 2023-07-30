package com.example.graduationproject.model.worker.getMyOffer

data class Order(
    val _id: String,
    val orderDifficulty: String,
    val title: String,
    val user: User
)