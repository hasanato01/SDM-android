package gov.syria.sdm.data

import androidx.compose.runtime.MutableState

data class Personal(
  val firstName: MutableState<String>,
  val surname: MutableState<String>,
  val fatherName: MutableState<String>,
  val motherName: MutableState<String>,
  val height: MutableState<String>,
  val weight: MutableState<String>,
  val faceColor: MutableState<String>,
  val eyeColor: MutableState<String>,
  val distinctiveMarks: MutableState<String>,
  val nationalNumber: MutableState<String>,
  val idNumber: MutableState<String>,
  val cardIssueDate: MutableState<String>,
  val placeOfBirth: MutableState<String>,
  val dateOfBirth: MutableState<String>,
)
