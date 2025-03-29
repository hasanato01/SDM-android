package gov.syria.sdm.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gov.syria.sdm.R
import gov.syria.sdm.data.Course
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.application.Screen
import gov.syria.sdm.ui.components.TextField

@Composable
fun CoursesAndSkillsForm(navController: NavController, viewModel: ApplicationViewModel) {
  val courseType = remember { mutableStateOf("") }
  val courseLevel = remember { mutableStateOf("") }
  val courseDuration = remember { mutableStateOf("") }
  val courseDate = remember { mutableStateOf("") }
  val organizing = remember { mutableStateOf("") }
  val haveDocument = remember { mutableStateOf(false) }
  val context = LocalContext.current

  val courses by remember { viewModel.courses }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Input Fields
    item {
      TextField(courseType, stringResource(R.string.courseType), R.drawable.ic_book)
    }
    item {
      TextField(courseLevel, stringResource(R.string.courseLevel), R.drawable.ic_apartment)
    }
    item {
      TextField(courseDuration, stringResource(R.string.courseDuration), R.drawable.ic_time)
    }
    item {
      TextField(
        courseDate,
        stringResource(R.string.date),
        R.drawable.ic_calendar,
        keyboardType = KeyboardType.Number
      )
    }
    item {
      Text(stringResource(R.string.haveDocument))
      Switch(
        checked = haveDocument.value,
        onCheckedChange = { haveDocument.value = it },
        thumbContent = if (haveDocument.value) {
          {
            Icon(
              imageVector = Icons.Filled.Check,
              contentDescription = null,
              modifier = Modifier.size(SwitchDefaults.IconSize),
            )
          }
        } else {
          null
        }
      )
    }
    item {
      TextField(organizing, stringResource(R.string.organizingEntity), R.drawable.ic_city)
    }

    // Button to add a new course
    item {
      Button(
        onClick = {
          if (courseType.value.isNotEmpty()) {
            viewModel.courses.value = courses + Course(
              type = courseType.value,
              level = courseLevel.value,
              duration = courseDuration.value,
              date = courseDate.value,
              organizing = organizing.value,
              haveDocument = haveDocument.value
            )
            // Clear input fields after adding
            courseType.value = ""
            courseLevel.value = ""
            courseDuration.value = ""
            courseDate.value = ""
            organizing.value = ""
            haveDocument.value = false
          }
        },
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(stringResource(R.string.addCourse))
      }
    }

    // Spacer
    item {
      Spacer(modifier = Modifier.height(16.dp))
    }

    // Display entered courses in a list
    items(courses) { course ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
      ) {
        Column(Modifier.padding(8.dp)) {
          Text("${stringResource(R.string.courseType)}: ${course.type}")
          Text("${stringResource(R.string.courseLevel)}: ${course.level}")
          Text("${stringResource(R.string.courseDuration)}: ${course.duration}")
          Text("${stringResource(R.string.date)}: ${course.date}")
          Text("${stringResource(R.string.organizingEntity)}: ${course.organizing}")
          Text("${stringResource(R.string.haveDocument)}: ${if (course.haveDocument) "✅" else "❌"}")

          // Remove button
          Button(
            onClick = {
              viewModel.courses.value = courses.filterNot { it == course } // Properly remove item
            },
            modifier = Modifier.align(Alignment.End)
          ) {
            Text(stringResource(R.string.remove))
          }
        }
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
            viewModel.updateCoursesInfo(context)
            navController.navigate(Screen.MilitaryService.route)
          }
        ) {
          Text(stringResource(R.string.saveContinue))
        }

        Button(
          onClick = {
            // Save and exit
            viewModel.updateCoursesInfo(context)
          }
        ) {
          Text(stringResource(R.string.saveExit))
        }
      }
    }
  }
}
