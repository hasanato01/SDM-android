package gov.syria.sdm.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Configure the Ktor client
val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys =
                true // Allow safe deserialization even if the response contains extra fields
        })
    }
}

// Function for making the API request
suspend fun apiRequest(
    method: HttpMethod,
    url: String, // Base URL + endpoint
    params: Map<String, String>? = null,
    body: Any? = null,
    headers: Map<String, String>? = null
): HttpResponse {
    return httpClient.request(url) {
        this.method = method

        // Add query params if existed
        params?.forEach { (key, value) ->
            url {
                parameters.append(key, value)
            }
        }

        // Add request body if existed (for PUT/POST)
        body?.let {
            contentType(ContentType.Application.Json)
            setBody(it)
        }

        // Add headers if existed
        headers?.forEach { (key, value) ->
            header(key, value)
        }
    }
}