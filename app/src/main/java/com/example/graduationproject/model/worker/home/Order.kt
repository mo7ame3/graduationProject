package com.example.graduationproject.model.worker.home

data class Order(
    val _id: String,
    val createdDate: String,
    val orderDifficulty: String,
    val title: String,
    val user: User
)