package de.nimelrian.jackson.datatype.postgis.parser

import com.fasterxml.jackson.databind.ObjectMapper
import de.nimelrian.jackson.datatype.postgis.postGisModule

fun testObjectMapper(): ObjectMapper = ObjectMapper().registerModule(postGisModule())
