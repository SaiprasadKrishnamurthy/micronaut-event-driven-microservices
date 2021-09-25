package com.sai.model

data class HelloEvent(val message: String, override val eventId: String, override val userId: String) :
    BaseEvent(eventId, userId)
