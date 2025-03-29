package gov.syria.sdm.data

import androidx.compose.runtime.MutableState

data class GovernmentWork(
  var workDuration: MutableState<String>,
  var workType: MutableState<String>,
  var leaveDate: MutableState<String>,
  var leaveReason: MutableState<String>,
)
