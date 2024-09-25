

package com.example.demo.dto

import com.example.demo.entity.Task

data class TaskDto(
    val id: Long?,
    val description: String,
    val isDone: Boolean
)

fun Task.toDto(): TaskDto {
    return TaskDto(
        id = this.id,
        description = this.description,
        isDone = this.isDone
    )
}
