package com.sai.controller

import com.sai.service.GreetingService
import com.sai.service.HelloService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*

@Controller("/greeting")
class GreetingController(private val greetingService: GreetingService) {

    @Get(uri = "/", produces = ["text/plain"])
    fun index(): String {
        val msg = UUID.randomUUID().toString()
        greetingService.greet(msg)
        return "Greeting Response $msg"
    }
}