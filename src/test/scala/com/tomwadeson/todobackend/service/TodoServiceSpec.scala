package com.tomwadeson.todobackend.service

import cats.data.Xor
import com.tomwadeson.todobackend.domain.{TodoItem, TodoItemForm}
import com.tomwadeson.todobackend.persistence.InMemoryTodoItemRepository
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import org.http4s._
import org.scalatest.{FlatSpec, Matchers}
import scodec.bits.ByteVector

import scalaz.stream.Process

class TodoServiceSpec extends FlatSpec with Matchers {

  "TodoService" should "fetch all todo items" in new Fixture {
    repository.create(TodoItemForm("First, do this"))
    repository.create(TodoItemForm("Then, do that"))

    val req       = request(Method.GET, "/todos")
    val response  = service.run(req).unsafePerformSync
    val body      = responseBodyAsString(response)
    val todoItems = decode[Seq[TodoItem]](body)

    response.status should be(Status.Ok)

    todoItems match {
      case Xor.Right(todoItems) =>
        todoItems should be(
            Seq(TodoItem(0, TodoItemForm("First, do this")), TodoItem(1, TodoItemForm("Then, do that"))))
      case _ => fail()
    }
  }

  it should "create new todo items" in new Fixture {
    val req      = request(Method.POST, "/todos", entityBodyFromString(TodoItemForm("Hello, World!").asJson.noSpaces))
    val response = service.run(req).unsafePerformSync

    response.status should be(Status.Created)
    repository.getAll should be(Seq(TodoItem(0, TodoItemForm("Hello, World!"))))
  }

  it should "fetch a single todo item by its ID" in new Fixture {
    repository.create(TodoItemForm("Hello"))

    val req      = request(Method.GET, "/todos/0")
    val response = service.run(req).unsafePerformSync

    response.status should be(Status.Ok)
    repository.getAll should be(Seq(TodoItem(0, TodoItemForm("Hello"))))
  }

  it should "delete todo items by ID" in new Fixture {
    repository.create(TodoItemForm("Hello"))
    repository.create(TodoItemForm("World"))

    val req      = request(Method.DELETE, "/todos/0")
    val response = service.run(req).unsafePerformSync

    response.status should be(Status.Ok)
    repository.getAll should be(Seq(TodoItem(1, TodoItemForm("World"))))
  }

  it should "delete all todo items" in new Fixture {
    repository.create(TodoItemForm("Hello"))
    repository.create(TodoItemForm("Hello"))

    val req      = request(Method.DELETE, "/todos")
    val response = service.run(req).unsafePerformSync

    response.status should be(Status.Ok)
    repository.getAll should be(empty)
  }

  trait Fixture {
    val repository = new InMemoryTodoItemRepository
    val service    = new TodoService(repository).service
  }

  private def request(method: Method, uri: String, body: EntityBody = EmptyBody): Request =
    Request(method = method, uri = Uri.fromString(uri).toOption.get, body = body)

  private def responseBodyAsString(response: Response): String =
    response.bodyAsText.runLog.unsafePerformSync.mkString

  private def entityBodyFromString(str: String): EntityBody = {
    implicit val defaultEncoding = java.nio.charset.Charset.defaultCharset()
    val bv                       = ByteVector.encodeString(str).right.toOption.get
    Process.emit(bv)
  }
}
