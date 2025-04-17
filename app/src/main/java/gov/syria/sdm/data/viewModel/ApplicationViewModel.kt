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
import gov.syria.sdm.api.Endpoints.USER_APPLICATION_EP
import gov.syria.sdm.data.Address
import gov.syria.sdm.data.Application
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
import org.json.JSONArray
import org.json.JSONObject

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

  var requests = mutableStateOf<List<Application>>(emptyList())
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
        fetchUserData(context)
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

  // Fetch user details API call
  fun fetchUserData(context: Context) {
    val token = getUserPrefs(context, "auth_token")
    val userId = getUserPrefs(context, "user_id")
    viewModelScope.launch {
      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP$userId",
          method = "GET",
          token = token
        )

        if (response != null && response.optString("message", "") == "Success") {
          val data = response.optJSONObject("data") ?: return@launch

          // Update personalInfo
          val userInfo = data.optJSONObject("userInfo") ?: JSONObject()
          personalInfo.value = Personal(
            firstName = mutableStateOf(userInfo.optString("name", "")),
            surname = mutableStateOf(userInfo.optString("surename", "")),
            fatherName = mutableStateOf(userInfo.optString("fatherName", "")),
            motherName = mutableStateOf(userInfo.optString("motherFullName", "")),
            height = mutableStateOf(userInfo.optString("height", "")),
            weight = mutableStateOf(userInfo.optString("weight", "")),
            faceColor = mutableStateOf(userInfo.optString("faceColor", "")),
            eyeColor = mutableStateOf(userInfo.optString("eyeColor", "")),
            distinctiveMarks = mutableStateOf(userInfo.optString("distinctiveMarks", "")),
            nationalNumber = mutableStateOf(userInfo.optString("nationalNumber", "")),
            idNumber = mutableStateOf(userInfo.optString("idNumber", "")),
            cardIssueDate = mutableStateOf(userInfo.optString("cardIssueDate", "").split("T").firstOrNull() ?: ""),
            placeOfBirth = mutableStateOf(userInfo.optString("placeOfBirth", "")),
            dateOfBirth = mutableStateOf(userInfo.optString("dateOfBirth", ""))
          )

          // Update addressInfo
          val addressInfoData = data.optJSONObject("addressInfo") ?: JSONObject()
          addressInfo.value = Address(
            city = mutableStateOf(addressInfoData.optString("city", "")),
            governorate = mutableStateOf(addressInfoData.optString("governorate", "")),
            trust = mutableStateOf(addressInfoData.optString("trust", "")),
            restriction = mutableStateOf(addressInfoData.optString("restriction", "")),
            detailedOriginPlace = mutableStateOf(addressInfoData.optString("detailedOriginPlace", "")),
            nextTo = mutableStateOf(addressInfoData.optString("nextTo", "")),
            sector = mutableStateOf(addressInfoData.optString("sector", "")),
            mass = mutableStateOf(addressInfoData.optString("mass", "")),
            buildingNumber = mutableStateOf(addressInfoData.optString("buildingNumber", "")),
            floor = mutableStateOf(addressInfoData.optString("floor", "")),
            apartment = mutableStateOf(addressInfoData.optString("apartment", "")),
            idCardResidence = mutableStateOf(data.optString("idCardResidence", ""))
          )

          // Update socialInfo
          val socialInfoData = data.optJSONObject("socialInfo") ?: JSONObject()
          socialInfo.value = Social(
            maritalStatus = mutableStateOf(socialInfoData.optString("maritalStatus", "")),
            wivesCount = mutableStateOf(socialInfoData.optString("wivesCount", "")),
            childrenCount = mutableStateOf(socialInfoData.optString("childrenCount", "")),
            malesCount = mutableStateOf(socialInfoData.optString("malesCount", "")),
            femalesCount = mutableStateOf(socialInfoData.optString("femalesCount", "")),
            marriageCertificateExists = mutableStateOf(socialInfoData.optBoolean("marriageCertificate", false)),
            marriageCertificateNo = mutableStateOf(socialInfoData.optString("familyBookOrMarriageCertificateNumber", ""))
          )

          // Update educational
          val educationalData = data.optJSONObject("educationaInfo") ?: JSONObject()
          val certificateNumberValue = educationalData.optString("certificateNumber", "")
          educational.value = Educational(
            educationalLevel = mutableStateOf(educationalData.optString("educationalLevel", "")),
            documentPresence = mutableStateOf(educationalData.optBoolean("documentPresence", false)),
            universityStudent = mutableStateOf(educationalData.optBoolean("universityStudent", false)),
            college = mutableStateOf(educationalData.optString("college", "")),
            academicYear = mutableStateOf(educationalData.optString("academicYear", "")),
            certificateType = mutableStateOf(educationalData.optString("certificateType", "")),
            collegeOrInstitute = mutableStateOf(educationalData.optString("collegeOrInstitute", "")),
            specializationEdu = mutableStateOf(educationalData.optString("specializationEdu", "")),
            yearCertificate = mutableStateOf(educationalData.optString("yearCertificate", "")),
            graduationRate = mutableStateOf(educationalData.optString("graduationRate", "")),
            certificateNumber = mutableStateOf(certificateNumberValue),
            certificateExists = mutableStateOf(certificateNumberValue.isNotBlank())
          )


          // Update courses
          val coursesArray = data.optJSONArray("coursesAndSkills") ?: JSONArray()
          val fetchedCourses = mutableListOf<Course>()
          for (i in 0 until coursesArray.length()) {
            val course = coursesArray.getJSONObject(i)
            fetchedCourses.add(
              Course(
                type = course.optString("courseType", ""),
                level = course.optString("courseLevel", ""),
                duration = course.optString("courseDuration", ""),
                date = course.optString("date", ""),
                organizing = course.optString("organizing", ""),
                haveDocument = course.optBoolean("haveDocument", false),
              )
            )
          }
          courses.value = fetchedCourses

          // Update travelList
          val travelArray = data.optJSONArray("travelInformation") ?: JSONArray()
          val fetchedTravel = mutableListOf<TravelEntry>()
          for (i in 0 until travelArray.length()) {
            val travel = travelArray.getJSONObject(i)
            fetchedTravel.add(
              TravelEntry(
                travelPlace = mutableStateOf(travel.optString("travelPlace", "")),
                travelReason = mutableStateOf(travel.optString("travelReason", "")),
                travelYear = mutableStateOf(travel.optString("travelYear", "")),
                travelDuration = mutableStateOf(travel.optString("duration", ""))
              )
            )
          }
          travelList.value = fetchedTravel

          // Update govWork
          val govWorkData = data.optJSONObject("workingWithTheGovernment") ?: JSONObject()
          govWork.value = GovernmentWork(
            workDuration = mutableStateOf(govWorkData.optString("workDuration", "")),
            workType = mutableStateOf(govWorkData.optString("workType", "")),
            leaveDate = mutableStateOf(govWorkData.optString("leaveDate", "")),
            leaveReason = mutableStateOf(govWorkData.optString("leaveReason", ""))
          )

          // Update military
          val militaryData = data.optJSONObject("militaryService") ?: JSONObject()
          val serviceType = militaryData.optString("serviceType", "")
          military.value = MilitaryService(
            militaryService = mutableStateOf(serviceType.isNotBlank()),
            serviceType = mutableStateOf(serviceType === "true"),
            serviceDuration = mutableStateOf(militaryData.optString("serviceDuration", "")),
            serviceLocation = mutableStateOf(militaryData.optString("serviceLocation", "")),
            specialization = mutableStateOf(militaryData.optString("specialization", "")),
            rank = mutableStateOf(militaryData.optString("rank", "")),
            moveWithinTheService = mutableStateOf(militaryData.optString("moveWithinTheService", "")),
            serviceEnd = mutableStateOf(militaryData.optString("serviceEnd", ""))
          )

          // Update arrest
          val arrestData = data.optJSONObject("militaryService") ?: JSONObject()
          arrest.value = Arrest(
            arrested = mutableStateOf(arrestData.optBoolean("arrestStatus", false)),
            arrestReason = mutableStateOf(arrestData.optString("arrestReason", "")),
            arrestDuration = mutableStateOf(arrestData.optString("arrestDuration", "")),
            arrestPlace = mutableStateOf(arrestData.optString("arrestPlace", ""))
          )

          // Update contactInfo
          val contactInfoData = data.optJSONObject("contactInfo") ?: JSONObject()
          contactInfo.value = Contact(
            preferredContact = mutableStateOf(contactInfoData.optString("preferredContact", "")),
            phone = mutableStateOf(contactInfoData.optString("phone", "")),
            telegram = mutableStateOf(contactInfoData.optString("telegram", "")),
            whatsApp = mutableStateOf(contactInfoData.optString("whatsApp", "")),
            alternativePhone = mutableStateOf(contactInfoData.optString("alternativePhone", "")),
            relationship = mutableStateOf(contactInfoData.optString("relationship", "")),
            relationPhone = mutableStateOf(contactInfoData.optString("relationPhone", ""))
          )

          // Update other fields
          chronicDisease.value = data.optString("chronicDisease", "")
          about.value = data.optString("cv", "")

          println("DEBUG: User data fetched and set successfully!")
        } else {
          println("DEBUG: Failed to fetch user data")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }

  // Personal info update API call
  fun updatePersonalInfo(context: Context) {
    viewModelScope.launch {
      val userId = getUserPrefs(context, "user_id")
      val token = getUserPrefs(context, "auth_token")
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
          requestBody = requestBody,
          token = token
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
    val token = getUserPrefs(context, "auth_token")
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
        ),
        "idCardResidence" to addressInfo.value.idCardResidence.value
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody,
          token = token
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
    val token = getUserPrefs(context, "auth_token")
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
          requestBody = requestBody,
          token = token
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
    val token = getUserPrefs(context, "auth_token")
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
          requestBody = requestBody,
          token = token
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
    val token = getUserPrefs(context, "auth_token")
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
          requestBody = requestBody,
          token = token
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
    val token = getUserPrefs(context, "auth_token")
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
          requestBody = requestBody,
          token = token
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
    val token = getUserPrefs(context, "auth_token")
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
          requestBody = requestBody,
          token = token
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
    val token = getUserPrefs(context, "auth_token")
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
          requestBody = requestBody,
          token = token
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
    val token = getUserPrefs(context, "auth_token")
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
          requestBody = requestBody,
          token = token
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
    val token = getUserPrefs(context, "auth_token")
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
          requestBody = requestBody,
          token = token
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
  fun updateOtherInfo(context: Context) {
    val token = getUserPrefs(context, "auth_token")
    viewModelScope.launch {
      val requestBody = mapOf(
        "chronicDisease" to chronicDisease,
        "cv" to about,
        "idCardResidence" to addressInfo.value.idCardResidence.value
      )

      try {
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$AFFILIATES_EP${getUserPrefs(context, "user_id")}",
          method = "PUT",
          requestBody = requestBody,
          token = token
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

  fun getMyRequests(context: Context) {
    val token = getUserPrefs(context, "auth_token")
    println("token $token")
    viewModelScope.launch {
      try {
        println("asd ${getUserPrefs(context, "user_id")}")
        val response = ApiHelper.makeRequest(
          url = BASE_URL,
          endpoint = "$USER_APPLICATION_EP${getUserPrefs(context, "user_id")}",
          method = "GET",
          token = token
        )
        println("res $response")
        if (response != null && response.optString("message", "") == "Success") {
          val dataArray = response.optJSONArray("data")
          val fetchedRequests = mutableListOf<Application>()

          for (i in 0 until (dataArray?.length() ?: 0)) {
            val item = dataArray!!.getJSONObject(i)
            fetchedRequests.add(
              Application(
                userId = item.optString("userId", ""),
                city = item.optString("city", "-"),
                status = item.optString("status", "-"),
                department = item.optString("department", "-"),
                desc = item.optString("desc", "-"),
                createdAt = item.optString("createdAt", ""),
                updatedAt = item.optString("updatedAt", "")
              )
            )
          }

          requests.value = fetchedRequests
        } else if (response != null && response.optString("message", "") == "404") {
          println("DEBUG: No applications")
        } else {
          println("DEBUG: Failed to fetch requests")
        }
      } catch (e: Exception) {
        println("DEBUG: Exception occurred - ${e.message}")
      }
    }
  }
}
