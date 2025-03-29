package gov.syria.sdm.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gov.syria.sdm.R
import gov.syria.sdm.data.TravelEntry
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.application.Screen
import gov.syria.sdm.ui.components.TextField

@Composable
fun TravelInformationForm(navController: NavController, viewModel: ApplicationViewModel) {
  val travelEntries = remember { viewModel.travelList }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Header
    item {
      Text(
        text = stringResource(R.string.travelHistory),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
      )
    }

    // Travel Entries List
    items(travelEntries.value) { travelEntry ->
      TravelRow(
        travelEntry = travelEntry,
        onRemove = {
          travelEntries.value -= travelEntry
        }
      )
      HorizontalDivider()
    }

    // Add New Entry Button
    item {
      Button(
        onClick = {
          travelEntries.value += TravelEntry(
            travelPlace = mutableStateOf(""),
            travelReason = mutableStateOf(""),
            travelYear = mutableStateOf(""),
            travelDuration = mutableStateOf("")
          )
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      ) {
        Text(text = stringResource(R.string.addTravelEntry))
      }
    }

    // Spacer
    item {
      Spacer(modifier = Modifier.height(20.dp))
    }

    // Navigation Buttons
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Button(
          onClick = {
            // Save and continue
            // viewModel.updatePersonalInfo(firstName, lastName)
            navController.navigate(Screen.CoursesAndSkills.route)
          }
        ) {
          Text(stringResource(R.string.saveContinue))
        }

        Button(
          onClick = {
            // Save and exit
            // viewModel.updatePersonalInfo(firstName, lastName)
          }
        ) {
          Text(stringResource(R.string.saveExit))
        }
      }
    }
  }
}

@Composable
fun TravelRow(travelEntry: TravelEntry, onRemove: () -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(Modifier.weight(1f)) {
      TextField(
        inputText = travelEntry.travelPlace,
        placeHolder = stringResource(R.string.travelPlace),
        leadingIcon = R.drawable.ic_train
      )
      TextField(
        inputText = travelEntry.travelReason,
        placeHolder = stringResource(R.string.travelReason),
        leadingIcon = R.drawable.ic_question
      )
      TextField(
        inputText = travelEntry.travelYear,
        placeHolder = stringResource(R.string.travelYear),
        leadingIcon = R.drawable.ic_calendar,
        keyboardType = KeyboardType.Number
      )
      TextField(
        inputText = travelEntry.travelDuration,
        placeHolder = stringResource(R.string.duration),
        leadingIcon = R.drawable.ic_time
      )
    }
    IconButton(onClick = onRemove) {
      Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
    }
  }
}
