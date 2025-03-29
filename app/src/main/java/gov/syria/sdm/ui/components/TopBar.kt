package gov.syria.sdm.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
  onNavigationIconClick: () -> Unit
) {
  Column(
    Modifier
      .fillMaxWidth()
      .padding(bottom = 10.dp)
  ) {
    Row(
      Modifier
        .fillMaxWidth()
        .height(65.dp)
        .padding(horizontal = 10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      /*  IconButton(onClick = { onNavigationIconClick() }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "",
                tint = Color(0xFFB2B2B2)
            )
        }*/
      Icon(
        imageVector = Icons.Default.Menu,
        contentDescription = "",
        tint = Color(0xFFB2B2B2),
        modifier = Modifier.clickable(
          indication = null,
          interactionSource = null
        ) {
          onNavigationIconClick()
        }
      )

      Row(verticalAlignment = Alignment.CenterVertically) {
        BadgedBox(
          badge = {
            Badge(
              modifier = Modifier
                .offset(x = (-15).dp, y = 8.dp)
                .size(11.dp),
              containerColor = Color(0xFFFF5151)
            )
          }
        ) {
          Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications Icon",
            tint = Color(0xFFB2B2B2),
            modifier = Modifier.padding(end = 7.dp)
          )
        }
        Icon(
          imageVector = Icons.Default.AccountCircle,
          contentDescription = "Account Image",
          tint = Color(0xFFB2B2B2),
          modifier = Modifier.size(30.dp)
        )
        Icon(
          imageVector = Icons.Default.ArrowDropDown,
          contentDescription = "Account Image",
          tint = Color(0xFFB2B2B2)
        )
      }
    }
    Divider(
      thickness = 1.dp,
      color = Color(0xFFF1F1F1),
    )
  }
}
