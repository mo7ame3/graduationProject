package com.example.graduationproject.model.client.offerOfAnOrder

data class Data(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val id: String,
    val order: Order,
    val status: String,
    val text: String? = null,
    val updatedAt: String,
    val worker: Worker
)