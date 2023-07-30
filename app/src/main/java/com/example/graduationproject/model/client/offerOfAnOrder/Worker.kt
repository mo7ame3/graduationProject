package com.example.graduationproject.model.client.offerOfAnOrder

data class Worker(
    val __v: Int,
    val _id: String,
    val rate: Int? = 0,
    val bio: String? = null,
    val address: String,
    val avatar: String? = null,
    val createdAt: String,
    val email: String,
    val id: String,
    val isAdmin: Boolean,
    val myCraft: Any,
    val name: String,
    val offers: List<Any>,
    val orders: List<Any>,
    val reports: List<Any>,
    val role: String,
    val tokens: List<Any>,
    val updatedAt: String
)