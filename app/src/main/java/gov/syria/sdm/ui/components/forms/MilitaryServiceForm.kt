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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gov.syria.sdm.R
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.application.Screen
import gov.syria.sdm.ui.components.TextField

@Composable
fun MilitaryServiceForm(navController: NavController, viewModel: ApplicationViewModel) {
  val military = viewModel.military.value
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    Text(text = stringResource(R.string.militaryService), fontSize = 16.sp)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      RadioButton(
        selected = military.militaryService.value,
        onClick = { military.militaryService.value = true }
      )
      Text(text = stringResource(R.string.yes))

      RadioButton(
        selected = !military.militaryService.value,
        onClick = { military.militaryService.value = false }
      )
      Text(text = stringResource(R.string.no))
    }

    if (military.militaryService.value) {
      Text(text = stringResource(R.string.serviceType), fontSize = 16.sp)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        RadioButton(
          selected = military.serviceType.value,
          onClick = { military.serviceType.value = true }
        )
        Text(text = stringResource(R.string.yes))

        RadioButton(
          selected = !military.serviceType.value,
          onClick = { military.serviceType.value = false }
        )
        Text(text = stringResource(R.string.no))
      }
      TextField(
        inputText = military.serviceDuration,
        placeHolder = stringResource(R.string.serviceDuration),
        leadingIcon = R.drawable.ic_time
      )
      TextField(
        inputText = military.serviceLocation,
        placeHolder = stringResource(R.string.serviceLocation),
        leadingIcon = R.drawable.ic_location
      )
      TextField(
        inputText = military.specialization,
        placeHolder = stringResource(R.string.specialization),
        leadingIcon = R.drawable.ic_specialization
      )
      TextField(
        inputText = military.rank,
        placeHolder = stringResource(R.string.rank),
        leadingIcon = R.drawable.ic_rank
      )
      TextField(
        inputText = military.moveWithinTheService,
        placeHolder = stringResource(R.string.moveWithinTheService),
        leadingIcon = R.drawable.ic_move
      )
      TextField(
        inputText = military.serviceEnd,
        placeHolder = stringResource(R.string.serviceEnd),
        leadingIcon = R.drawable.ic_time,
        keyboardType = KeyboardType.Number
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
          viewModel.updateMilitaryInfo(context)
          navController.navigate(Screen.ArrestInfo.route)
        }
      ) {
        Text(stringResource(R.string.saveContinue))
      }

      Button(
        onClick = {
          // Save and exit
          viewModel.updateMilitaryInfo(context)
          navController.navigate(Screen.Home.route)
        }
      ) {
        Text(stringResource(R.string.saveExit))
      }
    }
  }
}