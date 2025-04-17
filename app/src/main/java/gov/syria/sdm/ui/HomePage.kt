package gov.syria.sdm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gov.syria.sdm.R
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, viewModel: ApplicationViewModel) {
  val drawerState = rememberDrawerState(DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        Column(
          modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
        ) {
          Text(
            text = stringResource(R.string.menu),
            modifier = Modifier.padding(vertical = 16.dp)
          )
          HorizontalDivider()

          // Menu Items
          Text(
            text = stringResource(R.string.signIn),
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                scope.launch { drawerState.close() }
                navController.navigate("auth")
              }
              .padding(vertical = 12.dp)
          )

          Text(
            text = stringResource(R.string.updateProfile),
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                scope.launch { drawerState.close() }
                navController.navigate("personal_info")
              }
              .padding(vertical = 12.dp)
          )

          Text(
            text = stringResource(R.string.myRequests),
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                scope.launch { drawerState.close() }
                navController.navigate("my_requests")
              }
              .padding(vertical = 12.dp)
          )
        }
      }
    }
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text("Home") },
          navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
              Icon(Icons.Default.Menu, contentDescription = "Open menu")
            }
          }
        )
      }
    ) { innerPadding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
          .verticalScroll(rememberScrollState())
          .padding(16.dp)
      ) {
        // Placeholder Card
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
          Column(
            modifier = Modifier.padding(16.dp)
          ) {
            Text(
              text = "Welcome to the App",
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "This is a placeholder card with some sample content.",
            )
          }
        }
      }
    }
  }
}
