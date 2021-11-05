package de.nimelrian.jackson.datatype.postgis.parser

import io.kotest.assertions.json.shouldEqualJson
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.numericDoubles
import io.kotest.property.checkAll
import org.postgis.Point

internal class PointParserTest : DescribeSpec({
    describe("deserialize") {
        it("should deserialize point without z coordinate") {
            checkAll(coordinate2D) { (x, y) ->
                val mapper = testObjectMapper()
                val json = """
                    {
                      "type": "Point",
                      "coordinates": [
                        $x,
                        $y
                      ]
                    }
                """.trimIndent()
                val point = mapper.readValue(json, Point::class.java)
                point shouldBe Point(x, y)
            }
        }

        it("should deserialize point with z coordinate") {
            checkAll(coordinate3D) { (x, y, z) ->
                val mapper = testObjectMapper()
                val json = """
                    {
                      "type": "Point",
                      "coordinates": [
                        $x,
                        $y,
                        $z
                      ]
                    }
                """.trimIndent()
                val point = mapper.readValue(json, Point::class.java)
                point shouldBe Point(x, y, z)
            }
        }
    }

    describe("serialize") {
        it("should serialize point without z coordinate") {
            checkAll(coordinate2D) { (x, y) ->
                val mapper = testObjectMapper()
                val point = Point(x, y)
                val expectedJson = """
                    {
                      "type": "Point",
                      "coordinates": [
                        $x,
                        $y
                      ]
                    }
                """.trimIndent()
                val json = mapper.writeValueAsString(point)
                json shouldEqualJson expectedJson
            }
        }

        it("should serialize point with z coordinate") {
            checkAll(coordinate3D) { (x, y, z) ->
                val mapper = testObjectMapper()
                val point = Point(x, y, z)
                val expectedJson = """
                    {
                      "type": "Point",
                      "coordinates": [
                        $x,
                        $y,
                        $z
                      ]
                    }
                """.trimIndent()
                println(point)
                val json = mapper.writeValueAsString(point)
                json shouldEqualJson expectedJson
            }
        }
    }
})

private val coordinate2D = Arb.bind(
    Arb.numericDoubles(),
    Arb.numericDoubles(),
) { x, y -> Pair(x, y) }

private val coordinate3D = Arb.bind(
    Arb.numericDoubles(),
    Arb.numericDoubles(),
    Arb.numericDoubles(),
) { x, y, z -> Triple(x, y, z) }
