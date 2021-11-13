package org.sai.tools.grouping.model

interface Cardinality {
    fun getValue(): String
    fun getCardinality(): Long
}