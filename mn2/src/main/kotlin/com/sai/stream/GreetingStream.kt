package com.sai.stream

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sai.config.NatsConfiguration
import com.sai.eventlistener.EventListener
import com.sai.model.Greeting
import com.sai.model.GreetingEvent
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Value
import java.nio.charset.Charset
import java.util.*

@Context
class GreetingStream(
    @Value("\${greeting.stream.name}") private val streamName: String,
    @Value("\${greeting.topic.name}") private val topic: String,
    @Value("\${greeting.consumer.group.name}") private val consumerGroup: String,
    @Value("\${env}") private val env: String,
    eventListener: EventListener,
    natsConfiguration: NatsConfiguration
) : AbstractStream(eventListener, env, natsConfiguration) {

    override fun streamName() = streamName
    override fun consumerGroupName() = consumerGroup
    override fun topic(): String = topic

    override fun listenerFunction(): (String) -> Any {
        return { payload ->
            val helloEvent =
                jacksonObjectMapper().readValue(
                    payload.toByteArray(Charset.defaultCharset()),
                    GreetingEvent::class.java
                )
            Greeting(
                message = helloEvent.message,
                eventId = UUID.randomUUID().toString(),
                userId = UUID.randomUUID().toString()
            )
        }
    }
}