package gov.syria.sdm.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gov.syria.sdm.R

@Composable
fun RequestCard(
  cardColor: Long,
  state: String,
  section: String,
  submissionDate: String,
  lastUpdate: String
) {
  Card(
    Modifier
      .fillMaxWidth()
      .height(150.dp)
      .padding(horizontal = 15.dp, vertical = 7.dp),
    colors = CardDefaults.cardColors(Color(cardColor))
  ) {
    Column(
      Modifier
        .fillMaxSize()
        .padding(10.dp)
    ) {
      Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_bubble),
          contentDescription = "bubble icon",
          tint = Color(0xFF1C1B1F),
          modifier = Modifier.size(20.dp)
        )

        Text(
          text = stringResource(R.string.myApplication),
          color = Color(0xCC161E54),
          fontSize = 25.sp,
          fontWeight = FontWeight(700)

        )
      }
      Row(
        Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Column(
          Modifier.fillMaxHeight(),
          horizontalAlignment = Alignment.End,
          verticalArrangement = Arrangement.SpaceEvenly
        ) {
          Text(
            text = buildAnnotatedString {
              pushStyle(
                SpanStyle(
                  fontWeight = FontWeight(900),
                  color = Color(0xCC161E54),
                  fontSize = 16.sp
                )
              )
              append(stringResource(R.string.applicationDate))
              pushStyle(SpanStyle(fontWeight = FontWeight.Normal))
              append(submissionDate)
              pop()
            }
          )

          Text(
            text = buildAnnotatedString {
              pushStyle(
                SpanStyle(
                  fontWeight = FontWeight(900),
                  color = Color(0xCC161E54),
                  fontSize = 16.sp
                )
              )
              append(stringResource(R.string.lastUpdate) + ": ")
              pushStyle(SpanStyle(fontWeight = FontWeight.Normal))
              append(lastUpdate)
              pop()
            }
          )
        }
        Column(
          Modifier.fillMaxHeight(),
          horizontalAlignment = Alignment.End,
          verticalArrangement = Arrangement.SpaceEvenly
        ) {
          Text(
            text = buildAnnotatedString {
              pushStyle(
                SpanStyle(
                  fontWeight = FontWeight(900),
                  color = Color(0xCC161E54),
                  fontSize = 16.sp
                )
              )
              append(stringResource(R.string.status))
              pushStyle(SpanStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp))
              append(state)
              pop()
            }
          )
          Text(
            text = buildAnnotatedString {
              pushStyle(
                SpanStyle(
                  fontWeight = FontWeight(900),
                  color = Color(0xCC161E54),
                  fontSize = 16.sp
                )
              )
              append(stringResource(R.string.department))
              pushStyle(SpanStyle(fontWeight = FontWeight.Normal))
              append(section)
              pop()
            }
          )
        }
      }
    }
  }
}