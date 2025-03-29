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
import gov.syria.sdm.ui.components.DropdownMenu
import gov.syria.sdm.ui.components.TextField

@Composable
fun SocialInfoForm(navController: NavController, viewModel: ApplicationViewModel) {
  val social = viewModel.socialInfo.value
  val context = LocalContext.current
  val maritalStatuses = listOf(
    "أعزب/عزباء",
    "متزوج/متزوجة",
    "مطلق/مطلقة",
    "أرمل/أرملة"
  )
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    DropdownMenu(
      selectedItem = social.maritalStatus,
      options = maritalStatuses,
      label = stringResource(R.string.maritalStatus),
      icon = R.drawable.ic_marriage
    )
    if (social.maritalStatus.value == "متزوج/متزوجة") {
      TextField(
        inputText = social.wivesCount,
        placeHolder = stringResource(R.string.numberOfWives),
        leadingIcon = R.drawable.ic_female,
        keyboardType = KeyboardType.Number
      )
      TextField(
        inputText = social.childrenCount,
        placeHolder = stringResource(R.string.numberOfChildren),
        leadingIcon = R.drawable.ic_children,
        keyboardType = KeyboardType.Number
      )
      if (social.childrenCount.toString() != "0") {
        TextField(
          inputText = social.malesCount,
          placeHolder = stringResource(R.string.malesCount),
          leadingIcon = R.drawable.ic_gender_male,
          keyboardType = KeyboardType.Number
        )
        TextField(
          inputText = social.femalesCount,
          placeHolder = stringResource(R.string.femalesCount),
          leadingIcon = R.drawable.ic_gender_female,
          keyboardType = KeyboardType.Number
        )
      }
      if (social.marriageCertificateExists.value) {
        TextField(
          inputText = social.marriageCertificateNo,
          placeHolder = stringResource(R.string.marriageCertificateNo),
          leadingIcon = R.drawable.ic_certificate,
          keyboardType = KeyboardType.Number
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
          viewModel.updateSocialInfo(context)
          navController.navigate(Screen.GovernmentWork.route)
        }
      ) {
        Text(stringResource(R.string.saveContinue))
      }

      Button(
        onClick = {
          // Save and exit
          viewModel.updateSocialInfo(context)
          navController.navigate(Screen.Home.route)
        }
      ) {
        Text(stringResource(R.string.saveExit))
      }
    }
  }
}