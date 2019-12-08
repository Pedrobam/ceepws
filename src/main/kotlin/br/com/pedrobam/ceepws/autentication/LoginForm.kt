package br.com.pedrobam.ceepws.autentication

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

data class LoginForm(
    val email: String,
    val senha: String
) {
    fun converter(): Authentication {
        return UsernamePasswordAuthenticationToken(email, senha)
    }
}