package com.tomwadeson.todobackend.persistence

import com.tomwadeson.todobackend.domain.{TodoItem, TodoItemForm}

trait TodoItemRepository {
  def getAll: Seq[TodoItem]
  def create(todoItemForm: TodoItemForm): TodoItem
}
