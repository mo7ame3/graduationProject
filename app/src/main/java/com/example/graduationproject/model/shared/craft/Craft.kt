package com.example.graduationproject.model.shared.craft

import com.example.graduationproject.model.shared.getCraft.Order

data class Craft(
    val __v: Int? = null,
    val _id: String,
    val avatar: String,
    val cloudinary_id: String? = null,
    val createdAt: String? = null,
    val id: String,
    val name: String,
    val orders: List<Order>? = null,
    val updatedAt: String? = null,
    val workers: List<Any> ? = null
)