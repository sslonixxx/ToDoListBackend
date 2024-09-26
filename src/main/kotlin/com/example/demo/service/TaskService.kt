package com.example.demo.service

import com.example.demo.dto.*
import com.example.demo.repository.TaskRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.lang.RuntimeException

@Service
class TaskService(private val taskRepository: TaskRepository) {

    fun saveTask(createTaskDto: CreateTaskDto) {
        val task = createTaskDto.toEntity()
        taskRepository.save(task)
    }

    fun saveMultipleTasks(tasks: List<CreateTaskDto>) {
        if (tasks.isEmpty()) {
            throw IllegalArgumentException("Task list cannot be empty")
        }
        val taskEntities = tasks.map { it.toEntity() }
        taskRepository.saveAll(taskEntities)
    }

    fun uploadTasksFromFile(file: MultipartFile) {

        val objectMapper = jacksonObjectMapper()
        val tasks: List<CreateTaskDto> = try {
            objectMapper.readValue(file.inputStream)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to parse tasks from file")
        }

        if (tasks.isEmpty()) {
            throw IllegalArgumentException("File contains no tasks")
        }

        taskRepository.deleteAll()
        val taskEntities = tasks.map { it.toEntity() }
        taskRepository.saveAll(taskEntities)
    }

    fun getAllTasks(): List<TaskDto> {
        return taskRepository.findAll().map { task -> task.toDto() }
    }

    fun updateTaskDescription(id: Long, description: String) {
        if (description.isBlank()) {
            throw IllegalArgumentException("Description cannot be empty")
        }

        val task = taskRepository.findById(id).orElseThrow { RuntimeException("Task not found") }
        task.description = description
        taskRepository.save(task)
    }

    fun updateTaskStatus(id: Long, isDone: Boolean) {
        val task = taskRepository.findById(id).orElseThrow { RuntimeException("Task not found") }
        task.isDone = isDone
        taskRepository.save(task)
    }

    fun deleteTask(id: Long) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id)
        } else {
            throw RuntimeException("Task not found")
        }
    }
}
