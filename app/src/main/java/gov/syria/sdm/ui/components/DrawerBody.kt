package gov.syria.sdm.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gov.syria.sdm.data.MenuItem

@Composable
fun DrawerBody(
  items1: List<MenuItem>,
  items2: List<MenuItem>,
  currentRoute: String?,
  onItemClick: (MenuItem) -> Unit
) {
  LazyColumn(
    Modifier
      .fillMaxHeight()
      .fillMaxWidth(0.87f),
    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 15.dp)
  ) {
    item {
      Text(
        text = "الصفحات",
        fontSize = 16.sp,
        color = Color(0xFF686868),
        modifier = Modifier.padding(bottom = 15.dp)
      )
    }
    items(items1) { item ->
      Row(
        Modifier
          .padding(vertical = 15.dp)
          .clickable(
            indication = null,
            interactionSource = null
          ) {
            onItemClick(item)
          }
      ) {
        val whenSelectedColor by remember { mutableStateOf(0xFFFF5151) }
        if (currentRoute == item.id) {
          item.color = whenSelectedColor
        }
        Icon(
          painter = painterResource(id = item.icon),
          contentDescription = "",
          tint = Color(item.color),
          modifier = Modifier.size(20.dp)
        )
        Text(
          item.title,
          fontSize = 16.sp,
          color = Color(item.color),
          modifier = Modifier
            .padding(start = 15.dp)
            .fillMaxWidth()
        )
      }
    }
    item {
      Text(
        text = "اخرى",
        fontSize = 16.sp,
        color = Color(0xFF686868),
        modifier = Modifier.padding(top = 30.dp, bottom = 15.dp)
      )
    }
    items(items2) { item ->
      Row(
        Modifier
          .padding(vertical = 15.dp)
          .clickable {
            onItemClick(item)
          }
      ) {
        var defaultColor by remember { mutableLongStateOf(0xFF686868) }
        var whenSelectedColor by remember { mutableLongStateOf(0xFFFF5151) }

        /*if(item.id == currentRoute){
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = "",
                tint = Color(defaultColor),
                modifier = Modifier.size(20.dp)
            )

            Text(
                item.title,
                fontSize = 16.sp,
                color = Color(defaultColor),
                modifier = Modifier.padding(start = 15.dp)
            )
        }else{
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = "",
                tint = Color(whenSelectedColor),
                modifier = Modifier.size(20.dp)
            )

            Text(
                item.title,
                fontSize = 16.sp,
                color = Color(whenSelectedColor),
                modifier = Modifier.padding(start = 15.dp)
            )}*/

        Icon(
          painter = painterResource(id = item.icon),
          contentDescription = "",
          tint = Color(0xFF686868),
          modifier = Modifier.size(20.dp)
        )
        Text(
          item.title,
          fontSize = 16.sp,
          color = Color(0xFF686868),
          modifier = Modifier
            .padding(start = 15.dp)
            .fillMaxWidth()
        )
      }
    }
  }
}