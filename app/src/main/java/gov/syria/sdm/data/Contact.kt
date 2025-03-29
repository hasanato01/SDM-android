package gov.syria.sdm.data

import androidx.compose.runtime.MutableState

data class Contact(
  val preferredContact: MutableState<String>,
  val phone: MutableState<String>,
  val telegram: MutableState<String>,
  val whatsApp: MutableState<String>,
  val alternativePhone: MutableState<String>,
  val relationship: MutableState<String>,
  val relationPhone: MutableState<String>,
)
