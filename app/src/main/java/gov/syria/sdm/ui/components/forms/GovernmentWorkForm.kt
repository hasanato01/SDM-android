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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gov.syria.sdm.R
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.application.Screen
import gov.syria.sdm.ui.components.TextField

@Composable
fun GovernmentWorkForm(navController: NavController, viewModel: ApplicationViewModel) {
  var hasWorkedWithGovernment by remember { mutableStateOf(false) }
  val govWork = viewModel.govWork.value
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    Text(text = stringResource(R.string.workedWithGovernment), fontSize = 16.sp)

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      RadioButton(
        selected = hasWorkedWithGovernment,
        onClick = { hasWorkedWithGovernment = true }
      )
      Text(text = stringResource(R.string.yes))

      RadioButton(
        selected = !hasWorkedWithGovernment,
        onClick = { hasWorkedWithGovernment = false }
      )
      Text(text = stringResource(R.string.no))
    }

    // Show these fields only if "Yes" is selected
    if (hasWorkedWithGovernment) {
      TextField(
        inputText = govWork.workDuration,
        placeHolder = stringResource(R.string.workDuration),
        leadingIcon = R.drawable.ic_work
      )
      TextField(
        inputText = govWork.workType,
        placeHolder = stringResource(R.string.workType),
        leadingIcon = R.drawable.ic_type
      )
      TextField(
        inputText = govWork.leaveDate,
        placeHolder = stringResource(R.string.leaveDate),
        leadingIcon = R.drawable.ic_calendar,
        keyboardType = KeyboardType.Number
      )
      TextField(
        inputText = govWork.leaveReason,
        placeHolder = stringResource(R.string.leaveReason),
        leadingIcon = R.drawable.ic_question
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = {
          // Save and continue
          viewModel.updateGovWorkInfo(context)
          navController.navigate(Screen.EducationalInfo.route)
        }
      ) {
        Text(stringResource(R.string.saveContinue))
      }

      Button(
        onClick = {
          // Save and exit
          viewModel.updateGovWorkInfo(context)
          navController.navigate(Screen.Home.route)
        }
      ) {
        Text(stringResource(R.string.saveExit))
      }
    }
  }
}
