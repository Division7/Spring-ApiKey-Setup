package edu.ucsb.cs156.autograderproxy

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import kotlin.io.encoding.Base64
import kotlin.jvm.optionals.getOrNull

class ApiKeyFilter(private val apiKeyRepository: ApiKeyRepository) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.authType != null;
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val apiKey = request.getHeader("Authorization")?: run {
            filterChain.doFilter(request, response)
            return
        }

        val decodedKey = try {
                DigestUtils.sha256Hex(Base64.Default.decode(apiKey.replace("Bearer ", "")))
        }catch (e: IllegalArgumentException){
            response.sendError(401, "invalid api key")
            return
        }
        val apiKeyEntity = apiKeyRepository.findById(decodedKey).getOrNull()?: run {
            response.sendError(401, "invalid api key")
            return
        }

        println("API KEY: $apiKeyEntity")

        val token = ApiKeyToken(apiKeyEntity.hash)

        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext().apply { authentication = token })

        filterChain.doFilter(request, response)
    }
}