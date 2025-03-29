package gov.syria.sdm.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gov.syria.sdm.R
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.application.Screen
import gov.syria.sdm.ui.components.TextField

@Composable
fun PersonalInfoForm(navController: NavController, viewModel: ApplicationViewModel) {
  val personal = viewModel.personalInfo.value
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    TextField(
      inputText = personal.firstName,
      placeHolder = stringResource(R.string.firstName),
      leadingIcon = R.drawable.ic_person
    )
    TextField(
      inputText = personal.surname,
      placeHolder = stringResource(R.string.surname),
      leadingIcon = R.drawable.ic_person
    )
    TextField(
      inputText = personal.fatherName,
      placeHolder = stringResource(R.string.fatherName),
      leadingIcon = R.drawable.ic_person
    )
    TextField(
      inputText = personal.motherName,
      placeHolder = stringResource(R.string.motherName),
      leadingIcon = R.drawable.ic_female
    )
    TextField(
      inputText = personal.height,
      placeHolder = stringResource(R.string.height),
      leadingIcon = R.drawable.ic_height,
      keyboardType = KeyboardType.Number
    )
    TextField(
      inputText = personal.weight,
      placeHolder = stringResource(R.string.weight),
      leadingIcon = R.drawable.ic_weight,
      keyboardType = KeyboardType.Number
    )
    TextField(
      inputText = personal.faceColor,
      placeHolder = stringResource(R.string.faceColor),
      leadingIcon = R.drawable.ic_face
    )
    TextField(
      inputText = personal.eyeColor,
      placeHolder = stringResource(R.string.eyeColor),
      leadingIcon = R.drawable.ic_eye
    )
    TextField(
      inputText = personal.distinctiveMarks,
      placeHolder = stringResource(R.string.distinctiveMarks),
      leadingIcon = R.drawable.ic_mark
    )
    TextField(
      inputText = personal.nationalNumber,
      placeHolder = stringResource(R.string.nationalNumber),
      leadingIcon = R.drawable.ic_id_card,
      keyboardType = KeyboardType.Number
    )
    TextField(
      inputText = personal.idNumber,
      placeHolder = stringResource(R.string.idNumber),
      leadingIcon = R.drawable.ic_id_card,
      keyboardType = KeyboardType.Number
    )
    TextField(
      inputText = personal.cardIssueDate,
      placeHolder = stringResource(R.string.cardIssueDate),
      leadingIcon = R.drawable.ic_calendar,
      keyboardType = KeyboardType.Number
    )
    TextField(
      inputText = personal.placeOfBirth,
      placeHolder = stringResource(R.string.placeOfBirth),
      leadingIcon = R.drawable.ic_pin_drop
    )
    TextField(
      inputText = personal.dateOfBirth,
      placeHolder = stringResource(R.string.dateOfBirth),
      leadingIcon = R.drawable.ic_calendar,
      keyboardType = KeyboardType.Number
    )

    Spacer(modifier = Modifier.height(20.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = {
          // Save and continue
          viewModel.updatePersonalInfo(context)
          navController.navigate(Screen.AddressInfo.route)
        }
      ) {
        Text(stringResource(R.string.saveContinue))
      }

      Button(
        onClick = {
          // Save and exit
          viewModel.updatePersonalInfo(context)
          navController.navigate(Screen.Home.route)
        }
      ) {
        Text(stringResource(R.string.saveExit))
      }
    }
  }
}