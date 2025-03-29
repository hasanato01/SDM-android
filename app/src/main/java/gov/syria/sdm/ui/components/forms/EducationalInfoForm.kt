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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gov.syria.sdm.R
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.application.Screen
import gov.syria.sdm.ui.components.TextField

@Composable
fun EducationalInfoForm(navController: NavController, viewModel: ApplicationViewModel) {
  val educational = viewModel.educational.value
  val context = LocalContext.current
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    TextField(
      inputText = educational.educationalLevel,
      placeHolder = stringResource(R.string.educationalLevel),
      leadingIcon = R.drawable.ic_city
    )

    Text(text = stringResource(R.string.haveDocument), fontSize = 16.sp)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      RadioButton(
        selected = educational.documentPresence.value,
        onClick = { educational.documentPresence.value = true }
      )
      Text(text = stringResource(R.string.yes))

      RadioButton(
        selected = !educational.documentPresence.value,
        onClick = { educational.documentPresence.value = false }
      )
      Text(text = stringResource(R.string.no))
    }

    Text(text = stringResource(R.string.universityStudent), fontSize = 16.sp)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      RadioButton(
        selected = educational.universityStudent.value,
        onClick = { educational.universityStudent.value = true }
      )
      Text(text = stringResource(R.string.yes))

      RadioButton(
        selected = !educational.universityStudent.value,
        onClick = { educational.universityStudent.value = false }
      )
      Text(text = stringResource(R.string.no))
    }

    if (educational.universityStudent.value) {
      TextField(
        inputText = educational.college,
        placeHolder = stringResource(R.string.college),
        leadingIcon = R.drawable.ic_education
      )
      TextField(
        inputText = educational.academicYear,
        placeHolder = stringResource(R.string.academicYear),
        leadingIcon = R.drawable.ic_calendar
      )
      TextField(
        inputText = educational.certificateType,
        placeHolder = stringResource(R.string.certificateType),
        leadingIcon = R.drawable.ic_question
      )
      TextField(
        inputText = educational.collegeOrInstitute,
        placeHolder = stringResource(R.string.collegeOrInstitute),
        leadingIcon = R.drawable.ic_city
      )
      TextField(
        inputText = educational.specializationEdu,
        placeHolder = stringResource(R.string.specializationEdu),
        leadingIcon = R.drawable.ic_specialization
      )
      TextField(
        inputText = educational.yearCertificate,
        placeHolder = stringResource(R.string.yearOfCertificate),
        leadingIcon = R.drawable.ic_certificate
      )
      TextField(
        inputText = educational.graduationRate,
        placeHolder = stringResource(R.string.graduationRate),
        leadingIcon = R.drawable.ic_apartment
      )

      Text(text = stringResource(R.string.certificateExists), fontSize = 16.sp)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        RadioButton(
          selected = educational.certificateExists.value,
          onClick = { educational.certificateExists.value = true }
        )
        Text(text = stringResource(R.string.yes))

        RadioButton(
          selected = !educational.certificateExists.value,
          onClick = { educational.certificateExists.value = false }
        )
        Text(text = stringResource(R.string.no))
      }
      if (educational.certificateExists.value) {
        TextField(
          inputText = educational.certificateNumber,
          placeHolder = stringResource(R.string.certificateNumber),
          leadingIcon = R.drawable.ic_apartment
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = {
          // Save and continue
          viewModel.updateEducationInfo(context)
          navController.navigate(Screen.TravelInformation.route)
        }
      ) {
        Text(stringResource(R.string.saveContinue))
      }

      Button(
        onClick = {
          // Save and exit
          viewModel.updateEducationInfo(context)
          navController.navigate(Screen.Home.route)
        }
      ) {
        Text(stringResource(R.string.saveExit))
      }
    }
  }
}