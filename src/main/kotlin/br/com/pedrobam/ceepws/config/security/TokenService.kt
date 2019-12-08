package br.com.pedrobam.ceepws.config.security

import br.com.pedrobam.ceepws.usuario.Usuario
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Value("\${forum.jwt.expiration}")
    private lateinit var expiration: String

    @Value("\${forum.jwt.secret}")
    private lateinit var secret: String

    fun gerarToken(authentication: Authentication): String {
        val usuario = authentication.principal as Usuario
        val hoje = Date()
        val dataExpiracao = Date(hoje.time + expiration.toLong())
        return Jwts.builder()
            .setIssuer("API notes")
            .setSubject(usuario.id.toString())
            .setIssuedAt(hoje)
            .setExpiration(dataExpiracao)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun isTokenValido(token: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getIdUsuario(token: String): Long {
        val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        return claims.subject.toLong()
    }
}