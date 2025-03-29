package gov.syria.sdm.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gov.syria.sdm.R
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.application.Screen
import gov.syria.sdm.ui.components.TextField
import kotlinx.coroutines.launch

@Composable
fun AuthForm(navController: NavController, viewModel: ApplicationViewModel) {
  val email = remember { mutableStateOf("") }
  val password = remember { mutableStateOf("") }
  val firstName = remember { mutableStateOf("") }
  val lastName = remember { mutableStateOf("") }
  val isSignUpMode = remember { mutableStateOf(false) }
  val isError = remember { mutableStateOf(false) }
  val errorMessage = remember { mutableIntStateOf(0) }
  val snackbarHostState = remember { SnackbarHostState() }
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    // Title
    item {
      Text(
        text = if (isSignUpMode.value) stringResource(R.string.signUp) else stringResource(R.string.signIn),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
      )
    }

    // Email field
    item {
      TextField(
        inputText = email,
        placeHolder = stringResource(R.string.email),
        leadingIcon = R.drawable.ic_email,
        keyboardType = KeyboardType.Email
      )
    }
    // First name field
    if (isSignUpMode.value) {
      item {
        TextField(
          inputText = firstName,
          placeHolder = stringResource(R.string.firstName),
          leadingIcon = R.drawable.ic_person
        )
      }
      // Last name field
      item {
        TextField(
          inputText = lastName,
          placeHolder = stringResource(R.string.lastName),
          leadingIcon = R.drawable.ic_person
        )
      }
    }

    // Password field
    if (!isSignUpMode.value) {
      item {
        TextField(
          inputText = password,
          placeHolder = stringResource(R.string.password),
          leadingIcon = R.drawable.ic_lock,
          keyboardType = KeyboardType.Password,
          visualTransformation = PasswordVisualTransformation()
        )
      }
    }

    if (isError.value) {
      item {
        Text(
          text = stringResource(errorMessage.intValue),
          color = Color(red = 234, green = 153, blue = 153)
        )
      }
    }

    // Submit Button
    item {
      Button(
        onClick = {
          coroutineScope.launch {
            val trimmedEmail = email.value.trim()
            val trimmedPassword = password.value.trim()
            val trimmedFirstName = firstName.value.trim()
            val trimmedLastName = lastName.value.trim()

            if (isSignUpMode.value) {
              // Sign Up logic
              if (trimmedEmail.isNotEmpty() && trimmedFirstName.isNotEmpty() && trimmedLastName.isNotEmpty()) {
                val success = viewModel.signUp(
                  firstName = trimmedFirstName,
                  lastName = trimmedLastName,
                  email = trimmedEmail
                )
                if (success) {
                  isSignUpMode.value = false
                  coroutineScope.launch {
                    snackbarHostState.showSnackbar("تم إنشاء الحساب. يرجى تسجيل الدخول")
                  }
                } else {
                  errorMessage.intValue = R.string.errorWhenCreatingAccount
                  isError.value = true
                  println("❌ Error: Sign-Up Failed")
                }
              } else {
                errorMessage.intValue = R.string.emptyFields
                isError.value = true
                println("⚠️ Error: Fields Required")
              }
            } else {
              // Sign In logic
              if (trimmedEmail.isNotEmpty() && trimmedPassword.isNotEmpty()) {
                val success = viewModel.signIn(context, trimmedEmail, trimmedPassword)
                if (success) {
                  navController.navigate(Screen.PersonalInfo.route)
                } else {
                  errorMessage.intValue = R.string.invalidCredentials
                  isError.value = true
                  println("❌ Error: Sign-In Failed")
                }
              } else {
                errorMessage.intValue = R.string.emptyFields
                isError.value = true
                println("⚠️ Error: Fields Required")
              }
            }
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      ) {
        Text(text = if (isSignUpMode.value) stringResource(R.string.signUp) else stringResource(R.string.signIn))
      }
      SnackbarHost(hostState = snackbarHostState)
    }

    // Toggle Mode Button
    item {
      TextButton(
        onClick = {
          isSignUpMode.value = !isSignUpMode.value
          isError.value = false
          errorMessage.intValue = 0
        },
        modifier = Modifier.padding(top = 8.dp)
      ) {
        Text(
          text = if (isSignUpMode.value)
            stringResource(R.string.alreadyHaveAccount)
          else
            stringResource(R.string.needAccount),
          color = MaterialTheme.colorScheme.primary
        )
      }
    }
  }
}
