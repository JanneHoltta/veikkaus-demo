package com.example.demo

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class PlayerService {

    // List of players
    private val players = mutableListOf<Player>()
    // List of events
    private val gameEvents = mutableListOf<GameEvent>()

    // Additional function for getting all the current players
    fun getAllPlayers(): List<Player> {
        return players.toList()
    }

    fun addPlayer(player: Player): String? {
        if (player.balance < 0) {
            throw IllegalArgumentException("Invalid balance: ${player.balance}")
        }
        val userID = player.userID ?: UUID.randomUUID().toString()
        val playerWithID = player.copy(userID = userID)
        return try {
            players.add(playerWithID)
            userID
        } catch (e: Exception) {
            null
        }
    }

    fun addGameEvent( eventType: String, gameEvent: GameEvent): Int? {
        val eventID = UUID.randomUUID().toString()
        val timestamp = Instant.now()

        val player = players.find { it.userID == gameEvent.userIDRef }
            ?: throw IllegalArgumentException("Player with userID ${gameEvent.userIDRef} not found")

        when (eventType) {
            "buy" -> {
                if (player.balance < gameEvent.sum) {
                    throw IllegalArgumentException("Insufficient balance for buying")
                }
                player.balance -= gameEvent.sum
            }
            "win" -> player.balance += gameEvent.sum
            else -> throw IllegalArgumentException("Invalid event type: $eventType")
        }
        val eventObject = gameEvent.copy(eventID = eventID, timestamp = timestamp, event = eventType)
        gameEvents.add(eventObject)
        return player.balance
    }
}