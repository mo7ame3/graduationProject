package com.example.graduationproject.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.graduationproject.screens.admin.home.AdminHomeScreen
import com.example.graduationproject.screens.admin.query.blocks.AdminBlockedUsers
import com.example.graduationproject.screens.admin.query.clients.AdminAllClients
import com.example.graduationproject.screens.admin.query.crafts.AdminCraftsScreen
import com.example.graduationproject.screens.admin.query.crafts.CraftsViewModel
import com.example.graduationproject.screens.admin.query.crafts.allWorkers.AdminAllWorkersInSpecificJob
import com.example.graduationproject.screens.admin.query.crafts.createCraft.AdminCreateNewCraft
import com.example.graduationproject.screens.admin.query.crafts.createCraft.CreateNewCraftViewModel
import com.example.graduationproject.screens.admin.query.crafts.edit.AdminEditCraftScreen
import com.example.graduationproject.screens.admin.query.crafts.edit.AdminEditCraftViewModel
import com.example.graduationproject.screens.admin.query.reports.AdminReportsQuery
import com.example.graduationproject.screens.admin.query.reports.query.AdminReportListQuery
import com.example.graduationproject.screens.admin.query.workers.AdminAllWorkers
import com.example.graduationproject.screens.client.home.ClientHomeScreen
import com.example.graduationproject.screens.client.home.ClientHomeViewModel
import com.example.graduationproject.screens.client.order.ClientMyCraftOrders
import com.example.graduationproject.screens.client.order.ClientOrderOfferScreen
import com.example.graduationproject.screens.client.order.OrderViewModel
import com.example.graduationproject.screens.client.postScreen.ClientPostScreen
import com.example.graduationproject.screens.client.postScreen.PostViewModel
import com.example.graduationproject.screens.client.profile.ClientMyProfileScreen
import com.example.graduationproject.screens.client.profile.AdminClientProfileScreen
import com.example.graduationproject.screens.client.profile.ClientProfileScreen
import com.example.graduationproject.screens.client.profile.ClientProfileViewModel
import com.example.graduationproject.screens.client.rate.ClientRateScreen
import com.example.graduationproject.screens.sharedScreens.chat.ChatDetails
import com.example.graduationproject.screens.sharedScreens.login.AuthenticationViewModel
import com.example.graduationproject.screens.sharedScreens.login.ChangePasswordScreen
import com.example.graduationproject.screens.sharedScreens.login.ForgotPasswordScreen
import com.example.graduationproject.screens.sharedScreens.login.LoginScreen
import com.example.graduationproject.screens.sharedScreens.profileSetting.ProfileSettingScreen
import com.example.graduationproject.screens.sharedScreens.profileSetting.ProfileSettingViewModel
import com.example.graduationproject.screens.sharedScreens.report.ReportScreen
import com.example.graduationproject.screens.sharedScreens.splash.SplashScreen
import com.example.graduationproject.screens.worker.home.WorkerHomeScreen
import com.example.graduationproject.screens.worker.home.WorkerHomeViewModel
import com.example.graduationproject.screens.worker.myOffers.MyOfferProblemDetails
import com.example.graduationproject.screens.worker.myOffers.MyOffersViewModel
import com.example.graduationproject.screens.worker.problemDetails.WorkerProblemDetails
import com.example.graduationproject.screens.worker.problemDetails.WorkerProblemDetailsViewModel
import com.example.graduationproject.screens.worker.profile.WorkerMyProfileScreen
import com.example.graduationproject.screens.worker.profile.AdminWorkerProfileScreen
import com.example.graduationproject.screens.worker.profile.WorkerProfileScreen
import com.example.graduationproject.screens.worker.profile.WorkerProfileViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AllScreens.SplashScreen.name
    ) {
        val postScreen = AllScreens.ClientPostScreen.name
        composable(
            route = "$postScreen/{craftId}/{craftName}",
            arguments = listOf(
                navArgument(name = "craftId") {
                    type = NavType.StringType
                },
                navArgument(name = "craftName") {
                    type = NavType.StringType
                },
            )
        ) { data ->
            val viewModel = hiltViewModel<PostViewModel>()
            ClientPostScreen(
                navController = navController,
                craftId = data.arguments!!.getString("craftId").toString(),
                craftName = data.arguments!!.getString("craftName").toString(),
                viewModel
            )

        }
        composable(route = AllScreens.LoginScreen.name) {
            val viewModel = hiltViewModel<AuthenticationViewModel>()
            LoginScreen(
                navController = navController,
                authenticationViewModel = viewModel
            )
        }
        composable(route = AllScreens.ClientOrderOfferScreen.name + "/{problemTitle}/{orderDescription}/{orderId}/{craftId}",
            arguments = listOf(
                navArgument(name = "problemTitle") {
                    type = NavType.StringType
                },
                navArgument(name = "orderDescription") {
                    type = NavType.StringType
                },
                navArgument(name = "orderId") {
                    type = NavType.StringType
                },
                navArgument(name = "craftId") {
                    type = NavType.StringType
                }
            )) { data ->
            val orderViewModel = hiltViewModel<OrderViewModel>()
            ClientOrderOfferScreen(
                navController = navController,
                orderViewModel = orderViewModel,
                orderTitle = data.arguments?.getString("problemTitle")!!,
                orderDescription = data.arguments?.getString("orderDescription")!!,
                orderId = data.arguments?.getString("orderId")!!,
                craftId = data.arguments?.getString("craftId")!!,
            )
        }
        composable(
            route = AllScreens.ClientHomeScreen.name + "/{route}",
            arguments = listOf(navArgument(name = "route") {
                type = NavType.StringType
            }
            )
        ) { data ->
            val viewModel = hiltViewModel<ClientHomeViewModel>()
            val orderViewModel = hiltViewModel<OrderViewModel>()
            data.arguments!!.getString("route")?.let {
                ClientHomeScreen(
                    navController = navController,
                    route = it,
                    clientHomeViewModel = viewModel,
                    orderViewModel = orderViewModel
                )
            }
        }
        composable(route = AllScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(route = AllScreens.ForgotPasswordScreen.name) {
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = AllScreens.ChangePasswordScreen.name) {
            ChangePasswordScreen(navController = navController)
        }


        composable(
            route = AllScreens.ProfileScreen.name + "/{client}",
            arguments = listOf(navArgument(name = "client") {
                type = NavType.BoolType
            })
        )
        {
            val profileSettingViewModel = hiltViewModel<ProfileSettingViewModel>()
            ProfileSettingScreen(
                navController = navController,
                client = it.arguments!!.getBoolean("client"),
                profileSettingViewModel = profileSettingViewModel
            )
        }

        val myCraftOrders = AllScreens.ClientMyCraftOrders.name
        composable(
            route = "$myCraftOrders/{craftId}/{craftName}",
            arguments = listOf(navArgument(name = "craftId") {
                type = NavType.StringType
            }, navArgument(name = "craftName") {
                type = NavType.StringType
            })
        ) { data ->
            val orderViewModel = hiltViewModel<OrderViewModel>()
            ClientMyCraftOrders(
                navController = navController,
                craftId = data.arguments!!.getString("craftId").toString(),
                name = data.arguments!!.getString("craftName").toString(),
                orderViewModel = orderViewModel
            )

        }

        val profileScreen = AllScreens.AdminClientProfileScreen.name
        composable(
            route = "$profileScreen/{showReport}/{completeProject}/{admin}/{name}",
            arguments = listOf(navArgument(name = "showReport") {
                type = NavType.BoolType
            },
                navArgument(name = "completeProject") {
                    type = NavType.BoolType
                },
                navArgument(name = "admin") {
                    type = NavType.BoolType
                },
                navArgument(name = "name") {
                    type = NavType.StringType
                })
        ) { data ->
            AdminClientProfileScreen(
                navController = navController,
                ShowReportButton = data.arguments!!.getBoolean("showReport"),
                completeProject = data.arguments!!.getBoolean("completeProject"),
                isAdmin = data.arguments!!.getBoolean("admin"),
                clientName = data.arguments!!.getString("name").toString()
            )
        }


        composable(
            route = AllScreens.ClientProfileScreen.name + "/{clientId}",
            arguments = listOf(
                navArgument(name = "clientId") {
                    type = NavType.StringType
                },
            )
        )
        {
            val clientProfileViewModel = hiltViewModel<ClientProfileViewModel>()
            ClientProfileScreen(
                navController = navController,
                clientId = it.arguments?.getString("clientId").toString(),
                clientProfileViewModel = clientProfileViewModel
            )
        }

        composable(
            route = "${AllScreens.ClientMyProfileScreen.name}/{completeProject}",
            arguments = listOf(navArgument(name = "completeProject") {
                type = NavType.BoolType
            })
        ) { data ->
            val clientProfileViewModel = hiltViewModel<ClientProfileViewModel>()
            ClientMyProfileScreen(
                navController = navController,
                completeProject = data.arguments!!.getBoolean("completeProject"),
                clientProfileViewModel = clientProfileViewModel
            )
        }
        //Rate Screen
        composable(route = AllScreens.ClientRateScreen.name) {
            ClientRateScreen(navController = navController)
        }
        //Report Screen
        composable(
            route = AllScreens.ReportScreen.name + "/{client}/{status}",
            arguments = listOf(navArgument(name = "client") {
                type = NavType.BoolType
            }, navArgument(name = "status") {
                type = NavType.StringType
            })
        ) { data ->
            ReportScreen(
                navController = navController,
                client = data.arguments!!.getBoolean("client"),
                status = data.arguments!!.getString("status").toString()
            )
        }
        //Chat Details

        composable(
            route = "${AllScreens.ChatDetails.name}/{id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.StringType
            })
        ) { data ->
            data.arguments?.getString("id").let { id ->
                ChatDetails(navController = navController, id = id.toString())
            }
        }

        //worker


        composable(
            route = AllScreens.WorkerHomeScreen.name + "/{route}",
            arguments = listOf(navArgument(name = "route") {
                type = NavType.StringType
            })
        ) { data ->
            val viewModel = hiltViewModel<WorkerHomeViewModel>()
            val myOfferViewModel = hiltViewModel<MyOffersViewModel>()
            data.arguments!!.getString("route")?.let {
                WorkerHomeScreen(
                    navController = navController,
                    route = it,
                    workerHomeViewModel = viewModel,
                    myOffersViewModel = myOfferViewModel
                )
            }
        }

        composable(route = AllScreens.WorkerProblemDetails.name + "/{orderId}", arguments = listOf(
            navArgument(name = "orderId") {
                type = NavType.StringType
            }
        )) {
            val viewModel = hiltViewModel<WorkerProblemDetailsViewModel>()

            WorkerProblemDetails(
                navController = navController, orderID = it.arguments?.getString("orderId")
                    .toString(), workerProblemDetailsViewModel = viewModel
            )
        }
