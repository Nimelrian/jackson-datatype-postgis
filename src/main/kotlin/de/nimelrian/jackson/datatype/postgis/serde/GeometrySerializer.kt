package de.nimelrian.jackson.datatype.postgis.serde

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.postgis.Geometry
import org.postgis.Point

class GeometrySerializer : JsonSerializer<Geometry>() {
    override fun serialize(value: Geometry, generator: JsonGenerator, serializers: SerializerProvider?) {
        serialize(value, generator)
    }

    private fun serialize(value: Geometry, generator: JsonGenerator) {
        when (value) {
            is Point -> serializePoint(value, generator)
            else -> {
                val supportedTypes = listOf(Point::class.java.name)
                val message =
                    "Geometry of type ${value.javaClass.name} cannot be serialized as JSON. Supported types are: ${supportedTypes.joinToString()}"
                throw JsonMappingException(generator, message)

            };
        }
    }

    private fun serializePoint(point: Point, generator: JsonGenerator) {
        generator.writeStartObject();
        generator.writeStringField("type", "Point");
        generator.writeArrayFieldStart("coordinates")
        generator.writeNumber(point.x)
        generator.writeNumber(point.y)
        if (point.dimension == 3) {
            generator.writeNumber(point.z)
        }
        generator.writeEndArray()
        generator.writeEndObject();
    }

}
