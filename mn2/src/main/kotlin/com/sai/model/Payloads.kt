package com.sai.model

data class Hello(val message: String, override val eventId: String, override val userId: String) : BaseModel(eventId, userId)
