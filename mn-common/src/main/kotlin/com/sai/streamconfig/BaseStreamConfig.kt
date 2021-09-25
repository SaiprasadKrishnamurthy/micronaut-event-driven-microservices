package com.sai.streamconfig

import com.sai.eventlistener.EventListener
import io.micronaut.context.annotation.Value
import io.nats.client.Connection
import io.nats.client.Dispatcher
import io.nats.client.JetStream
import io.nats.client.Nats
import io.nats.client.api.StorageType
import io.nats.client.api.StreamConfiguration
import javax.annotation.PostConstruct

abstract class BaseStreamConfig(private val eventListener: EventListener, @Value("\${env}") private val env: String) {
    lateinit var natsConnection: Connection
    lateinit var jetStream: JetStream
    lateinit var dispatcher: Dispatcher

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
}