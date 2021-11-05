package de.nimelrian.jackson.datatype.postgis.parser

import com.fasterxml.jackson.databind.JsonNode
import org.postgis.Geometry

interface GeometryParser<ObjectType: Geometry> {
    fun parse(node: JsonNode): ObjectType
}
