package com.sai.stream

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sai.eventlistener.EventListener
import com.sai.model.BaseEvent
import io.micronaut.context.annotation.Value
import io.nats.client.Connection
import io.nats.client.Dispatcher
import io.nats.client.JetStream
import io.nats.client.Nats
import io.nats.client.api.StorageType
import io.nats.client.api.StreamConfiguration
import javax.annotation.PostConstruct

abstract class BaseStream(private val eventListener: EventListener, @Value("\${env}") private val env: String) {
    private lateinit var natsConnection: Connection
    private lateinit var jetStream: JetStream
    private lateinit var dispatcher: Dispatcher

    abstract fun streamName(): String
    abstract fun topic(): String
    abstract fun consumerGroupName(): String
    abstract fun listenerFunction(): (String) -> Any

    @PostConstruct
    fun init() {
        natsConnection = Nats.connect()
        dispatcher = natsConnection.createDispatcher()
        val jetStreamManagement = natsConnection.jetStreamManagement()
        val streamConfig = StreamConfiguration.builder()
            .name(streamName())
            .subjects("${env}.${topic()}")
            .storageType(StorageType.File)
            .build()
        jetStreamManagement.addStream(streamConfig)
        jetStream = natsConnection.jetStream()
        eventListener.register(jetStream, dispatcher, topic(), consumerGroupName(), listenerFunction())
    }

    fun send(message: BaseEvent) {
        jetStream.publish("${env}.${topic()}", jacksonObjectMapper().writeValueAsBytes(message))
    }
}