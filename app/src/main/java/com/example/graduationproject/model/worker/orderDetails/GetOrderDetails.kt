package com.example.graduationproject.model.worker.orderDetails

data class GetOrderDetails(
    val data: Data?= null,
    val status: String,
    val message: String? =null,
)