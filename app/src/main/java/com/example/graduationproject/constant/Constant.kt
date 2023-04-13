package com.example.graduationproject.constant

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import com.example.graduationproject.R
import com.example.graduationproject.data.AdminUserReport
import com.example.graduationproject.data.AdminUsersQuery
import com.example.graduationproject.data.BlockData
import com.example.graduationproject.data.GoogleDriveList

object Constant {
    const val title = "خدماتي"
    const val logo = R.drawable.home
    val adminCraftList = listOf(
        GoogleDriveList(
            jobName = "نجار",
            picGoogle = "https://drive.google.com/uc?id=1RWfee5Pf3LTODPqHOxKPOAHy79245zVy",
            id = 0,
            craftTitle = "خدمة النجارة",
            numberOfWorkers = 0,
            workerList = listOf(
                AdminUsersQuery(name = "أحمد محمد", report = 2, rate = 5, id = 0),
                AdminUsersQuery(name = "محمد أحمد", report = 1, rate = 2, id = 1),
                AdminUsersQuery(name = "جمعة", report = 2, rate = 3, id = 2),
                AdminUsersQuery(name = "عباس", report = 21, rate = 1, id = 3),
                AdminUsersQuery(name = "شعبان", report = 3, rate = 2, id = 4),
                AdminUsersQuery(name = "رمضان", report = 1, rate = 2, id = 5),
                AdminUsersQuery(name = "صلاح أحمد", report = 1, rate = 3, id = 6),
            ),
            allWorker = "جميع النجارين"
        ),
        GoogleDriveList(
            jobName = "سباك",
            picGoogle = "https://drive.google.com/uc?id=1EQPFlymQnubzmpaJzjRB34vkqkumxMbO",
            id = 1,
            craftTitle = "خدمة السباكة",
            numberOfWorkers = 0,
            workerList = listOf(
                AdminUsersQuery(name = "أحمد محمد", report = 2, rate = 5, id = 0),
                AdminUsersQuery(name = "محمد أحمد", report = 12, rate = 2, id = 1),
                AdminUsersQuery(name = "جمعة", report = 2, rate = 3, id = 2),
                AdminUsersQuery(name = "عباس", report = 2, rate = 1, id = 3),
                AdminUsersQuery(name = "شعبان", report = 3, rate = 2, id = 4),
                AdminUsersQuery(name = "رمضان", report = 1, rate = 2, id = 5),
                AdminUsersQuery(name = "صلاح أحمد", report = 10, rate = 3, id = 6),
            ),
            allWorker = "جميع السباكين"

        ),
        GoogleDriveList(
            jobName = "كهربائي",
            picGoogle = "https://drive.google.com/uc?id=1Myp6ZI5rsRuzmybv6vhBCb1AlVL-PoZG",
            id = 2,
            craftTitle = "خدمة الكهرباء",
            numberOfWorkers = 40,
            workerList = listOf(
                AdminUsersQuery(name = "أحمد محمد", report = 2, rate = 5, id = 0),
                AdminUsersQuery(name = "محمد أحمد", report = 1, rate = 2, id = 1),
                AdminUsersQuery(name = "جمعة", report = 2, rate = 3, id = 2),
                AdminUsersQuery(name = "عباس", report = 2, rate = 1, id = 3),
                AdminUsersQuery(name = "شعبان", report = 3, rate = 2, id = 4),
                AdminUsersQuery(name = "رمضان", report = 1, rate = 2, id = 5),
                AdminUsersQuery(name = "صلاح أحمد", report = 10, rate = 3, id = 6),
            ),
            allWorker = "جميع الكهربائين"
        ),
        GoogleDriveList(
            jobName = "عامل نظافة",
            picGoogle = "https://drive.google.com/uc?id=1E7uQZv7yL-BYsIEueH3s4txnG4ERWt_i",
            id = 3,
            craftTitle = "خدمة النظافة",
            numberOfWorkers = 5,
            workerList = listOf(
                AdminUsersQuery(name = "أحمد محمد", report = 2, rate = 5, id = 0),
                AdminUsersQuery(name = "محمد أحمد", report = 1, rate = 2, id = 1),
                AdminUsersQuery(name = "جمعة", report = 2, rate = 3, id = 2),
                AdminUsersQuery(name = "عباس", report = 12, rate = 1, id = 3),
                AdminUsersQuery(name = "شعبان", report = 3, rate = 2, id = 4),
                AdminUsersQuery(name = "رمضان", report = 1, rate = 2, id = 5),
                AdminUsersQuery(name = "صلاح أحمد", report = 10, rate = 3, id = 6),
            ),
            allWorker = "جميع عمال النظافة"
        ),
        GoogleDriveList(
            jobName = "حداد",
            picGoogle = "https://drive.google.com/uc?id=1rOAw-hs7ZlRGCoyfE0TY_k-d38hiRSqQ",
            id = 4,
            craftTitle = "خدمة الحدادة",
            numberOfWorkers = 40,
            workerList = listOf(
                AdminUsersQuery(name = "أحمد محمد", report = 2, rate = 5, id = 0),
                AdminUsersQuery(name = "محمد أحمد", report = 1, rate = 2, id = 1),
                AdminUsersQuery(name = "جمعة", report = 2, rate = 3, id = 2),
                AdminUsersQuery(name = "عباس", report = 2, rate = 1, id = 3),
                AdminUsersQuery(name = "شعبان", report = 13, rate = 2, id = 4),
                AdminUsersQuery(name = "رمضان", report = 1, rate = 2, id = 5),
                AdminUsersQuery(name = "صلاح أحمد", report = 10, rate = 3, id = 6),
            ),
            allWorker = "جميع الحدادين"
        ),
        GoogleDriveList(
            jobName = "صيانة اجهزة كهربائية",
            picGoogle = "https://drive.google.com/uc?id=1MFBp4RBc5UySt1ie73sU2dsYt0LElRkO",
            id = 5,
            craftTitle = "خدمة الصيانة",
            numberOfWorkers = 40,
            workerList = listOf(
                AdminUsersQuery(name = "أحمد محمد", report = 2, rate = 5, id = 0),
                AdminUsersQuery(name = "محمد أحمد", report = 1, rate = 2, id = 1),
                AdminUsersQuery(name = "جمعة", report = 12, rate = 3, id = 2),
                AdminUsersQuery(name = "عباس", report = 2, rate = 1, id = 3),
                AdminUsersQuery(name = "شعبان", report = 3, rate = 2, id = 4),
                AdminUsersQuery(name = "رمضان", report = 1, rate = 2, id = 5),
                AdminUsersQuery(name = "صلاح أحمد", report = 10, rate = 3, id = 6),
            ),
            allWorker = "جميع عمال الصيانة"
        ),
        GoogleDriveList(
            jobName = "عامل بناء",
            picGoogle = "https://drive.google.com/uc?id=1cXfjayL32kQSDWzr-sPQcj7BRVz-MS6d",
            id = 6,
            craftTitle = "خدمة البناء",
            numberOfWorkers = 10,
            workerList = listOf(
                AdminUsersQuery(name = "أحمد محمد", report = 2, rate = 5, id = 0),
                AdminUsersQuery(name = "محمد أحمد", report = 1, rate = 2, id = 1),
                AdminUsersQuery(name = "جمعة", report = 2, rate = 3, id = 2),
                AdminUsersQuery(name = "عباس", report = 12, rate = 1, id = 3),
                AdminUsersQuery(name = "شعبان", report = 3, rate = 2, id = 4),
                AdminUsersQuery(name = "رمضان", report = 1, rate = 2, id = 5),
                AdminUsersQuery(name = "صلاح أحمد", report = 10, rate = 3, id = 6),
            ),
            allWorker = "جميع عمال البناء"
        ),
        GoogleDriveList(
            jobName = "نقاش",
            picGoogle = "https://drive.google.com/uc?id=1AEwyg6IeChpa-Lq62gqfez-pWkmzeYF3",
            id = 7,
            craftTitle = "خدمة النقاشة",
            numberOfWorkers = 0,
            workerList = listOf(
                AdminUsersQuery(name = "أحمد محمد", report = 12, rate = 5, id = 0),
                AdminUsersQuery(name = "محمد أحمد", report = 1, rate = 2, id = 1),
                AdminUsersQuery(name = "جمعة", report = 2, rate = 3, id = 2),
                AdminUsersQuery(name = "عباس", report = 2, rate = 1, id = 3),
                AdminUsersQuery(name = "شعبان", report = 3, rate = 2, id = 4),
                AdminUsersQuery(name = "رمضان", report = 1, rate = 2, id = 5),
                AdminUsersQuery(name = "صلاح أحمد", report = 10, rate = 3, id = 6),
            ),
            allWorker = "جميع النقاشين"
        )
    )
    const val reportLimit = 5
    val adminAllWorkerDetails = listOf(
        AdminUsersQuery(
            name = "أحمد محمد", report = 0, rate = 5, id = 0
        ),
        AdminUsersQuery(
            name = "محمد أحمد", report = 1, rate = 2, id = 1, reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "جمعة", report = 2, rate = 3, id = 2, reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
            )
        ),
        AdminUsersQuery(
            name = "عباس", report = 5, rate = 1, id = 3, reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
                AdminUserReport(name = "محمود محمد", id = 3),
                AdminUserReport(name = "محمود محمد", id = 4),
            )
        ),
        AdminUsersQuery(
            name = "شعبان", report = 3, rate = 2, id = 4, reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
            )
        ),
        AdminUsersQuery(
            name = "رمضان", report = 1, rate = 2, id = 5,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "صلاح أحمد", report = 1, rate = 3, id = 6, reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "أحمد محمد", report = 6, rate = 5, id = 7,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
                AdminUserReport(name = "محمود محمد", id = 3),
                AdminUserReport(name = "محمود محمد", id = 4),
                AdminUserReport(name = "محمود محمد", id = 5),
            )
        ),
        AdminUsersQuery(
            name = "محمد أحمد", report = 1, rate = 2, id = 8, reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "جمعة", report = 2, rate = 3, id = 9, reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
            )
        ),
        AdminUsersQuery(
            name = "عباس", report = 2, rate = 1, id = 10, reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
            )
        ),
        AdminUsersQuery(
            name = "شعبان", report = 3, rate = 2, id = 11,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
            )
        ),
        AdminUsersQuery(
            name = "رمضان", report = 1, rate = 2, id = 12, reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "صلاح أحمد", report = 7, rate = 3, id = 13,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
                AdminUserReport(name = "محمود محمد", id = 3),
                AdminUserReport(name = "محمود محمد", id = 4),
                AdminUserReport(name = "محمود محمد", id = 5),
                AdminUserReport(name = "محمود محمد", id = 6),
            )
        ),
    )
    val adminAllClientDetails = listOf(
        AdminUsersQuery(
            name = "أحمد محمد", report = 0, id = 0,
        ),
        AdminUsersQuery(
            name = "محمد أحمد", report = 1, id = 1,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "جمعة", report = 2, id = 2,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
            )
        ),
        AdminUsersQuery(
            name = "عباس", report = 6, id = 3,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
                AdminUserReport(name = "محمود محمد", id = 3),
                AdminUserReport(name = "محمود محمد", id = 4),
                AdminUserReport(name = "محمود محمد", id = 5),
            )
        ),
        AdminUsersQuery(
            name = "شعبان", report = 3, id = 4,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
            )
        ),
        AdminUsersQuery(
            name = "رمضان", report = 1, id = 5,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "صلاح أحمد", report = 1, id = 6,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "أحمد محمد", report = 7, id = 7,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
                AdminUserReport(name = "محمود محمد", id = 3),
                AdminUserReport(name = "محمود محمد", id = 4),
                AdminUserReport(name = "محمود محمد", id = 5),
                AdminUserReport(name = "محمود محمد", id = 6),
            )
        ),
        AdminUsersQuery(
            name = "محمد أحمد", report = 1, id = 8,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "جمعة", report = 2, id = 9,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
            )
        ),
        AdminUsersQuery(
            name = "عباس", report = 2, id = 10,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
            )
        ),
        AdminUsersQuery(
            name = "شعبان", report = 3, id = 11,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
            )
        ),
        AdminUsersQuery(
            name = "رمضان", report = 1, id = 12,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
            )
        ),
        AdminUsersQuery(
            name = "صلاح أحمد", report = 5, id = 13,
            reportList = listOf(
                AdminUserReport(name = "محمود إبراهيم", id = 0),
                AdminUserReport(name = "أحمد محمد", id = 1),
                AdminUserReport(name = "محمد أحمد", id = 2),
                AdminUserReport(name = "محمود محمد", id = 3),
                AdminUserReport(name = "محمود محمد", id = 4),
            )
        ),
    )

    //Admin Block list
    val adminBlockList = listOf(
        BlockData(name = "أحمد محمد", image = Icons.Default.Lock),
        BlockData(name = "أحمد محمد", time = "5hours"),
        BlockData(name = "أحمد محمد", time = "10hours"),
        BlockData(name = "أحمد محمد", time = "1day"),
        BlockData(name = "أحمد محمد", time = "2days"),
        BlockData(name = "أحمد محمد", time = "3days"),
        BlockData(name = "أحمد محمد", time = "3days"),
        BlockData(name = "أحمد محمد", time = "3days"),
    )

    val reportList = listOf(
        "شخص مزيف",
        "سلوك غير لائق",
        "عدم التزام بالمواعيد",
        "سعر غير مناسب",
        "عمل غير متقن",
        "وصف غير دقيق للمشكلة",
        "عامل غير كفء",
    )
}