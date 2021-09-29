package com.sai.bootstrap

import com.sai.model.GreetingEvent
import com.sai.producer.EventBusProducer
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import java.util.*
import javax.inject.Singleton

@Singleton
@Requires(notEnv = [Environment.TEST])
class Bootstrap(private val eventBusProducer: EventBusProducer) {
    @EventListener
    fun fireGreetingMessages(event: StartupEvent) {
        for (i in 1..2) {
            val greetingEvent = GreetingEvent(
                eventId = UUID.randomUUID().toString(),
                message = "Hello ${UUID.randomUUID()}",
                userId = "${if (System.currentTimeMillis() % 2 == 0L) 2 else 1}"
            )
            eventBusProducer.sendGreetingEvent(greetingEvent.userId, greetingEvent)
            println(" sent ")
        }
    }
}