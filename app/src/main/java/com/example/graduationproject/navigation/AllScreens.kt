package com.example.graduationproject.navigation

enum class AllScreens {
    //shared

    SplashScreen,
    LoginScreen,
    ReportScreen,
    ChatDetails,
    ForgotPasswordScreen,
    ChangePasswordScreen,

    //client

    ClientHomeScreen,
    ClientPostScreen,
    ClientMyCraftOrders,
    ClientProfileScreen,
    ClientMyProfileScreen,
    ClientProfileSettingsScreen,
    ClientOrderOfferScreen,
    ClientRateScreen,

    //worker

    WorkerHomeScreen,
    WorkerProblemDetails,
    WorkerProfileScreen,
    WorkerMyProfileScreen,
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
    AdminReportListQuery,
    AdminCreateNewCraft
}