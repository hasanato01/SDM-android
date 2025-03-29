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
import gov.syria.sdm.ui.components.TextField

@Composable
fun ContactInfoForm(navController: NavController, viewModel: ApplicationViewModel) {
  val contact = viewModel.contactInfo.value
  val context = LocalContext.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    TextField(
      inputText = contact.preferredContact,
      placeHolder = stringResource(R.string.preferredContact),
      leadingIcon = R.drawable.ic_question
    )
    TextField(
      inputText = contact.phone,
      placeHolder = stringResource(R.string.phone),
      leadingIcon = R.drawable.ic_phone,
      keyboardType = KeyboardType.Number
    )
    TextField(
      inputText = contact.telegram,
      placeHolder = stringResource(R.string.telegram),
      leadingIcon = R.drawable.ic_telegram
    )
    TextField(
      inputText = contact.whatsApp,
      placeHolder = stringResource(R.string.whatsapp),
      leadingIcon = R.drawable.ic_whatsapp,
      keyboardType = KeyboardType.Number
    )
    TextField(
      inputText = contact.alternativePhone,
      placeHolder = stringResource(R.string.alternativePhone),
      leadingIcon = R.drawable.ic_alternative_phone
    )
    TextField(
      inputText = contact.relationship,
      placeHolder = stringResource(R.string.relationship),
      leadingIcon = R.drawable.ic_person
    )
    TextField(
      inputText = contact.relationPhone,
      placeHolder = stringResource(R.string.relationPhone),
      leadingIcon = R.drawable.ic_alternative_phone,
      keyboardType = KeyboardType.Number
    )

    Spacer(modifier = Modifier.height(20.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = {
          viewModel.updateContactInfo(context)
        }
      ) {
        Text(stringResource(R.string.finish))
      }
    }
  }
}
