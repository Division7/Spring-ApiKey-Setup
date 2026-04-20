package edu.ucsb.cs156.autograderproxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AutograderProxyApplication

fun main(args: Array<String>) {
    runApplication<AutograderProxyApplication>(*args)
}


