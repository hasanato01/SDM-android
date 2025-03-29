package gov.syria.sdm.data

import androidx.compose.runtime.MutableState

data class Social(
  var maritalStatus: MutableState<String>,
  var wivesCount: MutableState<String>,
  var childrenCount: MutableState<String>,
  var malesCount: MutableState<String>,
  var femalesCount: MutableState<String>,
  var marriageCertificateExists: MutableState<Boolean>,
  var marriageCertificateNo: MutableState<String>,
)
