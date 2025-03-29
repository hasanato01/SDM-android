package gov.syria.sdm.helper

class Helpers {
  fun normalizeEmail(email: String): String {
    val parts = email.split("@")
    return if (parts.size == 2 && parts[1].equals("gmail.com", ignoreCase = true)) {
      parts[0].replace(".", "") + "@" + parts[1]
    } else {
      email
    }
  }
}