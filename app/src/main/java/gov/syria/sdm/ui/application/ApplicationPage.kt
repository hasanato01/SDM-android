package gov.syria.sdm.ui.application

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import gov.syria.sdm.R
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.components.forms.AddressInfoForm
import gov.syria.sdm.ui.components.forms.ArrestForm
import gov.syria.sdm.ui.components.forms.ContactInfoForm
import gov.syria.sdm.ui.components.forms.CoursesAndSkillsForm
import gov.syria.sdm.ui.components.forms.EducationalInfoForm
import gov.syria.sdm.ui.components.forms.GovernmentWorkForm
import gov.syria.sdm.ui.components.forms.MilitaryServiceForm
import gov.syria.sdm.ui.components.forms.PersonalInfoForm
import gov.syria.sdm.ui.components.forms.SocialInfoForm
import gov.syria.sdm.ui.components.forms.TravelInformationForm
import kotlinx.coroutines.launch

//@Composable
//fun RequestSubmissionPage(viewModel: ApplicationViewModel = viewModel()) {
//  val coroutineScope = rememberCoroutineScope()
//  val pagerState = rememberPagerState(pageCount = { 9 })
//
//  Column(
//    Modifier
//      .fillMaxSize()
//      .padding(horizontal = 18.dp, vertical = 10.dp),
//    verticalArrangement = Arrangement.Top
//  ) {
//    Text(
//      text = stringResource(R.string.submitApplication),
//      fontSize = 25.sp,
//      color = Color(0xCC161E54),
//      modifier = Modifier.padding(bottom = 8.dp)
//    )
//
//    // Horizontal Pager to navigate through form sections
//    HorizontalPager(
//      state = pagerState,
//      modifier = Modifier.weight(1f)
//    ) { page ->
//      when (page) {
//        0 -> PersonalInfoForm(viewModel)
//
//        1 -> AddressInfoForm(viewModel)
//
//        2 -> SocialInfoForm(viewModel)
//
//        3 -> GovernmentWorkForm(viewModel)
//
//        4 -> EducationalInfoForm(viewModel)
//
//        5 -> TravelInformationForm(viewModel)
//
//        6 -> CoursesAndSkillsForm(viewModel)
//
//        7 -> MilitaryServiceForm(viewModel)
//
//        8 -> ArrestForm(viewModel)
//
//        9 -> ContactInfoForm(viewModel)
//      }
//    }
//
//    // Navigation Buttons
//    Row(
//      Modifier
//        .fillMaxWidth()
//        .padding(horizontal = 20.dp, vertical = 20.dp),
//      horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//      Button(
//        onClick = {
//          coroutineScope.launch {
//            if (pagerState.currentPage > 0)
//              pagerState.animateScrollToPage(pagerState.currentPage - 1)
//          }
//        },
//        enabled = pagerState.currentPage > 0,
//        shape = RoundedCornerShape(5.dp),
//        colors = ButtonDefaults.buttonColors(Color(0xFFFF5151)),
//        modifier = Modifier
//          .height(35.dp)
//          .fillMaxWidth(0.3f)
//      ) {
//        Text(stringResource(R.string.prev), fontSize = 15.sp, color = Color.White)
//      }
//
//      Button(
//        onClick = {
//          coroutineScope.launch {
//            if (pagerState.currentPage < pagerState.pageCount - 1)
//              pagerState.animateScrollToPage(pagerState.currentPage + 1)
//          }
//        },
//        enabled = pagerState.currentPage < pagerState.pageCount - 1,
//        shape = RoundedCornerShape(5.dp),
//        colors = ButtonDefaults.buttonColors(Color(0xFF0B6C61)),
//        modifier = Modifier
//          .height(35.dp)
//          .fillMaxWidth(0.3f)
//      ) {
//        Text(stringResource(R.string.next), fontSize = 15.sp, color = Color.White)
//      }
//    }
//  }
//}
