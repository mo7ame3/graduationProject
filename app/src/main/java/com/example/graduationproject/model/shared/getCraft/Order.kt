package com.example.graduationproject.model.shared.getCraft

data class Order(
    val __v: Int,
    val _id: String,
    val avatar: String,
    val cloudinary_id: String,
    val craft: String,
    val createDate: String,
    val createdAt: String,
    val description: String,
    val offers: List<Any>,
    val orderDifficulty: String,
    val orderDone: Boolean,
    val orderHavingOffers: Boolean,
    val title: String,
    val updatedAt: String,
    val user: User
)