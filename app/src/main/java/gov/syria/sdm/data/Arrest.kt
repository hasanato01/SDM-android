package gov.syria.sdm.data

import androidx.compose.runtime.MutableState

data class Arrest(
  val arrested: MutableState<Boolean>,
  val arrestReason: MutableState<String>,
  val arrestDuration: MutableState<String>,
  val arrestPlace: MutableState<String>,
)
