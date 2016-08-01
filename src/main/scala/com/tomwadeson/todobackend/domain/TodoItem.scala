package com.tomwadeson.todobackend.domain

import com.tomwadeson.todobackend.TodoBackendConfig

case class TodoItem(id: Long, title: String, url: String, completed: Boolean, order: Option[Int]) {

  def update(todoItemForm: TodoItemPatchForm): TodoItem =
    this.copy(title = todoItemForm.title.getOrElse(title),
              completed = todoItemForm.completed.getOrElse(completed),
              order = todoItemForm.order.orElse(order))
}

object TodoItem {
  def apply(id: Long, todoItemForm: TodoItemPostForm): TodoItem = {
    val url = s"${TodoBackendConfig.BaseUrl}/$id"
    TodoItem(id, todoItemForm.title, url, false, todoItemForm.order)
  }
}

case class TodoItemPostForm(title: String, order: Option[Int] = None)

case class TodoItemPatchForm(title: Option[String], completed: Option[Boolean], order: Option[Int])
