package gov.syria.sdm.data.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gov.syria.sdm.api.ApiHelper
import gov.syria.sdm.api.Endpoints.AFFILIATES_EP
import gov.syria.sdm.api.Endpoints.BASE_URL
import gov.syria.sdm.api.Endpoints.LOGIN_EP
import gov.syria.sdm.api.Endpoints.SIGNUP_EP
import gov.syria.sdm.data.Address
import gov.syria.sdm.data.Arrest
import gov.syria.sdm.data.Contact
import gov.syria.sdm.data.Course
import gov.syria.sdm.data.Educational
import gov.syria.sdm.data.GovernmentWork
import gov.syria.sdm.data.MilitaryService
import gov.syria.sdm.data.Personal
import gov.syria.sdm.data.Social
import gov.syria.sdm.data.TravelEntry
import gov.syria.sdm.helper.Helpers
import kotlinx.coroutines.launch

class ApplicationViewModel : ViewModel() {
  //region Data
  //Personal form
  var personalInfo = mutableStateOf(
    Personal(
      firstName = mutableStateOf(""),
      surname = mutableStateOf(""),
      fatherName = mutableStateOf(""),
      motherName = mutableStateOf(""),
      height = mutableStateOf(""),
      weight = mutableStateOf(""),
      faceColor = mutableStateOf(""),
      eyeColor = mutableStateOf(""),
      distinctiveMarks = mutableStateOf(""),
      nationalNumber = mutableStateOf(""),
      idNumber = mutableStateOf(""),
      cardIssueDate = mutableStateOf(""),
      placeOfBirth = mutableStateOf(""),
      dateOfBirth = mutableStateOf(""),
    )
  )

  //Address form
  var addressInfo = mutableStateOf(
    Address(
      city = mutableStateOf(""),
      governorate = mutableStateOf(""),
      trust = mutableStateOf(""),
      restriction = mutableStateOf(""),
      detailedOriginPlace = mutableStateOf(""),
      nextTo = mutableStateOf(""),
      sector = mutableStateOf(""),
      mass = mutableStateOf(""),
      buildingNumber = mutableStateOf(""),
      floor = mutableStateOf(""),
      apartment = mutableStateOf(""),
      idCardResidence = mutableStateOf(""),
    )
  )

  //Social form
  var socialInfo = mutableStateOf(
    Social(
      maritalStatus = mutableStateOf(""),
      wivesCount = mutableStateOf(""),
      childrenCount = mutableStateOf(""),
      malesCount = mutableStateOf(""),
      femalesCount = mutableStateOf(""),
      marriageCertificateExists = mutableStateOf(false),
      marriageCertificateNo = mutableStateOf(""),
    )
  )

  //Educational form
  var educational = mutableStateOf(
    Educational(
      educationalLevel = mutableStateOf(""),
      documentPresence = mutableStateOf(false),
      universityStudent = mutableStateOf(false),
      college = mutableStateOf(""),
      academicYear = mutableStateOf(""),
      certificateType = mutableStateOf(""),
      collegeOrInstitute = mutableStateOf(""),
      specializationEdu = mutableStateOf(""),
      yearCertificate = mutableStateOf(""),
      graduationRate = mutableStateOf(""),
      certificateExists = mutableStateOf(false),
      certificateNumber = mutableStateOf(""),
    )
  )

  //Courses form
  var courses = mutableStateOf(listOf<Course>())

  //Travel form
  var travelList = mutableStateOf(listOf<TravelEntry>())

  //Government work form
  var govWork = mutableStateOf(
    GovernmentWork(
      workDuration = mutableStateOf(""),
      workType = mutableStateOf(""),
      leaveDate = mutableStateOf(""),
      leaveReason = mutableStateOf(""),
    )
  )

  //Military form
  var military = mutableStateOf(
    MilitaryService(
      militaryService = mutableStateOf(false),
      serviceType = mutableStateOf(false),
      serviceDuration = mutableStateOf(""),
      serviceLocation = mutableStateOf(""),
      specialization = mutableStateOf(""),
      rank = mutableStateOf(""),
      moveWithinTheService = mutableStateOf(""),
      serviceEnd = mutableStateOf(""),
    )
  )

  //Arrest form
  var arrest = mutableStateOf(
    Arrest(
      arrested = mutableStateOf(false),
      arrestReason = mutableStateOf(""),
      arrestDuration = mutableStateOf(""),
      arrestPlace = mutableStateOf(""),
    )
  )

  //Contact form
  var contactInfo = mutableStateOf(
    Contact(
      preferredContact = mutableStateOf(""),
      phone = mutableStateOf(""),
      telegram = mutableStateOf(""),
      whatsApp = mutableStateOf(""),
      alternativePhone = mutableStateOf(""),
      relationship = mutableStateOf(""),
      relationPhone = mutableStateOf("")
    )
  )

  var chronicDisease = mutableStateOf("")
  var about = mutableStateOf("")
  //endregion

  val helpers = Helpers()

