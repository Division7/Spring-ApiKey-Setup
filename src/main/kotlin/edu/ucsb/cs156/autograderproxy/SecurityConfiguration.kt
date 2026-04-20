package edu.ucsb.cs156.autograderproxy

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.util.WebUtils


@EnableWebSecurity
@Configuration
class SecurityConfiguration{
    @Bean
    fun configureSecurity(builder: HttpSecurity, apiKeyRepository: ApiKeyRepository): SecurityFilterChain {
        builder.authorizeHttpRequests { it.anyRequest().permitAll()}
            .oauth2Login(Customizer.withDefaults())
            .formLogin { it.disable() }
            .addFilterBefore(ApiKeyFilter(apiKeyRepository), CsrfFilter::class.java)
            .httpBasic { httpBasic -> httpBasic.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) }
            .csrf {
                it.ignoringRequestMatchers(
                    {
                        SecurityContextHolder.getContext().authentication?.authorities?.contains(
                            SimpleGrantedAuthority("ROLE_API_KEY"))?: false
                    }
                )
            }
            .anonymous { it.disable() }
        return builder.build()
    }
}