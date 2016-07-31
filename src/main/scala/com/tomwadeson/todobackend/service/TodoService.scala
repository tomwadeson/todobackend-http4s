package com.tomwadeson.todobackend.service

import com.tomwadeson.todobackend.domain.TodoItemForm
import com.tomwadeson.todobackend.persistence.TodoItemRepository
import io.circe.{Decoder, Encoder}
import org.http4s.HttpService
import org.http4s.dsl._
import io.circe.generic.auto._

class TodoService(todoItemRepository: TodoItemRepository) {

  // Register circe as the JSON decoder/encoder
  implicit def circeJsonDecoder[A](implicit decoder: Decoder[A]) = org.http4s.circe.jsonOf[A]
  implicit def circeJsonEncoder[A](implicit encoder: Encoder[A]) = org.http4s.circe.jsonEncoderOf[A]

  val service: HttpService = HttpService {
    case GET -> Root / "todos" =>
      Ok(todoItemRepository.getAll)

    case req @ POST -> Root / "todos" =>
      req.decode[TodoItemForm] { todoItemForm =>
        Created(todoItemRepository.create(todoItemForm))
      }
  }
}
