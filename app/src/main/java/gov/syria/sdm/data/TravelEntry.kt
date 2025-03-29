package gov.syria.sdm.data

import androidx.compose.runtime.MutableState

data class TravelEntry(
  val travelPlace: MutableState<String>,
  val travelReason: MutableState<String>,
  val travelYear: MutableState<String>,
  val travelDuration: MutableState<String>
)

