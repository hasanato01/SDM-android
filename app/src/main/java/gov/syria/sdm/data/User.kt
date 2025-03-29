package gov.syria.sdm.data

data class User(
  val id: String,
  val firstName: String,
  val lastName: String,
  val email: String,
  val password: String,
  val role: String = "user",
  val adminCity: String?,
  val userInfo: Personal,
  val addressInfo: Address,
  val idCardResidence: String?,
  val socialInfo: Social,
  val chronicDisease: String?,
  val educationalInfo: Educational,
  val coursesAndSkills: List<Course>,
  val travelInformation: List<TravelEntry>,
  val workingWithTheGovernment: List<GovernmentWork>,
  val militaryService: MilitaryService?,
  val contactInformation: Contact,
  val cv: String?,
  val passwordChangedAt: String?,
  val passwordResetCode: String?,
  val passwordResetExpires: String?,
  val passwordResetVerified: Boolean?
)
