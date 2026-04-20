package edu.ucsb.cs156.autograderproxy

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

class ApiKeyToken(private val hash: HashedApiKey) : AbstractAuthenticationToken(
    listOf(SimpleGrantedAuthority("ROLE_API_KEY"))
) {
    init{
        super.setAuthenticated(true)
    }

    override fun getCredentials(): HashedApiKey {
       return hash
    }

    override fun getPrincipal(): HashedApiKey {
        return hash
    }
}