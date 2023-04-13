package com.example.graduationproject.data

data class AdminUsersQuery(
    val name: String,
    val report: Int = 0,
    val rate: Int = 0,
    val id: Int,
    val reportList: List<AdminUserReport>? = null
)
