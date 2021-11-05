package de.nimelrian.jackson.datatype.postgis

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleModule
import de.nimelrian.jackson.datatype.postgis.parser.PointParser
import de.nimelrian.jackson.datatype.postgis.serde.GeometryDeserializer
import de.nimelrian.jackson.datatype.postgis.serde.GeometrySerializer
import org.postgis.Geometry
import org.postgis.Point

fun postGisModule(): Module = SimpleModule("postGisModule")
    .addDeserializer(Point::class.java, GeometryDeserializer(PointParser()))
    .addSerializer(Geometry::class.java, GeometrySerializer())
