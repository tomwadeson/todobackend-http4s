package com.tomwadeson.todobackend.service

import com.tomwadeson.todobackend.domain.{TodoItemPatchForm, TodoItemPostForm}
import com.tomwadeson.todobackend.persistence.TodoItemRepository
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}
import org.http4s.HttpService
import org.http4s.dsl._

class TodoService(todoItemRepository: TodoItemRepository) {

  // Register circe as the JSON decoder/encoder
  implicit def circeJsonDecoder[A](implicit decoder: Decoder[A]) = org.http4s.circe.jsonOf[A]
  implicit def circeJsonEncoder[A](implicit encoder: Encoder[A]) = org.http4s.circe.jsonEncoderOf[A]

  val service: HttpService = HttpService {
    case GET -> Root / "todos" =>
      Ok(todoItemRepository.getAll)

    case GET -> Root / "todos" / LongVar(id) =>
      todoItemRepository.getById(id).fold(NotFound())(Ok(_))

    case req @ POST -> Root / "todos" =>
      req.decode[TodoItemPostForm] { todoItemForm =>
        Created(todoItemRepository.create(todoItemForm))
      }

    case DELETE -> Root / "todos" / LongVar(id) => {
      val todoItem = todoItemRepository.getById(id)
      todoItem.foreach(item => todoItemRepository.delete(item.id))
      todoItem.fold(NotFound())(Ok(_))
    }

    case DELETE -> Root / "todos" => {
      todoItemRepository.deleteAll
      Ok()
    }

    case req @ PATCH -> Root / "todos" / LongVar(id) =>
      req.decode[TodoItemPatchForm] { todoItemForm =>
        todoItemRepository.update(id, todoItemForm).fold(NotFound())(Ok(_))
      }
  }
}