  // Sign In API call
  suspend fun signIn(context: Context, email: String, password: String): Boolean {
    val requestBody = mapOf(
      "email" to helpers.normalizeEmail(email),
      "password" to password
    )

    val response = ApiHelper.makeRequest(
      url = BASE_URL,
      endpoint = LOGIN_EP,
      method = "POST",
      requestBody = requestBody
    )

    println(response)

    if (response != null && response.optString("status") == "true") {
      val token = response.optString("token")
      val userId = response.optJSONObject("data")?.optString("_id") ?: ""

      if (token.isNotEmpty() && userId.isNotEmpty()) {
        saveToSharedPreferences(context, token, userId)
      }
      return true
    }

    return false
  }

  // Sign Up API call
  suspend fun signUp(email: String, firstName: String, lastName: String): Boolean {
    val requestBody = mapOf(
      "firstName" to firstName,
      "lastName" to lastName,
      "email" to helpers.normalizeEmail(email)
    )
    val response = ApiHelper.makeRequest(
      url = BASE_URL,
      endpoint = SIGNUP_EP,
      method = "POST",
      requestBody = requestBody
    )
    return response?.optBoolean("status", false) == true
  }

  // Personal info update API call
  fun updatePersonalInfo(context: Context) {
    viewModelScope.launch {
      val userId = getUserPrefs(context, "user_id")
      if (userId.isNullOrEmpty()) {
        println("DEBUG: User ID is missing!")
      }

      val requestBody = mapOf(
        "userInfo" to mapOf(
          "name" to personalInfo.value.firstName.value,
          "surename" to personalInfo.value.surname.value,
          "fatherName" to personalInfo.value.fatherName.value,
          "motherFullName" to personalInfo.value.motherName.value,
          "height" to personalInfo.value.height.value,
          "weight" to personalInfo.value.weight.value,
          "faceColor" to personalInfo.value.faceColor.value,
          "eyeColor" to personalInfo.value.eyeColor.value,
          "distinctiveMarks" to personalInfo.value.distinctiveMarks.value,
          "nationalNumber" to personalInfo.value.nationalNumber.value,
          "idNumber" to personalInfo.value.idNumber.value,
          "cardIssueDate" to personalInfo.value.cardIssueDate.value,
          "placeOfBirth" to personalInfo.value.placeOfBirth.value,
          "dateOfBirth" to personalInfo.value.dateOfBirth.value,
        )
      )


      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP$userId",
          method = "PUT",
          requestBody = requestBody
        )

        println("Response: $response")

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Profile updated successfully!")
        } else {
          println("DEBUG: Failed to update profile")
        }
      } catch (e: Exception) {
        println("DEBUG: Network request failed: ${e.message}")
      }
    }
  }

  // Address update API call
  fun updateAddressInfo(context: Context) {
    viewModelScope.launch {
      val requestBody = mapOf(
        "addressInfo" to mapOf(
          "city" to addressInfo.value.city.value,
          "governorate" to addressInfo.value.governorate.value,
          "trust" to addressInfo.value.trust.value,
          "restriction" to addressInfo.value.restriction.value,
          "detailedOriginPlace" to addressInfo.value.detailedOriginPlace.value,
          "nextTo" to addressInfo.value.nextTo.value,
          "sector" to addressInfo.value.sector.value,
          "mass" to addressInfo.value.mass.value,
          "buildingNumber" to addressInfo.value.buildingNumber.value,
          "floor" to addressInfo.value.floor.value,
          "apartment" to addressInfo.value.apartment.value,
        )
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody
        )

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Address updated successfully!")
        } else {
          println("DEBUG: Failed to update address")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Social info update API call
  fun updateSocialInfo(context: Context) {
    viewModelScope.launch {
      val requestBody = mapOf(
        "socialInfo" to mapOf(
          "maritalStatus" to socialInfo.value.maritalStatus.value,
          "wivesCount" to socialInfo.value.wivesCount.value,
          "childrenCount" to socialInfo.value.childrenCount.value,
          "malesCount" to socialInfo.value.malesCount.value,
          "femalesCount" to socialInfo.value.femalesCount.value,
          "marriageCertificateExists" to socialInfo.value.marriageCertificateExists.value,
          "marriageCertificateNo" to socialInfo.value.marriageCertificateNo.value
        )
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody
        )

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Social info updated successfully!")
        } else {
          println("DEBUG: Failed to update social info")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Government work info update API call
  fun updateGovWorkInfo(context: Context) {
    viewModelScope.launch {
      val requestBody = mapOf(
        "workingWithTheGovernment" to mapOf(
          "workDuration" to govWork.value.workDuration.value,
          "workType" to govWork.value.workType.value,
          "leaveDate" to govWork.value.leaveDate.value,
          "leaveReason" to govWork.value.leaveReason.value
        )
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody
        )

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Government work info updated successfully!")
        } else {
          println("DEBUG: Failed to update government work info")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Educational info update API call
  fun updateEducationInfo(context: Context) {
    viewModelScope.launch {
      val requestBody = mapOf(
        "educationaInfo" to mapOf(
          "educationalLevel" to educational.value.educationalLevel.value,
          "documentPresence" to educational.value.documentPresence.value,
          "universityStudent" to educational.value.universityStudent.value,
          "college" to educational.value.college.value,
          "academicYear" to educational.value.academicYear.value,
          "certificateType" to educational.value.certificateType.value,
          "collegeOrInstitute" to educational.value.collegeOrInstitute.value,
          "specializationEdu" to educational.value.specializationEdu.value,
          "yearCertificate" to educational.value.yearCertificate.value,
          "graduationRate" to educational.value.graduationRate.value,
          "certificateExists" to educational.value.certificateExists.value,
          "certificateNumber" to educational.value.certificateNumber.value
        )
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody
        )

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Education info updated successfully!")
        } else {
          println("DEBUG: Failed to update education info")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Travel info update API call
  fun updateTravelInfo(context: Context) {
    viewModelScope.launch {
      if (travelList.value.isEmpty()) {
        println("DEBUG: No travel entries to update")
        return@launch
      }

      val requestBody = mapOf(
        "travelInformation" to travelList.value.map { travel ->
          mapOf(
            "travelPlace" to travel.travelPlace.value,
            "travelReason" to travel.travelReason.value,
            "travelYear" to travel.travelYear.value,
            "travelDuration" to travel.travelDuration.value
          )
        }
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody
        )

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Travel entries updated successfully!")
        } else {
          println("DEBUG: Failed to update travel entries")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Courses info update API call
  fun updateCoursesInfo(context: Context) {
    viewModelScope.launch {
      if (courses.value.isEmpty()) {
        println("DEBUG: No courses to update")
        return@launch
      }

      val requestBody = mapOf(
        "coursesAndSkills" to courses.value.map { course ->
          mapOf(
            "type" to course.type,
            "level" to course.level,
            "duration" to course.duration,
            "date" to course.date,
            "organizing" to course.organizing,
            "haveDocument" to course.haveDocument
          )
        }
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody
        )

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Courses updated successfully!")
        } else {
          println("DEBUG: Failed to update courses")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Military info update API call
  fun updateMilitaryInfo(context: Context) {
    viewModelScope.launch {
      val requestBody = mapOf(
        "militaryService" to mapOf(
          "militaryService" to military.value.militaryService.value,
          "serviceType" to military.value.serviceType.value,
          "serviceDuration" to military.value.serviceDuration.value,
          "serviceLocation" to military.value.serviceLocation.value,
          "specialization" to military.value.specialization.value,
          "rank" to military.value.rank.value,
          "moveWithinTheService" to military.value.moveWithinTheService.value,
          "serviceEnd" to military.value.serviceEnd.value
        )
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody
        )

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Military info updated successfully!")
        } else {
          println("DEBUG: Failed to update military info")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Arrest info update API call
  fun updateArrestInfo(context: Context) {
    viewModelScope.launch {
      val requestBody = mapOf(
        "militaryService" to mapOf(
          "arrested" to arrest.value.arrested.value,
          "arrestReason" to arrest.value.arrestReason.value,
          "arrestDuration" to arrest.value.arrestDuration.value,
          "arrestPlace" to arrest.value.arrestPlace.value
        )
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody
        )

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Arrest info updated successfully!")
        } else {
          println("DEBUG: Failed to update arrest info")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Contact update API call
  fun updateContactInfo(context: Context) {
    viewModelScope.launch {
      val requestBody = mapOf(
        "contactInformation" to mapOf(
          "preferredContact" to contactInfo.value.preferredContact.value,
          "phone" to contactInfo.value.phone.value,
          "telegram" to contactInfo.value.telegram.value,
          "whatsApp" to contactInfo.value.whatsApp.value,
          "alternativePhone" to contactInfo.value.alternativePhone.value,
          "relationship" to contactInfo.value.relationship.value,
          "relationPhone" to contactInfo.value.relationPhone.value
        )
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody
        )

        if (response != null && response.optString("message", "") == "Success") {
          println("DEBUG: Contact info updated successfully!")
        } else {
          println("DEBUG: Failed to update contact info")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Courses info update API call
  fun updateOtherInfo(context: Context){
    viewModelScope.launch {
      val requestBody = mapOf(
        "chronicDisease" to chronicDisease,
        "cv" to about,
        "idCardResidence" to addressInfo.value.idCardResidence.value
      )

      val response = ApiHelper.makeRequest(
        url = BASE_URL,
        endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
        method = "PUT",
        requestBody = requestBody
      )

      if (response != null && response.optBoolean("status", false)) {
        println("DEBUG: Other info updated successfully!")
      } else {
        println("DEBUG: Failed to update other info")
      }
    }
  }

  fun saveToSharedPreferences(context: Context, token: String, userId: String) {
    val sharedPreferences: SharedPreferences =
      context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    sharedPreferences.edit {
      putString("auth_token", token)
      putString("user_id", userId)
    }
  }

  fun getUserPrefs(context: Context, field: String): String? {
    val sharedPreferences: SharedPreferences =
      context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(field, null)
  }

  fun clearUserData(context: Context) {
    val sharedPreferences: SharedPreferences =
      context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit { clear() }
  }
}
