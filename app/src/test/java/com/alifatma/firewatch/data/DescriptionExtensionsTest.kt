package com.alifatma.firewatch.data

import com.alifatma.firewatch.data.RfsFeaturesStub.listWithoutPropertyDescription
import com.alifatma.firewatch.data.RfsFeaturesStub.pointIncidentStubForParsingProperties
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DescriptionExtensionsTest {

    @Test
    fun `parseDescription returns empty IncidentDetails when description is empty`() {
        val props = listWithoutPropertyDescription[0].properties

        val result = props.parseDescription()

        assertNull(result.alertLevel)
        assertNull(result.location)
        assertTrue(result.extras.isEmpty())
    }

    @Test
    fun `parseDescription parses all known fields correctly`() {

        val props = pointIncidentStubForParsingProperties[0].properties
        val result = props.parseDescription()

        assertEquals("Advice", result.alertLevel)
        assertEquals("MAIN CREEK RD, MAIN CREEK 2420", result.location)
        assertEquals("Dungog", result.councilArea)
        assertEquals("Being controlled", result.status)
        assertEquals("Bush Fire", result.type)
        assertEquals(true, result.fire)
        assertEquals("0 ha", result.size)
        assertEquals("Rural Fire Service", result.responsibleAgency)
        assertEquals("16 Apr 2026 23:54", result.updated)
        assertTrue(result.extras.isEmpty())
    }

    @Test
    fun `parseDescription handles missing fields as null`() {
        val description = "ALERT LEVEL: Warning<br />SIZE: 150 ha"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Warning", result.alertLevel)
        assertNull(result.location)
        assertNull(result.councilArea)
        assertEquals("150 ha", result.size)
        assertNull(result.status)
    }

    @Test
    fun `parseDescription parses FIRE as true when value is Yes`() {
        val description = "FIRE: Yes"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals(true, result.fire)
    }

    @Test
    fun `parseDescription parses FIRE as false when value is No`() {
        val description = "FIRE: No"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals(false, result.fire)
    }

    @Test
    fun `parseDescription parses FIRE as true when value is yes (lowercase)`() {
        val description = "FIRE: yes"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals(true, result.fire)
    }

    @Test
    fun `parseDescription parses FIRE as false when value is empty`() {
        val description = "FIRE: "
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals(false, result.fire)
    }

    @Test
    fun `parseDescription decodes HTML entities in values`() {
        val description = "LOCATION: Road &lt; Street &amp; More&nbsp;Text"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Road < Street & More Text", result.location)
    }

    @Test
    fun `parseDescription handles various br tag formats`() {
        val description1 = "ALERT LEVEL: Advice<br />SIZE: 100 ha"
        val description2 = "ALERT LEVEL: Advice<br/>SIZE: 100 ha"
        val description3 = "ALERT LEVEL: Advice<br>SIZE: 100 ha"
        val description4 = "ALERT LEVEL: Advice< BR >SIZE: 100 ha"

        for (desc in listOf(description1, description2, description3, description4)) {
            val props = RfsProperties(description = desc)
            val result = props.parseDescription()
            assertEquals("Advice", result.alertLevel)
            assertEquals("100 ha", result.size)
        }
    }

    @Test
    fun `parseDescription stores unknown labels in extras map`() {
        val description = "ALERT LEVEL: Advice<br />CUSTOM_FIELD: CustomValue<br />ANOTHER: AnotherValue"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Advice", result.alertLevel)
        assertEquals("CustomValue", result.extras["CUSTOM_FIELD"])
        assertEquals("AnotherValue", result.extras["ANOTHER"])
    }

    @Test
    fun `parseDescription uses last value when labels are duplicated`() {
        val description = "ALERT LEVEL: First<br />ALERT LEVEL: Second<br />ALERT LEVEL: Third"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Third", result.alertLevel)
    }

    @Test
    fun `parseDescription skips lines with no colon`() {
        val description = "ALERT LEVEL: Advice<br />INVALID LINE<br />SIZE: 50 ha"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Advice", result.alertLevel)
        assertEquals("50 ha", result.size)
    }

    @Test
    fun `parseDescription handles empty values as empty string`() {
        val description = "ALERT LEVEL: <br />LOCATION: "
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("", result.alertLevel)
        assertEquals("", result.location)
    }

    @Test
    fun `parseDescription handles value containing colons`() {
        val description = "LOCATION: Road: Name: Extended"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Road: Name: Extended", result.location)
    }

    @Test
    fun `parseDescription is case-insensitive for label matching`() {
        val description = "alert level: Info<br />location: Test<br />COUNCIL AREA: Area"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Info", result.alertLevel)
        assertEquals("Test", result.location)
        assertEquals("Area", result.councilArea)
    }

    @Test
    fun `parseDescription trims whitespace around labels and values`() {
        val description = "   ALERT LEVEL   :   Advice   <br />   SIZE   :   100 ha   "
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Advice", result.alertLevel)
        assertEquals("100 ha", result.size)
    }

    @Test
    fun `parseDescription decodes numeric HTML entities`() {
        val description = "LOCATION: Test&#32;Location"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Test Location", result.location)
    }

    @Test
    fun `parseDescription decodes hex HTML entities`() {
        val description = "LOCATION: Test&#x20;Location"
        val props = RfsProperties(description = description)
        val result = props.parseDescription()

        assertEquals("Test Location", result.location)
    }
}
