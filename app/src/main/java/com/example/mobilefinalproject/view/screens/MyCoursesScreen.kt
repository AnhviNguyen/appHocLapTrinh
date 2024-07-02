package com.example.mobilefinalproject.view.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilefinalproject.model.Course
import com.example.mobilefinalproject.navigation.NavigationItem
import com.example.mobilefinalproject.view.components.CircularProgressIndicatorEx
import com.example.mobilefinalproject.view.components.SmallCourseCard
import com.example.mobilefinalproject.view.components.TopBar
import com.example.mobilefinalproject.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyCoursesScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
    myCourses:List<Course>
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(navController = navController, title = "My Courses")
        },
    ) {
        LazyColumn(

            contentPadding = PaddingValues(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
            if (myCourses.isEmpty()){
                item {
                    Spacer(modifier = Modifier.height(300.dp))
                }
                item {
                    CircularProgressIndicatorEx()
                }
            } else{
                items(myCourses) { course ->
                    SmallCourseCard(
                        course = course,
                        onItemClick = { navController.navigate("${NavigationItem.CourseDetails.route}/${course.id}") }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }


}