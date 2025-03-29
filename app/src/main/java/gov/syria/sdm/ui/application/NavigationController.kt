package gov.syria.sdm.ui.application

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import gov.syria.sdm.data.viewModel.ApplicationViewModel
import gov.syria.sdm.ui.HomePage
import gov.syria.sdm.ui.auth.AuthForm
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

sealed class Screen(val route: String) {
  data object Auth : Screen("auth")
  data object Home : Screen("home")
  data object PersonalInfo : Screen("personal_info")
  data object AddressInfo : Screen("address_info")
  data object SocialInfo : Screen("social_info")
  data object GovernmentWork : Screen("government_work")
  data object EducationalInfo : Screen("educational_info")
  data object TravelInformation : Screen("travel_information")
  data object CoursesAndSkills : Screen("courses_and_skills")
  data object MilitaryService : Screen("military_service")
  data object ArrestInfo : Screen("arrest_info")
  data object ContactInfo : Screen("contact_info")
}

@Composable
fun NavigationController(navController: NavHostController, viewModel: ApplicationViewModel) {
  NavHost(navController = navController, startDestination = Screen.Home.route) {
    composable(Screen.Home.route) { HomePage(navController, viewModel) }
    composable(Screen.Auth.route) { AuthForm(navController, viewModel) }
    composable(Screen.PersonalInfo.route) { PersonalInfoForm(navController, viewModel) }
    composable(Screen.AddressInfo.route) { AddressInfoForm(navController, viewModel) }
    composable(Screen.SocialInfo.route) { SocialInfoForm(navController, viewModel) }
    composable(Screen.GovernmentWork.route) { GovernmentWorkForm(navController, viewModel) }
    composable(Screen.EducationalInfo.route) { EducationalInfoForm(navController, viewModel) }
    composable(Screen.TravelInformation.route) { TravelInformationForm(navController, viewModel) }
    composable(Screen.CoursesAndSkills.route) { CoursesAndSkillsForm(navController, viewModel) }
    composable(Screen.MilitaryService.route) { MilitaryServiceForm(navController, viewModel) }
    composable(Screen.ArrestInfo.route) { ArrestForm(navController, viewModel) }
    composable(Screen.ContactInfo.route) { ContactInfoForm(navController, viewModel) }
  }
}
