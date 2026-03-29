package com.gblrod.taskvault.model

import java.util.UUID

data class Task(
    val title: String,
    var isCompleted: Boolean = false,
    val id: String = UUID.randomUUID().toString(),
)
