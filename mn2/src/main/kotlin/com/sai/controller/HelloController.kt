package com.sai.controller

import com.sai.service.HelloService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*

@Controller("/hello")
class HelloController(private val helloService: HelloService) {

    @Get(uri = "/", produces = ["text/plain"])
    fun index(): String {
        val msg = UUID.randomUUID().toString()
        helloService.hello(msg)
        return "Example Response $msg"
    }
}