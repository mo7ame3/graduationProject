package com.example.graduationproject.model.admin.createCraft

data class Craft(
    val __v: Int,
    val _id: String,
    val avatar: String,
    val cloudinary_id: String,
    val createdAt: String,
    val id: String,
    val name: String,
    val orders: List<Any>,
    val updatedAt: String,
    val workers: List<Any>
)