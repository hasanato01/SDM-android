package gov.syria.sdm.api

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object ApiHelper {
  private val gson = Gson()

  suspend fun makeRequest(
    url: String,
    endpoint: String,
    method: String = "GET",
    requestBody: Any? = null
  ): JSONObject? {
    return withContext(Dispatchers.IO) {
      try {
        val connection = URL("$url$endpoint").openConnection() as HttpURLConnection
        connection.requestMethod = method
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "application/json")

        // Handle request body for POST & PUT
        if (method == "POST" || method == "PUT") {
          connection.doOutput = true
          val jsonInputString = gson.toJson(requestBody) // Convert any object to JSON
          OutputStreamWriter(connection.outputStream).use { writer ->
            writer.write(jsonInputString)
            writer.flush()
          }
        }

        val responseCode = connection.responseCode
        if (responseCode in 200..299) { // Check if response is successful
          val response = connection.inputStream.bufferedReader().use { it.readText() }
          JSONObject(response) // Return response as JSON
        } else {
          null // Handle error response
        }
      } catch (e: Exception) {
        e.printStackTrace()
        null
      }
    }
  }
}
