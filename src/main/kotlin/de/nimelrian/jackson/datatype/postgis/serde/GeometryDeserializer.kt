package de.nimelrian.jackson.datatype.postgis.serde

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import de.nimelrian.jackson.datatype.postgis.parser.GeometryParser
import org.postgis.Geometry

class GeometryDeserializer<ObjectType: Geometry>(
    private val parser: GeometryParser<ObjectType>
): JsonDeserializer<ObjectType>() {
    override fun deserialize(jsonParser: JsonParser, ctxt: DeserializationContext): ObjectType {
        val codec = jsonParser.codec
        val rootNode = codec.readTree<JsonNode>(jsonParser)
        return parser.parse(rootNode)
    }
}
