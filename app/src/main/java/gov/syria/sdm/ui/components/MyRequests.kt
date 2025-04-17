package gov.syria.sdm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gov.syria.sdm.data.Application
import gov.syria.sdm.data.viewModel.ApplicationViewModel

@Composable
fun MyRequests(navController: NavController, viewModel: ApplicationViewModel) {
  val context = LocalContext.current
  val requests = viewModel.requests.value

  LaunchedEffect(Unit) {
    viewModel.getMyRequests(context)
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    // Table Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.Gray)
        .padding(8.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text("#", fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.2f))
      Text("تاريخ الطلب", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
      Text("القسم", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
      Text("حالة الطلب", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
    }

    // Table Body
    LazyColumn {
      itemsIndexed(requests) { index, item ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(if (index % 2 == 0) Color.LightGray else Color.White),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("${index + 1}", modifier = Modifier.weight(0.2f))
          Text(item.createdAt.take(10), modifier = Modifier.weight(1f))
          Text(item.department, modifier = Modifier.weight(1f))
          Text(item.status, modifier = Modifier.weight(1f))
        }
      }
    }
  }
}
