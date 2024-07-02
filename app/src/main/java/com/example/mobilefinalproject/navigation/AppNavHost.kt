package com.example.mobilefinalproject.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobilefinalproject.config.AuthSharedPreferencesUtil
import com.example.mobilefinalproject.view.screens.CartScreen
import com.example.mobilefinalproject.view.screens.ChatScreen
import com.example.mobilefinalproject.view.screens.CheckoutScreen
import com.example.mobilefinalproject.view.screens.CourseDetailScreen
import com.example.mobilefinalproject.view.screens.CourseOverviewScreen
import com.example.mobilefinalproject.view.screens.CourseResult
import com.example.mobilefinalproject.view.screens.CoursesByCategory
import com.example.mobilefinalproject.view.screens.CoursesResultScreen
import com.example.mobilefinalproject.view.screens.EditProfileScreen
import com.example.mobilefinalproject.view.screens.HomeScreen
import com.example.mobilefinalproject.view.screens.LessonDetailScreen
import com.example.mobilefinalproject.view.screens.MainScreen

import com.example.mobilefinalproject.view.screens.MultipleChoiceTestScreen
import com.example.mobilefinalproject.view.screens.MyCoursesScreen
import com.example.mobilefinalproject.view.screens.auth.LoginScreen
import com.example.mobilefinalproject.view.screens.ProfileScreen
import com.example.mobilefinalproject.view.screens.RecentLessonsScreen
import com.example.mobilefinalproject.view.screens.auth.RegisterScreen
import com.example.mobilefinalproject.viewmodel.AuthViewModel
import com.example.mobilefinalproject.viewmodel.ChatViewModel
import com.example.mobilefinalproject.viewmodel.CourseViewModel
import com.example.mobilefinalproject.viewmodel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun AppNavHost(
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController,
    courseViewModel: CourseViewModel,
    pickVisualMediaLauncher: ActivityResultLauncher<String>,
    modifier: Modifier,
    authManager: AuthSharedPreferencesUtil
) {
    val userCurrent by authViewModel.userCurrent.collectAsState(null)
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationItem.Main.route
    ) {
        composable(NavigationItem.Main.route) {
            MainScreen(authManager, navController)
        }
        composable("${NavigationItem.Home.route}/{userID}",
            arguments = listOf(
                navArgument("userID") {
                    type = NavType.IntType
                },
            )
        ) {
            val userID = it.arguments?.getInt("userID")
            Log.e("userID",userID.toString())
            if (userID != null) {
               HomeScreen(
                   homeViewModel = homeViewModel,
                   authViewModel = authViewModel,
                   navController = navController,
                   courseViewModel = courseViewModel,
                   userID = userID
               )
            }
        }
        composable("${NavigationItem.Profile.route}/{userID}",
            arguments = listOf(
                navArgument("userID") {
                    type = NavType.IntType
                },
            )
        ) {
            val userID = it.arguments?.getInt("userID")
            Log.e("userID",userID.toString())
            if (userID != null) {
                ProfileScreen(userID = userID, navController = navController, authViewModel = authViewModel )
            }
        }
        composable("${NavigationItem.EditProfile.route}/{userID}",
            arguments = listOf(
                navArgument("userID") {
                    type = NavType.IntType
                },
            )
        ) {
            val userID = it.arguments?.getInt("userID")
            Log.e("userID",userID.toString())
            if (userID != null) {
               EditProfileScreen(userID = userID, navController = navController, authViewModel = authViewModel)
            }
        }
        composable(NavigationItem.AuthorProfile.route) {

        }
        composable(
            "${NavigationItem.CourseOverview.route}/{courseID}",
            arguments = listOf(
                navArgument("courseID") {
                    type = NavType.IntType
                },
            )
        ) {
            val courseID = it.arguments?.getInt("courseID")
            Log.e("courses-id",courseID.toString())
            if (courseID != null) {
                userCurrent?.let { it1 ->
                    CourseOverviewScreen(
                        courseViewModel = courseViewModel,
                        courseId = courseID,
                        userID = it1.id ,
                        navController = navController
                    )
                }
            }
        }
        composable(
            "${NavigationItem.CourseDetails.route}/{courseID}",
            arguments = listOf(
                navArgument("courseID") {
                    type = NavType.IntType
                },
            )
        ) {
            val courseID = it.arguments?.getInt("courseID")
            Log.e("courses-id",courseID.toString())
            if (courseID != null) {
                CourseDetailScreen(courseViewModel, courseID,navController)
            }
        }

        composable(
            "${NavigationItem.Lesson.route}/{lessonID}",
            arguments = listOf(
                navArgument("lessonID") {
                    type = NavType.IntType
                },
            )
        ) {
            val lessonID = it.arguments?.getInt("lessonID")
            Log.e("lesson-id",lessonID.toString())
            if (lessonID != null) {
                userCurrent?.let {
                    it1 ->
                    LessonDetailScreen(
                        lessonID = lessonID,
                        userID = it1.id,
                        courseViewModel = courseViewModel ,
                        navController = navController)

                }

            }
        }

        composable(NavigationItem.CoursePlaylist.route) {

        }
        composable(
            "${NavigationItem.CourseResult.route}/{SearchKey}",
            arguments = listOf(
                navArgument("SearchKey") {
                    type = NavType.StringType
                },
            )
        ) {
            val searchKey = it.arguments?.getString("SearchKey")
            if (searchKey != null) {
                CoursesResultScreen(
                    homeViewModel = homeViewModel,
                    navController = navController,
                    searchKey = searchKey
                )
            }
        }

        composable(NavigationItem.Login.route) {
            LoginScreen(authViewModel, homeViewModel, navController,courseViewModel)
        }
        composable(NavigationItem.Register.route) {
            RegisterScreen(authViewModel, navController)

        }
//        composable(
//            "${NavigationItem.Review.route}/{testID}",
//            arguments = listOf(
//                navArgument("testID") {
//                    type = NavType.IntType
//                },
//            )
//        ) {
//            val testID = it.arguments?.getInt("testID")
//            if (testID != null) {
//                ReviewScreen(
//                    navController = navController,
//                    courseViewModel = courseViewModel,
//                    lessonID =
//                )
//            }
//        }
        composable("${NavigationItem.MyCourses.route}/{userID}",
            arguments = listOf(
                navArgument("userID") {
                    type = NavType.IntType
                },
            )
        ) {
            val userID = it.arguments?.getInt("userID")
            Log.e("userID",userID.toString())
            if (userID != null) {
                val myCourses by authViewModel.myCourse.collectAsState()
                LaunchedEffect(key1 = Unit) {
                    authViewModel.getMyCourse(userID)
                }
                MyCoursesScreen(homeViewModel = homeViewModel, navController = navController, myCourses = myCourses )
            }
        }
        composable("${NavigationItem.Cart.route}/{userID}",
            arguments = listOf(
                navArgument("userID") {
                    type = NavType.IntType
                },
            )
        ) {
            val userID = it.arguments?.getInt("userID")
            Log.e("userID",userID.toString())
            if (userID != null) {
                CartScreen(userID = userID, navController = navController, courseViewModel = courseViewModel)
            }
        }

        composable(NavigationItem.Chat.route
        ) {
            ChatScreen(navController = navController, pickVisualMediaLauncher = pickVisualMediaLauncher)
        }
        composable("${NavigationItem.RecentLessons.route}/{userID}",
            arguments = listOf(
                navArgument("userID") {
                    type = NavType.IntType
                },
            )
        ) {
            val userID = it.arguments?.getInt("userID")
            if (userID != null) {
                RecentLessonsScreen(navController = navController,userID)
            }
        }
        composable(
            "${NavigationItem.CourseByCategory.route}/{CategoryId}/{CategoryName}",
            arguments = listOf(
                navArgument("CategoryId") {
                    type = NavType.StringType
                },
                navArgument("CategoryName") {
                    type = NavType.StringType
                }
            )
        ) {
            val categoryId = it.arguments?.getString("CategoryId")?.toInt()
            val categoryName = it.arguments?.getString("CategoryName")
            if (categoryId != null && categoryName != null) {
                CoursesByCategory(
                    homeViewModel = homeViewModel,
                    nameCategory = categoryName,
                    navController
                )
            }
        }

        composable(
            "${NavigationItem.Test.route}/{lessonID}",
            arguments = listOf(
                navArgument("lessonID") {
                    type = NavType.IntType
                },
            )
        ) {
            val lessonID = it.arguments?.getInt("lessonID")
            Log.e("lesson-id",lessonID.toString())
            if (lessonID != null) {
                MultipleChoiceTestScreen(
                    navController = navController,
                    courseViewModel = courseViewModel,
                    lessonID = lessonID
                )
            }
        }

        composable(
            "${NavigationItem.Checkout.route}/{userID}/{courseID}",
            arguments = listOf(
                navArgument("userID") {
                    type = NavType.IntType
                },
                navArgument("courseID") {
                    type = NavType.IntType
                }
            )
        ) {
            val userID = it.arguments?.getInt("userID")
            val courseID = it.arguments?.getInt("courseID")

            if (userID != null && courseID!=null) {
                CheckoutScreen(
                    userID = userID,
                    courseID = courseID,
                    navController = navController,
                    courseViewModel = courseViewModel
                )
            }
        }
    }


}