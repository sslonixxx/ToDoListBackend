package com.example.demo.controller

import com.example.demo.dto.CreateTaskDto
import com.example.demo.dto.TaskDto
import com.example.demo.service.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping("/create")
    fun createTask(@RequestBody createTaskDto: CreateTaskDto): ResponseEntity<String> {
        taskService.saveTask(createTaskDto)
        return ResponseEntity.ok("Task created successfully")
    }

    @PostMapping("/create/multiple")
    fun createMultipleTasks(@RequestBody tasks: List<CreateTaskDto>): ResponseEntity<String> {
        taskService.saveMultipleTasks(tasks)
        return ResponseEntity.ok("Tasks created successfully")
    }

    @PostMapping("/upload")
    fun uploadTasksFromFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        taskService.uploadTasksFromFile(file)
        return ResponseEntity.ok("Tasks uploaded successfully")
    }

    @GetMapping
    fun getAllTasks(): ResponseEntity<List<TaskDto>> {
        val tasks = taskService.getAllTasks()
        return ResponseEntity.ok(tasks)
    }

    @PutMapping("/{id}/description")
    fun updateTaskDescription(@PathVariable id: Long, @RequestBody description: String): ResponseEntity<String> {
        taskService.updateTaskDescription(id, description)
        return ResponseEntity.ok("Task description updated successfully")
    }


    @PutMapping("/{id}/status")
    fun updateTaskStatus(@PathVariable id: Long, @RequestBody isDone: Boolean): ResponseEntity<String> {
        taskService.updateTaskStatus(id, isDone)
        return ResponseEntity.ok("Task status updated successfully")
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<String> {
        taskService.deleteTask(id)
        return ResponseEntity.ok("Task deleted successfully")
    }
}
