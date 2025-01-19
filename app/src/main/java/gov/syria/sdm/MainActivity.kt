package gov.syria.sdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gov.syria.sdm.ui.theme.SDMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SDMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SDMTheme {
        Greeting("Android")
    }
}

//suspend fun fetchUsers() {
//    val url = "https://jsonplaceholder.typicode.com/users"
//    val response = apiRequest(
//        method = HttpMethod.Get,
//        url = url,
//        params = mapOf("limit" to "10", "page" to "1")
//    )
//    println(response.bodyAsText()) // Print the response body as a string
//}
//
//@kotlinx.serialization.Serializable
//data class LoginRequest(val email: String, val password: String)
//
//suspend fun loginUser() {
//    val url = "https://example.com/api/login"
//    val body = LoginRequest(email = "user@example.com", password = "password123")
//    val response = apiRequest(
//        method = HttpMethod.Post,
//        url = url,
//        body = body,
//        headers = mapOf("Authorization" to "Bearer YOUR_TOKEN")
//    )
//    println(response.bodyAsText()) // Print the response body as a string
//}
//@kotlinx.serialization.Serializable
//data class User(val id: Int, val name: String, val email: String)
//
//suspend fun fetchUserDetails() {
//    val url = "https://jsonplaceholder.typicode.com/users/1"
//    val response = apiRequest(
//        method = HttpMethod.Get,
//        url = url
//    )
//    val user: User = response.body()
//    println("User: $user")
//}
