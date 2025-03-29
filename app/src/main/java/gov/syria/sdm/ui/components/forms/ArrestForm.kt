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
fun ArrestForm(navController: NavController, viewModel: ApplicationViewModel) {
  val arrest = viewModel.arrest.value
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(text = stringResource(R.string.arrested), fontSize = 16.sp)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      RadioButton(
        selected = arrest.arrested.value,
        onClick = { arrest.arrested.value = true }
      )
      Text(text = stringResource(R.string.yes))

      RadioButton(
        selected = !arrest.arrested.value,
        onClick = { arrest.arrested.value = false }
      )
      Text(text = stringResource(R.string.no))
    }

    if (arrest.arrested.value) {
      TextField(
        inputText = arrest.arrestReason,
        placeHolder = stringResource(R.string.arrestReason),
        leadingIcon = R.drawable.ic_arrest
      )
      TextField(
        inputText = arrest.arrestDuration,
        placeHolder = stringResource(R.string.arrestDuration),
        leadingIcon = R.drawable.ic_time
      )
      TextField(
        inputText = arrest.arrestPlace,
        placeHolder = stringResource(R.string.arrestPlace),
        leadingIcon = R.drawable.ic_location
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
          viewModel.updateArrestInfo(context)
          navController.navigate(Screen.ContactInfo.route)
        }
      ) {
        Text(stringResource(R.string.saveContinue))
      }

      Button(
        onClick = {
          // Save and exit
          viewModel.updateArrestInfo(context)
          navController.navigate(Screen.Home.route)
        }
      ) {
        Text(stringResource(R.string.saveExit))
      }
    }
  }
}