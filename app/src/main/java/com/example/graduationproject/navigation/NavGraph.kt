package com.example.graduationproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.graduationproject.screens.admin.home.AdminHomeScreen
import com.example.graduationproject.screens.admin.query.blocks.AdminBlockedUsers
import com.example.graduationproject.screens.admin.query.clients.AdminAllClients
import com.example.graduationproject.screens.admin.query.jobs.AdminJobsScreen
import com.example.graduationproject.screens.admin.query.jobs.allWorkers.AdminAllWorkersInSpecificJob
import com.example.graduationproject.screens.admin.query.jobs.edit.AdminEditJobsScreen
import com.example.graduationproject.screens.admin.query.reports.AdminReportsQuery
import com.example.graduationproject.screens.admin.query.reports.query.AdminReportListQuery
import com.example.graduationproject.screens.admin.query.workers.AdminAllWorkers
import com.example.graduationproject.screens.client.home.ClientHomeScreen
import com.example.graduationproject.screens.client.order.ClientMyCraftOrders
import com.example.graduationproject.screens.client.order.ClientOrderOfferScreen
import com.example.graduationproject.screens.client.postScreen.ClientPostScreen
import com.example.graduationproject.screens.client.profile.ClientProfileScreen
import com.example.graduationproject.screens.client.profileSettings.ClientProfileSettingsScreen
import com.example.graduationproject.screens.client.rate.ClientRateScreen
import com.example.graduationproject.screens.sharedScreens.chat.ChatDetails
import com.example.graduationproject.screens.sharedScreens.login.LoginScreen
import com.example.graduationproject.screens.sharedScreens.report.ReportScreen
import com.example.graduationproject.screens.sharedScreens.splash.SplashScreen
import com.example.graduationproject.screens.worker.home.WorkerHomeScreen
import com.example.graduationproject.screens.worker.myProjects.MyProjectProblemDetails
import com.example.graduationproject.screens.worker.problemDetails.WorkerProblemDetails
import com.example.graduationproject.screens.worker.profile.WorkerProfileScreen
import com.example.graduationproject.screens.worker.profileSettings.WorkerProfileSettingsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AllScreens.SplashScreen.name
    ) {
        val postScreen = AllScreens.ClientPostScreen.name
        composable(
            route = "$postScreen/{id}", arguments = listOf(navArgument(name = "id") {
                type = NavType.StringType
            })
        ) { data ->
            data.arguments?.getString("id").let { id ->
                ClientPostScreen(navController = navController, id = id.toString())
            }
        }
        composable(route = AllScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(route = AllScreens.ClientOrderOfferScreen.name + "/{problemTitle}",
            arguments = listOf(
                navArgument(name = "problemTitle") {
                    type = NavType.StringType
                }
            )) { data ->
            ClientOrderOfferScreen(
                navController = navController,
                title = data.arguments?.getString("problemTitle")!!
            )
        }
        composable(
            route = AllScreens.ClientHomeScreen.name + "/{route}",
            arguments = listOf(navArgument(name = "route") {
                type = NavType.StringType
            })
        ) { data ->
            data.arguments!!.getString("route")?.let {
                ClientHomeScreen(
                    navController = navController, route = it
                )
            }
        }
        composable(route = AllScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(route = AllScreens.ClientProfileSettingsScreen.name) {
            ClientProfileSettingsScreen(navController = navController)
        }
        val myCraftOrders = AllScreens.ClientMyCraftOrders.name
        composable(
            route = "$myCraftOrders/{id}", arguments = listOf(navArgument(name = "id") {
                type = NavType.StringType
            })
        ) { data ->
            data.arguments?.getString("id").let { id ->
                ClientMyCraftOrders(navController = navController, id = id.toString())
            }
        }

        val profileScreen = AllScreens.ClientProfileScreen.name
        composable(
            route = "$profileScreen/{showReport}/{completeProject}/{admin}/{name}",
            arguments = listOf(navArgument(name = "showReport") {
                type = NavType.BoolType
            }, navArgument(name = "completeProject") {
                type = NavType.BoolType
            },
                navArgument(name = "admin") {
                    type = NavType.BoolType
                },
                navArgument(name = "name") {
                    type = NavType.StringType
                })
        ) { data ->
            ClientProfileScreen(
                navController = navController,
                ShowReportButton = data.arguments!!.getBoolean("showReport"),
                completeProject = data.arguments!!.getBoolean("completeProject"),
                isAdmin = data.arguments!!.getBoolean("admin"),
                clientName = data.arguments!!.getString("name").toString(),
            )
        }
        //Rate Screen
        composable(route = AllScreens.ClientRateScreen.name) {
            ClientRateScreen(navController = navController)
        }
        //Report Screen
        composable(
            route = AllScreens.ReportScreen.name + "/{client}",
            arguments = listOf(navArgument(name = "client") {
                type = NavType.BoolType
            })
        ) { data ->
            ReportScreen(
                navController = navController,
                client = data.arguments!!.getBoolean("client")
            )
        }
        //Chat Details
        composable(route = AllScreens.ClientChatDetails.name) {
            ChatDetails(navController = navController)
        }

        //worker


        composable(
            route = AllScreens.WorkerHomeScreen.name + "/{route}",
            arguments = listOf(navArgument(name = "route") {
                type = NavType.StringType
            })
        ) { data ->
            data.arguments!!.getString("route")?.let {
                WorkerHomeScreen(
                    navController = navController, route = it
                )
            }
        }

        composable(route = AllScreens.WorkerProblemDetilas.name) {
            WorkerProblemDetails(navController = navController)
        }
//        composable(route = AllScreens.WorkerProfileScreen.name) {
//            WorkerProfileScreen(navController = navController)
//        }
        val workerProfileScreen = AllScreens.WorkerProfileScreen.name
        composable(
            route = "$workerProfileScreen/{showReport}/{admin}/{name}",
            arguments = listOf(navArgument(name = "showReport") {
                type = NavType.BoolType
            }, navArgument(name = "admin") {
                type = NavType.BoolType
            }, navArgument(name = "name") {
                type = NavType.StringType
            }
            )
        ) { data ->
            WorkerProfileScreen(
                navController = navController,
                ShowReportButton = data.arguments!!.getBoolean("showReport"),
                isAdmin = data.arguments!!.getBoolean("admin"),
                workerName = data.arguments!!.getString("name").toString()
            )
        }
        composable(route = AllScreens.WorkerProfileSettingsScreen.name) {
            WorkerProfileSettingsScreen(navController = navController)
        }
        composable(route = AllScreens.MyProjectProblemDetails.name) {
            MyProjectProblemDetails(navController = navController)
        }


        //Admin Screen


        composable(route = AllScreens.AdminHomeScreen.name) {
            AdminHomeScreen(navController = navController)
        }
        composable(route = AllScreens.AdminJobsScreen.name) {
            AdminJobsScreen(navController = navController)
        }
        composable(
            route = AllScreens.AdminEditJobsScreen.name + "/{id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.IntType
            })
        ) { data ->
            AdminEditJobsScreen(navController = navController, id = data.arguments?.getInt("id")!!)
        }
        composable(
            route = AllScreens.AdminAllWorkersInSpecificJob.name + "/{id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.IntType
            })
        ) { data ->
            AdminAllWorkersInSpecificJob(
                navController = navController,
                id = data.arguments?.getInt("id")!!
            )
        }
        composable(route = AllScreens.AdminAllWorkers.name) {
            AdminAllWorkers(navController)
        }
        composable(route = AllScreens.AdminAllClients.name) {
            AdminAllClients(navController)
        }

        composable(route = AllScreens.AdminBlockedUsers.name) {
            AdminBlockedUsers(navController)
        }
        composable(
            route = AllScreens.AdminReportsQuery.name + "/{client}/{id}",
            arguments = listOf(navArgument(name = "client") {
                type = NavType.BoolType
            }, navArgument(name = "id") {
                type = NavType.IntType
            })
        ) { data ->
            AdminReportsQuery(
                navController, id = data.arguments?.getInt("id")!!,
                client = data.arguments?.getBoolean("client")!!
            )
        }
        composable(
            route = AllScreens.AdminReportListQuery.name + "/{reportUserName}",
            arguments = listOf(navArgument(name = "reportUserName") {
                type = NavType.StringType
            })
        ) { data ->
            AdminReportListQuery(
                navController,
                name = data.arguments?.getString("reportUserName").toString()
            )
        }
    }
}