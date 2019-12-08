package br.com.pedrobam.ceepws.usuario

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UsuarioRepository: JpaRepository<Usuario, Long> {

    fun findByEmail(email: String): Optional<Usuario>

    fun findAllByEmail(email: String): Optional<List<Usuario>>
}