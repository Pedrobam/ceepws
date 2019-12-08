package br.com.pedrobam.ceepws.autentication

import br.com.pedrobam.ceepws.config.security.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.naming.AuthenticationException
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AutenticacaoController {

    @Autowired
    private lateinit var authManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    @PostMapping
    fun autenticar(@RequestBody @Valid form: LoginForm): ResponseEntity<TokenDto> {
        val dadosLogin = form.converter()
        return try {
            val authentication = authManager.authenticate(dadosLogin)
            val token = tokenService.gerarToken(authentication)
            ResponseEntity(TokenDto(token, "Bearer"), HttpStatus.OK)
        } catch (e: AuthenticationException) {
            ResponseEntity.badRequest().build()
        }
    }
}