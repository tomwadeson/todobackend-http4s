package com.tomwadeson.todobackend.persistence

import com.tomwadeson.todobackend.domain.{TodoItem, TodoItemForm, TodoItemPartialForm}

trait TodoItemRepository {
  def getAll: Seq[TodoItem]
  def getById(id: Long): Option[TodoItem]
  def create(todoItemForm: TodoItemForm): TodoItem
  def deleteAll: Unit
  def delete(id: Long): Unit
  def update(id: Long, todoItemForm: TodoItemPartialForm): Option[TodoItem]
}
