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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gov.syria.sdm.R
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.application.Screen
import gov.syria.sdm.ui.components.DropdownMenu
import gov.syria.sdm.ui.components.TextField

@Composable
fun AddressInfoForm(navController: NavController, viewModel: ApplicationViewModel) {
  val address = viewModel.addressInfo.value
  val context = LocalContext.current
  val cities = listOf(
    "حلب",
    "دمشق",
    "حماة",
    "ادلب",
    "درعا",
    "دير الزور",
    "طرطوس",
    "اللاذقية",
    "الحسكة",
    "الرقة",
    "حمص",
    "ريف دمشق",
    "القنيطرة",
    "السويداء",
  )
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    DropdownMenu(
      selectedItem = address.city,
      options = cities,
      label = stringResource(R.string.city),
      icon = R.drawable.ic_city
    )
    TextField(
      inputText = address.trust,
      placeHolder = stringResource(R.string.trust),
      leadingIcon = R.drawable.ic_trust
    )
    TextField(
      inputText = address.restriction,
      placeHolder = stringResource(R.string.restriction),
      leadingIcon = R.drawable.ic_restriction
    )
    TextField(
      inputText = address.detailedOriginPlace,
      placeHolder = stringResource(R.string.detailedOriginPlace),
      leadingIcon = R.drawable.ic_location
    )
    TextField(
      inputText = address.nextTo,
      placeHolder = stringResource(R.string.nextTo),
      leadingIcon = R.drawable.ic_next_to
    )
    TextField(
      inputText = address.sector,
      placeHolder = stringResource(R.string.sector),
      leadingIcon = R.drawable.ic_sector
    )
    TextField(
      inputText = address.mass,
      placeHolder = stringResource(R.string.mass),
      leadingIcon = R.drawable.ic_mass
    )
    TextField(
      inputText = address.buildingNumber,
      placeHolder = stringResource(R.string.buildingNumber),
      leadingIcon = R.drawable.ic_city
    )
    TextField(
      inputText = address.floor,
      placeHolder = stringResource(R.string.floor),
      leadingIcon = R.drawable.ic_floor
    )
    TextField(
      inputText = address.apartment,
      placeHolder = stringResource(R.string.apartment),
      leadingIcon = R.drawable.ic_apartment
    )
    TextField(
      inputText = address.idCardResidence,
      placeHolder = stringResource(R.string.idCardResidence),
      leadingIcon = R.drawable.ic_id_card
    )

    Spacer(modifier = Modifier.height(20.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = {
          // Save and continue
          viewModel.updateAddressInfo(context)
          navController.navigate(Screen.SocialInfo.route)
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