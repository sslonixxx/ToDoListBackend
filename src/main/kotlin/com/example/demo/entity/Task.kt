package com.example.demo.entity
import jakarta.persistence.*

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var isDone: Boolean = false
) {
    constructor() : this(null, "", false)
}

