package com.gblrod.taskvault.dto

data class TaskDto(
    val title: String,
    var isCompleted: Boolean = false
)
