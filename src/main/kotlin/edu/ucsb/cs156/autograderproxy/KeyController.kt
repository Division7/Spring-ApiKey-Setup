package edu.ucsb.cs156.autograderproxy

import jakarta.servlet.http.HttpSession
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class KeyController(private val apiKeyRepository: ApiKeyRepository) {

    @GetMapping("/report")
    fun report(session: HttpSession) : String {
       println("runs")
        return String.format(
            "Session ID: %s, Key: %s",
            session.id,
            session.getAttribute("key"),
        )
    }

    @PostMapping("/invalidate")
    @PreAuthorize("hasRole('ROLE_API_KEY')")
    fun invalidate() : String {
        return "OK"
    }

    @GetMapping("/generate")
    @PreAuthorize("hasRole('OIDC_USER')")
    fun generateKey() : String {
        println("runs")
        val newKey = generateApiKey()
        apiKeyRepository.save(StoredApiKey(newKey.hash))
        return newKey.key
    }
}