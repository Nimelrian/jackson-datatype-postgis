package de.nimelrian.jackson.datatype.postgis.parser

import com.fasterxml.jackson.databind.JsonNode
import org.postgis.Point

class PointParser : GeometryParser<Point> {
    override fun parse(node: JsonNode): Point {
        val array = node.get("coordinates")
        assert(array.isArray) { "Expected coordinate array" }
        return when (array.size()) {
            2 -> Point(
                array.get(0).asDouble(),
                array.get(1).asDouble(),
            )
            3 -> Point(
                array.get(0).asDouble(),
                array.get(1).asDouble(),
                array.get(2).asDouble(),
            )
            else -> throw IllegalArgumentException("Expecting coordinate array of format [x, y, <z>] for single point. Got ${array.size()} elements.")
        }
    }

}
