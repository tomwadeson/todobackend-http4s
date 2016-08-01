package com.tomwadeson.todobackend

object TodoBackendConfig {
  val Port: Int = sys.env.getOrElse("PORT", "8080").toInt
  val BaseUrl = s"http://86.169.225.42:$Port/todos"
}
