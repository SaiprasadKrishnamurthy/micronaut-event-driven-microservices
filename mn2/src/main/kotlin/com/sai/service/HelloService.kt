package com.sai.service

import com.sai.eventpublisher.EventBusSender
import com.sai.model.HelloEvent
import io.micronaut.context.annotation.Value
import java.util.*
import javax.inject.Singleton


@Singleton
class HelloService(
    private val eventBusSender: EventBusSender,
    @Value("\${hello.topic.name}") private val topic: String
) {
    fun hello(msg: String) {
        val event = HelloEvent(eventId = UUID.randomUUID().toString(), userId = "userId", message = msg)
        eventBusSender.send(topic, event)
    }
}