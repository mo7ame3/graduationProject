package com.example.graduationproject.model.worker.getMyOffer

data class Data(
    val _id: String,
    val id: String,
    val order: Order,
    val status: String,
    val text: String,
    val worker: Worker
)