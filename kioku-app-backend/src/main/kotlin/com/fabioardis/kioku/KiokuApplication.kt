package com.fabioardis.kioku

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KiokuApplication

fun main(args: Array<String>) {
	runApplication<KiokuApplication>(*args)
}
