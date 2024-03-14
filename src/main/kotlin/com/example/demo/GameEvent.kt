package com.example.demo

import java.time.Instant

data class GameEvent(
    val userIDRef: String, // Reference to the playerID
    val eventID: String?,
    val timestamp: Instant?,
    val event: String?, // Buy or profit
    val sum: Int
)