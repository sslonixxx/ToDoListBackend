package com.example.demo.dto

import com.example.demo.entity.Task

data class CreateTaskDto(
    val description: String,
    val isDone: Boolean = false
)


fun CreateTaskDto.toDomain(): Task {
    return Task(
        description = this.description,
        isDone = this.isDone
    )
}

