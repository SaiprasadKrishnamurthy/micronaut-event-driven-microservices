package com.sai.producer

import com.sai.model.Greeting
import com.sai.model.GreetingEvent
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.messaging.annotation.Header


@KafkaClient
@Header(name = "X-Token", value = "\${kafka.message.token}")
interface EventBusProducer {
    @Topic("\${greeting.topic}")
    fun sendGreetingEvent(@KafkaKey id: String, greetingEvent: GreetingEvent)
}