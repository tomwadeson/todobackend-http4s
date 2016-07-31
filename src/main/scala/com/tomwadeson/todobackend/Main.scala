package com.tomwadeson.todobackend

import com.tomwadeson.todobackend.persistence.InMemoryTodoItemRepository
import com.tomwadeson.todobackend.service.TodoService
import org.http4s.HttpService
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.middleware.CORS
import org.http4s.server.{Server, ServerApp}

import scalaz.concurrent.Task

object Main extends ServerApp {

  val service: HttpService = CORS(new TodoService(new InMemoryTodoItemRepository).service)

  override def server(args: List[String]): Task[Server] =
    BlazeBuilder
      .bindHttp(8080, "0.0.0.0")
      .mountService(service, "/")
      .start
}
