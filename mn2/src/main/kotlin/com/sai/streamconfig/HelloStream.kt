package com.sai.streamconfig

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sai.eventlistener.EventListener
import com.sai.model.Hello
import com.sai.model.HelloEvent
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Value
import java.nio.charset.Charset
import java.util.*

@Context
class HelloStream(
    @Value("\${hello.stream.name}") private val streamName: String,
    @Value("\${hello.topic.name}") private val topic: String,
    @Value("\${hello.consumer.group.name}") private val consumerGroup: String,
    @Value("\${env}") private val env: String,
    private val eventListener: EventListener
) : BaseStream(eventListener, env) {

    override fun streamName() = streamName
    override fun consumerGroupName() = consumerGroup
    override fun topic(): String = topic

    override fun listenerFunction(): (String) -> Any {
        return { payload ->
            val helloEvent =
                jacksonObjectMapper().readValue(payload.toByteArray(Charset.defaultCharset()), HelloEvent::class.java)
            Hello(
                message = helloEvent.message,
                eventId = UUID.randomUUID().toString(),
                userId = UUID.randomUUID().toString()
            )
        }
    }
}