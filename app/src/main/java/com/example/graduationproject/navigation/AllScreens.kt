package com.example.graduationproject.navigation

enum class AllScreens {
    //shared

    SplashScreen,
    LoginScreen,
    ReportScreen,
    ForgotPasswordScreen,
    ChangePasswordScreen,

    //client

    ClientHomeScreen,
    ClientPostScreen,
    ClientMyCraftOrders,
    ClientProfileScreen,
    ClientProfileSettingsScreen,
    ClientOrderOfferScreen,
    ClientRateScreen,
    ClientChatDetails,

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