package com.sai.eventpublisher

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sai.model.BaseEvent
import com.sai.streamconfig.BaseStreamConfig
import io.micronaut.context.annotation.Value
import javax.inject.Singleton

@Singleton
class EventBusSender(private val baseStreamConfig: BaseStreamConfig, @Value("\${env}") private val env: String) {
    fun send(topic: String, message: BaseEvent) {
        baseStreamConfig.jetStream.publish("${env}.${topic}", jacksonObjectMapper().writeValueAsBytes(message))
    }
}