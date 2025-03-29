package gov.syria.sdm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gov.syria.sdm.R

@Composable
fun FieldExaminationCard() {
  CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    Card(
      Modifier
        .fillMaxWidth()
        .height(280.dp)
        .padding(horizontal = 15.dp)
        .padding(top = 7.dp),
      shape = RoundedCornerShape(10.dp),
      colors = CardDefaults.cardColors(Color(0xF2161E54))
    ) {
      Column(Modifier.fillMaxSize()) {
        Card(
          Modifier
            .fillMaxHeight(0.2f)
            .fillMaxWidth(),
          shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
          colors = CardDefaults.cardColors(Color(0xFF1B204A))
        ) {
          Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
          ) {
            Text(
              text = stringResource(R.string.fieldExaminationNo),
              fontSize = 20.sp,
              color = Color.White,
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp),
            )
          }

        }
        Column(
          Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(top = 20.dp)
        ) {
          Text(
            text = stringResource(R.string.dateTime),
            fontSize = 10.sp,
            color = Color.White,
            fontWeight = FontWeight(500),
          )

          Text(
            text = stringResource(R.string.fieldExaminationDesc),
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 20.dp),
            style = TextStyle(lineHeight = 20.sp)
          )
        }

        Column(
          Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = stringResource(R.string.applicantCount),
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp, top = 10.dp),
          )

          Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFF5151)),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
              .height(35.dp)
              .fillMaxWidth(35 / 100f)


          ) {
            Text(
              text = stringResource(R.string.showDetails),
              fontSize = 15.sp,
              color = Color(0xFFFFFFFF)
            )
          }
        }


      }
    }
  }
}
