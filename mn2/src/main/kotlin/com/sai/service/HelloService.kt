package com.sai.service

import com.sai.model.HelloEvent
import com.sai.stream.HelloStream
import java.util.*
import javax.inject.Singleton


@Singleton
class HelloService(private val helloStream: HelloStream) {
    fun hello(msg: String) {
        val event = HelloEvent(eventId = UUID.randomUUID().toString(), userId = "userId", message = msg)
        helloStream.send(event)
    }
}