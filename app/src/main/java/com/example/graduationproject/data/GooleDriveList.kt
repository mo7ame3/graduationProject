package com.example.graduationproject.data

data class GoogleDriveList(
    val id: Int,
    val jobName: String,
    val picGoogle: String,
    val numberOfWorkers: Int,
    val craftTitle: String,
    val workerList: List<AdminUsersQuery>,
    val allWorker: String //
)