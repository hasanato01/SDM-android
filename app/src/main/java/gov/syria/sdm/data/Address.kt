package gov.syria.sdm.data

import androidx.compose.runtime.MutableState

data class Address(
  val city: MutableState<String>,
  val governorate: MutableState<String>,
  val trust: MutableState<String>,
  val restriction: MutableState<String>,
  val detailedOriginPlace: MutableState<String>,
  val nextTo: MutableState<String>,
  val sector: MutableState<String>,
  val mass: MutableState<String>,
  val buildingNumber: MutableState<String>,
  val floor: MutableState<String>,
  val apartment: MutableState<String>,
  val idCardResidence: MutableState<String>,
)
