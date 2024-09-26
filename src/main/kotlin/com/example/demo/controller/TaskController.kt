package com.example.demo.controller

import com.example.demo.dto.CreateTaskDto
import com.example.demo.dto.TaskDto
import com.example.demo.service.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.MethodArgumentNotValidException

@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping("/create")
    fun createTask(@RequestBody createTaskDto: CreateTaskDto): ResponseEntity<String> {
        return try {
            taskService.saveTask(createTaskDto)
            ResponseEntity.ok("Task created successfully")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/create/multiple")
    fun createMultipleTasks(@RequestBody tasks: List<CreateTaskDto>): ResponseEntity<String> {
        return try {
            taskService.saveMultipleTasks(tasks)
            ResponseEntity.ok("Tasks created successfully")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/upload")
    fun uploadTasksFromFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        return try {
            taskService.uploadTasksFromFile(file)
            ResponseEntity.ok("Tasks uploaded successfully")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("/{id}/description")
    fun updateTaskDescription(@PathVariable id: Long, @RequestBody description: String): ResponseEntity<String> {
        return try {
            taskService.updateTaskDescription(id, description)
            ResponseEntity.ok("Task description updated successfully")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        } catch (e: RuntimeException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("/{id}/status")
    fun updateTaskStatus(@PathVariable id: Long, @RequestBody isDone: Boolean): ResponseEntity<String> {
        return try {
            taskService.updateTaskStatus(id, isDone)
            ResponseEntity.ok("Task status updated successfully")
        } catch (e: RuntimeException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<String> {
        return try {
            taskService.deleteTask(id)
            ResponseEntity.ok("Task deleted successfully")
        } catch (e: RuntimeException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
