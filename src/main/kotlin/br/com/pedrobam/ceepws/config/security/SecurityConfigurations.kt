package br.com.pedrobam.ceepws.config.security

import br.com.pedrobam.ceepws.autentication.AutenticacaoService
import br.com.pedrobam.ceepws.usuario.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfigurations : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var autenticacaoService: AutenticacaoService

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    //Configuração de autenticação
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(autenticacaoService)?.passwordEncoder(BCryptPasswordEncoder())
    }

    //Configuração de autorização
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/topicos").permitAll()
            .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter::class.java)
    }

    //Configuração de arquivos estáticos(js, css, imagem, etc..)
    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**")
    }
}

fun main(args: Array<String>) {
    System.out.println(BCryptPasswordEncoder().encode("123456"))
}