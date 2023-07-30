package com.example.graduationproject.model.client.updateOrder

data class Order(
    val __v: Int,
    val _id: String,
    val avatar: String,
    val cloudinary_id: String,
    val craft: String,
    val createdAt: String,
    val createdDate: String,
    val description: String,
    val offers: List<Any>,
    val orderDifficulty: String,
    val orderHavingOffers: Boolean,
    val orderedTime: String,
    val status: String,
    val title: String,
    val updatedAt: String,
    val user: User
)