package com.example.demo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant
import java.util.*

@SpringBootTest
class PlayerServiceTest {
	@Autowired
	lateinit var playerService: PlayerService

	// Test if player successfully added
	@Test
	fun testAddPlayer_Successful() {
		val userID = UUID.randomUUID().toString()
		val player = Player(userID, name = "John", balance = 100)
		// Add the player
		val id = playerService.addPlayer(player)
		// Verify that the userID is not null
		assertNotNull(id)
	}

	// Test if player gives negative balance
	@Test
	fun testAddPlayer_NegativeBalance() {
		val userID = UUID.randomUUID().toString()
		// Attempt to add a player with a negative balance
		val player = Player(userID, name = "John", balance = -50)

		// Verify that adding the player throws an IllegalArgumentException with the expected error message
		val exception = assertThrows(IllegalArgumentException::class.java) {
			playerService.addPlayer(player)
		}
		assertEquals("Invalid balance: -50", exception.message)
	}

	// Test if game successfully ordered
	@Test
	fun testAddGameEvent_SuccessfulBuyEvent() {
		val userID = UUID.randomUUID().toString()
		val eventID = UUID.randomUUID().toString()
		val timestamp = Instant.now()
		val player = Player(userID, name = "rick", balance = 500)
		playerService.addPlayer(player)
		val gameEvent = GameEvent(userID, eventID, timestamp, event = "buy", sum = 50)
		val remainingBalance = playerService.addGameEvent(eventType = "buy", gameEvent)
		assertEquals(450, remainingBalance)
	}

	// Test if winnings are added to total balance
	@Test
	fun testAddGameEvent_SuccessfulWinEvent() {
		val userID = UUID.randomUUID().toString()
		val player = Player(userID, name = "John", balance = 100)
		playerService.addPlayer(player)
		val eventID = UUID.randomUUID().toString()
		val timestamp = Instant.now()
		val gameEvent = GameEvent(userID, eventID, timestamp, event = "win", 50)
		val remainingBalance = playerService.addGameEvent(eventType = "win", gameEvent)
		assertEquals(150, remainingBalance)
	}

	// Test if balance is not enough to buy game
	@Test
	fun testAddGameEvent_InsufficientBalance() {
		val userID = UUID.randomUUID().toString()
		val eventID = UUID.randomUUID().toString()
		val timestamp = Instant.now()
		val player = Player(userID, name = "rick", balance = 500)
		playerService.addPlayer(player)
		val gameEvent = GameEvent(userID, eventID, timestamp, event = "buy", sum = 600)
		val exception = assertThrows(IllegalArgumentException::class.java) {
			playerService.addGameEvent("buy", gameEvent)
		}
		assertEquals("Insufficient balance for buying", exception.message)
	}

	// Test if event type is something else than "buy" or "win"
	@Test
	fun testAddGameEvent_InvalidEventType() {
		val userID = UUID.randomUUID().toString()
		val eventID = UUID.randomUUID().toString()
		val timestamp = Instant.now()
		val player = Player(userID, name = "rick", balance = 500)
		playerService.addPlayer(player)
		val gameEvent = GameEvent(userID, eventID, timestamp, event = "test", sum = 400)
		val exception = assertThrows(IllegalArgumentException::class.java) {
			playerService.addGameEvent("invalid", gameEvent)
		}
		assertEquals("Invalid event type: invalid", exception.message)
	}
}

