package com.comsysto.cycling.utils

fun toStringWithRsaField(fields: Map<String, String>) =
    fields
        .entries
        .joinToString()

fun fromStringToFieldMap(value: String): Map<String, String> =
    value.split(", ").associate {
        val (left, right) = it.split("=")
        left to right
    }