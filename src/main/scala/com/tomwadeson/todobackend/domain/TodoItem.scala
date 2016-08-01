package com.tomwadeson.todobackend.domain

case class TodoItem(id: Long, title: String, completed: Boolean = false)
case class TodoItemForm(title: String)
