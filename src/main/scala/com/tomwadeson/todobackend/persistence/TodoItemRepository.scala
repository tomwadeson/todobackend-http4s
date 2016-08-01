package com.tomwadeson.todobackend.persistence

import com.tomwadeson.todobackend.domain.{TodoItem, TodoItemPatchForm, TodoItemPostForm}

trait TodoItemRepository {
  def getAll: Seq[TodoItem]
  def getById(id: Long): Option[TodoItem]
  def create(todoItemForm: TodoItemPostForm): TodoItem
  def deleteAll: Unit
  def delete(id: Long): Unit
  def update(id: Long, todoItemForm: TodoItemPatchForm): Option[TodoItem]
}
