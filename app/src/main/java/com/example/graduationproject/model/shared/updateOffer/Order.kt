package com.example.graduationproject.model.shared.updateOffer

data class Order(
    val _id: String,
    val orderDifficulty: String,
    val title: String,
    val user: User
)