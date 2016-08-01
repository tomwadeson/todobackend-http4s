package com.tomwadeson.todobackend.domain

import com.tomwadeson.todobackend.TodoBackendConfig

case class TodoItem(id: Long, title: String, url: String, completed: Boolean = false)

object TodoItem {
  def apply(id: Long, todoItemForm: TodoItemForm): TodoItem = {
    val url = s"${TodoBackendConfig.BaseUrl}/$id"
    TodoItem(id, todoItemForm.title, url)
  }
}

case class TodoItemForm(title: String)
