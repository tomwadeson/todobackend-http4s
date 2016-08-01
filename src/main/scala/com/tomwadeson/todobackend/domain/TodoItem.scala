package com.tomwadeson.todobackend.domain

import com.tomwadeson.todobackend.TodoBackendConfig

case class TodoItem(id: Long, title: String, url: String, completed: Boolean = false) {
  def update(todoItemForm: TodoItemPartialForm): TodoItem =
    this.copy(title = todoItemForm.title.getOrElse(title),
              completed = todoItemForm.completed.getOrElse(completed))
}

object TodoItem {
  def apply(id: Long, todoItemForm: TodoItemForm): TodoItem = {
    val url = s"${TodoBackendConfig.BaseUrl}/$id"
    TodoItem(id, todoItemForm.title, url)
  }
}

case class TodoItemForm(title: String)

case class TodoItemPartialForm(title: Option[String], completed: Option[Boolean])
