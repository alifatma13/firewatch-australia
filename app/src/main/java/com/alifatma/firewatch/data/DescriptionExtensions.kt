package com.alifatma.firewatch.data

/**
 * Parses the semi-structured description field from RFS API into structured IncidentDetails.
 *
 * Input format: "LABEL: value<br />LABEL: value<br />..."
 *
 * Known labels are mapped to model fields (case-insensitive).
 * Unknown labels are stored in the extras map.
 * Duplicate labels: last one wins.
 * HTML entities are decoded.
 */
fun RfsProperties.parseDescription(): IncidentDetails {
    if (description.isBlank()) {
        return IncidentDetails()
    }

    val fieldMap = mutableMapOf<String, String>()
    val extras = mutableMapOf<String, String>()

    // Split by various forms of <br> tag: <br />, <br/>, <br>, < BR >, etc.
    val lines = description
        .replace(Regex("<\\s*br\\s*/?\\s*>", RegexOption.IGNORE_CASE), "\n")
        .split("\n")

    for (line in lines) {
        val trimmed = line.trim()
        if (trimmed.isEmpty()) continue

        // Split by first colon only
        val colonIndex = trimmed.indexOf(':')
        if (colonIndex == -1) {
            // No colon found, skip this line
            continue
        }

        val label = trimmed.substring(0, colonIndex).trim()
        val value = trimmed.substring(colonIndex + 1).trim()

        // Decode HTML entities
        val decodedValue = decodeHtmlEntities(value)

        // Normalize label to known field name (case-insensitive)
        val normalizedLabel = label.uppercase()

        when (normalizedLabel) {
            "ALERT LEVEL" -> fieldMap["alertLevel"] = decodedValue
            "LOCATION" -> fieldMap["location"] = decodedValue
            "COUNCIL AREA" -> fieldMap["councilArea"] = decodedValue
            "STATUS" -> fieldMap["status"] = decodedValue
            "TYPE" -> fieldMap["type"] = decodedValue
            "FIRE" -> fieldMap["fire"] = decodedValue
            "SIZE" -> fieldMap["size"] = decodedValue
            "RESPONSIBLE AGENCY" -> fieldMap["responsibleAgency"] = decodedValue
            "UPDATED" -> fieldMap["updated"] = decodedValue
            else -> extras[label] = decodedValue
        }
    }

    return IncidentDetails(
        alertLevel = fieldMap["alertLevel"],
        location = fieldMap["location"],
        councilArea = fieldMap["councilArea"],
        status = fieldMap["status"],
        type = fieldMap["type"],
        fire = fieldMap["fire"]?.let { parseFireBoolean(it) },
        size = fieldMap["size"],
        responsibleAgency = fieldMap["responsibleAgency"],
        updated = fieldMap["updated"],
        extras = extras
    )
}

/**
 * Parse "Yes"/"No" string to Boolean.
 * Case-insensitive: "yes", "YES", "Yes" all map to true.
 * Anything else maps to false.
 */
private fun parseFireBoolean(value: String): Boolean {
    return value.trim().lowercase() == "yes"
}

/**
 * Decode common HTML entities.
 * Handles: &lt; &gt; &amp; &quot; &apos; &nbsp; &#number; &#xhex;
 */
private fun decodeHtmlEntities(text: String): String {
    var result = text
    // Named entities (amp last to avoid double-decoding)
    result = result.replace("&lt;", "<")
    result = result.replace("&gt;", ">")
    result = result.replace("&quot;", "\"")
    result = result.replace("&apos;", "'")
    result = result.replace("&nbsp;", " ")
    result = result.replace("&amp;", "&")

    // Numeric entities (decimal)
    result = result.replace(Regex("&#(\\d+);")) { matchResult ->
        matchResult.groupValues[1].toIntOrNull()?.toChar()?.toString() ?: matchResult.value
    }
    // Numeric entities (hex)
    result = result.replace(Regex("&#x([0-9a-fA-F]+);")) { matchResult ->
        matchResult.groupValues[1].toIntOrNull(16)?.toChar()?.toString() ?: matchResult.value
    }

    return result
}

fun RfsProperties.extractIncidentId() : String? {
    val regex = "(\\d+)$".toRegex()
    return regex.find(this.guid)?.value
}

