package com.sai.eventlistener

import io.micronaut.context.annotation.Value
import io.micronaut.context.event.ApplicationEventPublisher
import io.nats.client.Dispatcher
import io.nats.client.JetStream
import io.nats.client.PushSubscribeOptions
import javax.inject.Singleton

@Singleton
class EventListener(
    private val publisher: ApplicationEventPublisher,
    @Value("\${env}") private val env: String
) {

    fun register(
        jetStream: JetStream,
        dispatcher: Dispatcher,
        topic: String,
        queue: String,
        payloadTransformerFunction: (input: String) -> Any
    ) {
        jetStream.subscribe(
            "${env}.${topic}", queue, dispatcher,
            { m ->
                val transformedPayload = payloadTransformerFunction(String(m.data))
                publisher.publishEvent(transformedPayload)
            },
            true,
            PushSubscribeOptions.builder().durable("events-durable-store").build()
        )
    }
}