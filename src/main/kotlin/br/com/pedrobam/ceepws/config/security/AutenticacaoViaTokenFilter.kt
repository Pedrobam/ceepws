package br.com.pedrobam.ceepws.config.security

import br.com.pedrobam.ceepws.usuario.UsuarioRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AutenticacaoViaTokenFilter(
    private val tokenService: TokenService,
    private val usuarioRepository: UsuarioRepository
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = recuperarToken(request)
        val valido = tokenService.isTokenValido(token)
        if (valido) {
            autenticarCliente(token!!)
        }
        filterChain.doFilter(request, response)
    }

    private fun autenticarCliente(token: String) {
        val idUsuario = tokenService.getIdUsuario(token)
        val usuario = usuarioRepository.findById(idUsuario).get()
        val authentication = UsernamePasswordAuthenticationToken(usuario, null, usuario.authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")
        if (token.isNullOrEmpty()) {
            return null
        }
        return token.substring(7, token.length)
    }
}