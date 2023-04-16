package com.example.graduationproject.navigation

enum class AllScreens {
    //shared

    SplashScreen,
    LoginScreen,
    ReportScreen,
    ChatDetails,

    //client

    ClientHomeScreen,
    ClientPostScreen,
    ClientMyCraftOrders,
    ClientProfileScreen,
    ClientProfileSettingsScreen,
    ClientOrderOfferScreen,
    ClientRateScreen,

    //worker

    WorkerHomeScreen,
    WorkerProblemDetilas,
    WorkerProfileScreen,
    WorkerProfileSettingsScreen,
    MyProjectProblemDetails,

    //Admin

    AdminHomeScreen,
    AdminJobsScreen,
    AdminEditJobsScreen,
    AdminAllWorkersInSpecificJob,
    AdminAllWorkers,
    AdminAllClients,
    AdminBlockedUsers,
    AdminReportsQuery,
    AdminReportListQuery
}