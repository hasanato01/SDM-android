package gov.syria.sdm.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextField(
  inputText: MutableState<String>,
  placeHolder: String,
  leadingIcon: Int,
  keyboardType: KeyboardType = KeyboardType.Text,
  visualTransformation: VisualTransformation = VisualTransformation.None
) {
  CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    OutlinedTextField(
      value = inputText.value,
      onValueChange = { newText -> inputText.value = newText },
      shape = RoundedCornerShape(5.dp),
      textStyle = TextStyle(fontSize = 14.sp, textDirection = TextDirection.Content),
      singleLine = true,
      placeholder = {
        Text(
          text = placeHolder,
          fontSize = 14.sp,
          color = Color(0xFFB3B3B3)
        )
      },
      leadingIcon = {
        Icon(
          painter = painterResource(id = leadingIcon),
          contentDescription = null,
          modifier = Modifier.size(20.dp)
        )
      },
      keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
      visualTransformation = visualTransformation,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
    )
  }
}
