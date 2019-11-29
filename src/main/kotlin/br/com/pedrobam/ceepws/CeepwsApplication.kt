package br.com.pedrobam.ceepws

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableSpringDataWebSupport
class CeepwsApplication

fun main(args: Array<String>) {
	runApplication<CeepwsApplication>(*args)
}
