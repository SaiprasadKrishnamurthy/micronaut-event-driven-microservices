package com.sai.service

import com.sai.model.GreetingEvent
import com.sai.streamconfig.GreetingStream
import java.util.*
import javax.inject.Singleton


@Singleton
class GreetingService(private val greetingStream: GreetingStream) {
    fun greet(msg: String) {
        val event = GreetingEvent(eventId = UUID.randomUUID().toString(), userId = "userId", message = msg)
        greetingStream.send(event)
    }
}