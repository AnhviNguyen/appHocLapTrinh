package com.example.mobilefinalproject.view.screens.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobilefinalproject.R
import com.example.mobilefinalproject.config.CONSTANT
import com.example.mobilefinalproject.config.ValidRegex
import com.example.mobilefinalproject.model.RegisterRequest
import com.example.mobilefinalproject.navigation.NavigationItem
import com.example.mobilefinalproject.view.components.InfoDialog
import com.example.mobilefinalproject.view.components.MyButton
import com.example.mobilefinalproject.view.components.OutlineMyButton
import com.example.mobilefinalproject.view.screens.HomeScreen
import com.example.mobilefinalproject.viewmodel.AuthViewModel
import com.example.mobilefinalproject.viewmodel.HomeViewModel

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    var isEmty by remember {
        mutableStateOf(false)
    }
    var isErrorEmailUnique by remember {
        mutableStateOf(false)
    }
    val isErrorRegister by authViewModel.isErrorRegister.collectAsState(false)
    val isLoading by authViewModel.isLoading.collectAsState()


    val keyboardController = LocalSoftwareKeyboardController.current

    var name by rememberSaveable {
        mutableStateOf("")
    }
    var isNameError by rememberSaveable {
        mutableStateOf(false)
    }
    var nameTextError by rememberSaveable {
        mutableStateOf("")
    }

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var isEmailError by rememberSaveable {
        mutableStateOf(false)
    }
    var emailTextError by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var isPasswordError by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordTextError by rememberSaveable {
        mutableStateOf("")
    }
    var passwordVisible by rememberSaveable {
        mutableStateOf(false) }

    fun validateEmail(text: String) {
        if (text == ""){
            isEmailError=true;
            emailTextError="Email must be required"
        }else{
            if (!ValidRegex.isEmail(text)){
                isEmailError=true;
                emailTextError="Incorrect email format."
            } else{
                isEmailError=false;
            }
        }

    }
    fun validateName(text: String) {
        if (text == ""){
            isNameError=true;
            nameTextError="Name must be required"
        }else{
            isNameError=false;
        }

    }
    fun validatePassword(text: String) {
        if (text == ""){
            isPasswordError=true;
            passwordTextError="Password must be required"
        }else{
            if (text.length<6){
                isPasswordError=true;
                passwordTextError="Password must have at least 6 characters"
            } else{
                isPasswordError=false;
            }
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Sign in", fontSize = 27.sp, fontWeight = FontWeight.ExtraBold, color = colorResource(id = R.color.blue))
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Create your account", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(20.dp))

        Row (modifier = Modifier.fillMaxWidth()){
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
            )
            {
                Image(
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 10.dp),
                )
                Text(text = "Facebook", color = Color.Black, modifier = Modifier.padding(vertical = 10.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
            )
            {
                Image(
                    painter = painterResource(R.drawable.google_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 10.dp),
                )
                Text(text = "Google", color = Color.Black, modifier = Modifier.padding(vertical = 10.dp))
            }
        }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it;
                    validateName(name)
                },

                isError = isNameError,
                singleLine = true,
                supportingText = {
                    if (isNameError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = nameTextError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ })
                    {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription =null )
                    }
                },
                trailingIcon = @androidx.compose.runtime.Composable {
                    IconButton(onClick = { name = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                },

                label = { Text(text = "Username") },
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { keyboardController?.hide() }),
                modifier = Modifier
                    .fillMaxWidth()


            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it;
                    validateEmail(email)
                },

                isError = isEmailError,
                singleLine = true,
                supportingText = {
                    if (isEmailError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = emailTextError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { name = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                },
                label = { Text(text = "Email") },
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { keyboardController?.hide() }),
                modifier = Modifier
                    .fillMaxWidth()


            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it;
                    validatePassword(password)
                },

                isError = isPasswordError,
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.hide_password),
                            contentDescription = null
                        )
                    }
                },
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription =null )
                    }
                },
                supportingText = {
                    if (isPasswordError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = passwordTextError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                label = { Text(text = "Password") },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()


            )



        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Create an account",
                onClick =
                {
                    if (email == "" || password == "" || name == "") {
                        isEmty = true
                    } else {
                        if (isEmailError || isPasswordError || isNameError) {

                        } else {
                            authViewModel.register(RegisterRequest(name, email, password), navController)

                            Log.e("isErrorRegister", isErrorRegister.toString())
                            if (isErrorRegister) {
                                isErrorEmailUnique = true
                            }
                            Log.e("isErrorEmailUnique", isErrorEmailUnique.toString())
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlineMyButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Login here",
                onClick = {  navController.navigate(NavigationItem.Login.route) }
            )
        }


    }


        if (isEmty) {
            InfoDialog(
                title = "Register validated",
                desc = "Information cannot be left blank.",
                urlIcon = CONSTANT.APP.FAIL_ICON_URL,
                onDismiss = {
                    isEmty = false
                }
            )
        }

        if (isErrorEmailUnique) {
            InfoDialog(
                title = "Email error",
                desc = "Email already exists.",
                urlIcon = CONSTANT.APP.FAIL_ICON_URL,
                onDismiss = {
                    isErrorEmailUnique = false
                }
            )
        }

        if (isLoading){
            val strokeWidth = 5.dp

            CircularProgressIndicator(
                modifier = Modifier
                    .drawBehind {
                        drawCircle(
                            Color.Red,
                            radius = size.width / 2 - strokeWidth.toPx() / 2,
                            style = Stroke(strokeWidth.toPx())
                        )
                    },
                color = Color.LightGray,
                strokeWidth = strokeWidth
            )
        }
    }



