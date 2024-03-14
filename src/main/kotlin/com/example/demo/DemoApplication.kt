package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class DemoApplication()

fun main(args: Array<String>) {
	// Create a Spring application context
	val context = runApplication<DemoApplication>(*args)

	// Retrieve the PlayerService bean from the context
	val playerService = context.getBean(PlayerService::class.java)

	// Generate UUIDs for player IDs
	val userID1 = UUID.randomUUID().toString()
	val userID2 = UUID.randomUUID().toString()
	val userID3 = UUID.randomUUID().toString()

	// Create player objects
	val player1 = Player(userID1, name = "Player 1", balance = 100)
	val player2 = Player(userID2, name = "Player 2", balance = 200)
	val player3 = Player(userID3, name = "Player 3", balance = 300)

	// Add players to the player service
	playerService.addPlayer(player1)
	playerService.addPlayer(player2)
	playerService.addPlayer(player3)
}
