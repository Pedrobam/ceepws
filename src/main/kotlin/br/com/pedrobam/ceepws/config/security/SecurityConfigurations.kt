package br.com.pedrobam.ceepws.config.security

import br.com.pedrobam.ceepws.CeepwsApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
@Configuration
class SecurityConfigurations : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var autenticacaoService: AutenticacaoService

    //Configuração de autenticação
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(autenticacaoService)?.passwordEncoder(BCryptPasswordEncoder())
    }

    //Configuração de autorização
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/topicos").permitAll()
            .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
            .anyRequest().authenticated()
            .and().formLogin()
    }

    //Configuração de arquivos estáticos(js, css, imagem, etc..)
    override fun configure(web: WebSecurity?) {

    }
}

fun main(args: Array<String>) {
    System.out.println(BCryptPasswordEncoder().encode("123456"))
}