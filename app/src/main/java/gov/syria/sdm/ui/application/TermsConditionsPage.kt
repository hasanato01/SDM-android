package gov.syria.sdm.ui.application

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gov.syria.sdm.R

@Composable
fun TermsAndConditionsPage() {
  Column(Modifier.fillMaxSize()) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
      Text(
        text = stringResource(R.string.termsConds),
        fontSize = 17.sp,
        color = Color.Black,
        modifier = Modifier.padding(vertical = 7.dp)
      )
    }
  }
}