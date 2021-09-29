package com.sai.listener

import com.sai.model.Greeting
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.messaging.annotation.Header
import javax.inject.Singleton

@Singleton
class EventBusListener {
    @KafkaListener(offsetReset = OffsetReset.EARLIEST)
    @Topic("\${greeting.topic}")
    fun receiveGreeting(
        @Header(name = "X-Token") messageHeader: String,
        greeting: Greeting
    ) {
        println("$messageHeader -----> $greeting")
    }
}