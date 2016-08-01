package com.tomwadeson.todobackend

object TodoBackendConfig {
  val Port: Int = sys.env.getOrElse("PORT", "8080").toInt
  val BaseUrl = s"https://todobackend-http4s.herokuapp.com/todos"
}
