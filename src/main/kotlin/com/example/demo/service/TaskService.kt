
package com.example.demo.service

import com.example.demo.dto.TaskDto
import com.example.demo.dto.CreateTaskDto
import com.example.demo.dto.toDomain
import com.example.demo.dto.toDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.example.demo.repository.TaskRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.lang.RuntimeException

@Service
class TaskService(private val taskRepository: TaskRepository) {

    fun saveTask(createTaskDto: CreateTaskDto) {
        val task = createTaskDto.toDomain()
        taskRepository.save(task)
    }
    fun saveMultipleTasks(tasks: List<CreateTaskDto>) {
        val taskEntities = tasks.map { it.toDomain() }
        taskRepository.saveAll(taskEntities)
    }
    fun uploadTasksFromFile(file: MultipartFile) {

        taskRepository.deleteAll()

        val objectMapper = jacksonObjectMapper()
        val tasks: List<CreateTaskDto> = objectMapper.readValue(file.inputStream)

        val taskEntities = tasks.map { it.toDomain() }
        taskRepository.saveAll(taskEntities)
    }

    fun getAllTasks(): List<TaskDto> {
        return taskRepository.findAll().map { task -> task.toDto() }
    }

    fun updateTaskDescription(id: Long, description: String) {
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
