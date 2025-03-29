package gov.syria.sdm.data

import androidx.compose.runtime.MutableState

data class MilitaryService(
  val militaryService: MutableState<Boolean>,
  val serviceType: MutableState<Boolean>,
  val serviceDuration: MutableState<String>,
  val serviceLocation: MutableState<String>,
  val specialization: MutableState<String>,
  val rank: MutableState<String>,
  val moveWithinTheService: MutableState<String>,
  val serviceEnd: MutableState<String>,
)
