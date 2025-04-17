package gov.syria.sdm.api

object Endpoints {
  const val BASE_URL = "http://192.168.1.102"

  //Auth
  const val LOGIN_EP = "/api/v1/auth/login"
  const val SIGNUP_EP = "/api/v1/auth/signup"

  //Affiliates
  const val AFFILIATES_EP = "/api/v1/affiliates/"

  //Application
  const val APPLICATION_EP = "/api/v1/application/"
  const val filterApplicationsEP = "/api/v1/application/filterapplications"
  const val USER_APPLICATION_EP = "/api/v1/application/userapplication/"
  const val statusEP = "/api/v1/application/status/"
}
