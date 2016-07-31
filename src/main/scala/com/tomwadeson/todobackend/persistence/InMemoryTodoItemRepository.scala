package com.tomwadeson.todobackend.persistence

import java.util.concurrent.atomic.AtomicLong

import com.tomwadeson.todobackend.domain.{TodoItem, TodoItemForm}

import scala.collection.concurrent.TrieMap

class InMemoryTodoItemRepository extends TodoItemRepository {

  private val repository = TrieMap[Long, TodoItem]()
  private val idSequence = new AtomicLong()

  override def getAll: Seq[TodoItem] =
    repository.values.toSeq

  override def getById(id: Long): Option[TodoItem] =
    repository.get(id)

  override def create(todoItemForm: TodoItemForm): TodoItem = {
    val id       = idSequence.getAndIncrement
    val todoItem = TodoItem(id, todoItemForm.title)
    repository.put(id, todoItem)
    todoItem
  }

  override def delete(id: Long): Unit =
    repository.remove(id)

  override def deleteAll: Unit =
    synchronized {
      idSequence.set(0)
      repository.clear()
    }
}