//        composable(route = AllScreens.WorkerProfileScreen.name) {
//            WorkerProfileScreen(navController = navController)
//        }
        val adminWorkerProfileScreen = AllScreens.AdminWorkerProfileScreen.name
        composable(
            route = "$adminWorkerProfileScreen/{showReport}/{admin}/{name}",
            arguments = listOf(navArgument(name = "showReport") {
                type = NavType.BoolType
            }, navArgument(name = "admin") {
                type = NavType.BoolType
            }, navArgument(name = "name") {
                type = NavType.StringType
            }
            )
        ) { data ->
            AdminWorkerProfileScreen(
                navController = navController,
                ShowReportButton = data.arguments!!.getBoolean("showReport"),
                isAdmin = data.arguments!!.getBoolean("admin"),
                workerName = data.arguments!!.getString("name").toString()
            )
        }

        composable(route = AllScreens.WorkerProfileScreen.name + "/{workerId}", arguments = listOf(
            navArgument(name = "workerId") {
                type = NavType.StringType
            }
        )) {
            val workerProfileViewModel = hiltViewModel<WorkerProfileViewModel>()
            WorkerProfileScreen(
                navController = navController,
                workerId = it.arguments?.getString("workerId").toString(),
                workerProfileViewModel = workerProfileViewModel
            )
        }

        composable(route = AllScreens.WorkerMyProfileScreen.name) {
            val workerProfileViewModel = hiltViewModel<WorkerProfileViewModel>()
            WorkerMyProfileScreen(
                navController = navController,
                workerProfileViewModel = workerProfileViewModel
            )
        }

        composable(route = AllScreens.MyProjectProblemDetails.name + "/{orderId}",
            arguments = listOf(
                navArgument(name = "orderId") {
                    type = NavType.StringType
                }
            )) {
            val myOffersViewModel = hiltViewModel<MyOffersViewModel>()
            MyOfferProblemDetails(
                navController = navController,
                myOffersViewModel = myOffersViewModel,
                orderId = it.arguments!!.getString("orderId").toString()
            )
        }


        //Admin Screen


        composable(route = AllScreens.AdminHomeScreen.name) {
            AdminHomeScreen(navController = navController)
        }
        composable(route = AllScreens.AdminJobsScreen.name) {
            val viewModel = hiltViewModel<CraftsViewModel>()
            AdminCraftsScreen(navController = navController, viewModel)
        }
        composable(
            route = AllScreens.AdminEditJobsScreen.name + "/{craftId}",
            arguments = listOf(navArgument(name = "craftId") {
                type = NavType.StringType
            })
        ) { data ->
            val viewModel = hiltViewModel<AdminEditCraftViewModel>()
            AdminEditCraftScreen(
                navController = navController,
                craftId = data.arguments?.getString("craftId")!!,
                viewModel
            )
        }
        composable(
            route = AllScreens.AdminAllWorkersInSpecificJob.name + "/{id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.StringType
            })
        ) { data ->
            AdminAllWorkersInSpecificJob(
                navController = navController,
                id = data.arguments?.getString("id")!!
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
        composable(
            route = AllScreens.AdminCreateNewCraft.name
        ) {
            val viewModel = hiltViewModel<CreateNewCraftViewModel>()
            AdminCreateNewCraft(navController, viewModel)
        }
    }
}