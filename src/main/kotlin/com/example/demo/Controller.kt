package com.example.demo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
class Controller(private val playerService: PlayerService) {
    @PostMapping("/create")
    fun create(@RequestBody body: Player): ResponseEntity<String> {
        val userID = try {
            playerService.addPlayer(body)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
        return if (userID != null) {
            ResponseEntity.ok("Player created with UserID: $userID, Balance: ${body.balance}, and Name: ${body.name}")
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create player")
        }
    }

    @GetMapping("/")
    fun mainPage(): String {
        return("Welcome to API which provides integration between game motor and game account wallet :)")
    }

    @GetMapping("/all")
    fun getAllPlayers(): ResponseEntity<List<Player>> {
        val allPlayers = playerService.getAllPlayers()
        return ResponseEntity.ok(allPlayers)
    }
    @PostMapping("/{eventType}")
    fun createGameEvent(
        @PathVariable eventType: String,
        @RequestBody body: GameEvent
    ): ResponseEntity<String> {
        val remainingBalance = try {
            playerService.addGameEvent(eventType, body)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
        return ResponseEntity.ok("Remaining balance: $remainingBalance")
    }
}