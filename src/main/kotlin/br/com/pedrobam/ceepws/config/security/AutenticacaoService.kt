package br.com.pedrobam.ceepws.config.security

import br.com.pedrobam.ceepws.usuario.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AutenticacaoService : UserDetailsService {

    @Autowired
    private lateinit var repository: UsuarioRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val usuario = repository.findByEmail(username)
        if (usuario.isPresent) {
            return usuario.get()
        }
        throw UsernameNotFoundException("Dados invalidos!")
    }
}