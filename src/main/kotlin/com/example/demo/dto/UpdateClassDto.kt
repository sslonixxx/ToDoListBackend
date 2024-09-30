package com.example.demo.dto

data class UpdateTaskDto(
    val description: String,
    val isDone: Boolean
)

data class UpdateDescriptionDto(
    val description: String
)

data class UpdateStatusDto(
    val isDone: Boolean
)
