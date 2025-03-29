package gov.syria.sdm.data

import androidx.compose.runtime.MutableState

data class Educational(
  val educationalLevel: MutableState<String>,
  val documentPresence: MutableState<Boolean>,
  val universityStudent: MutableState<Boolean>,
  val college: MutableState<String>,
  val academicYear: MutableState<String>,
  val certificateType: MutableState<String>,
  val collegeOrInstitute: MutableState<String>,
  val specializationEdu: MutableState<String>,
  val yearCertificate: MutableState<String>,
  val graduationRate: MutableState<String>,
  val certificateExists: MutableState<Boolean>,
  val certificateNumber: MutableState<String>,
)
