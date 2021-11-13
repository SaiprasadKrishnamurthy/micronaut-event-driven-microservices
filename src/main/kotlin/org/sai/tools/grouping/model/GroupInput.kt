package org.sai.tools.grouping.model

data class GroupInput(val entries: List<String>, val delimiter: String)
data class GroupOutput(
    val originalInputSize: Int,
    val compressedSize: Int,
    val ungrouped: List<String>,
    val entries: List<Set<String>>
) {
    val reductionPercentage: Double
        get() = String.format(
            "%.1f",
            ((originalInputSize.toDouble() - compressedSize.toDouble()) / originalInputSize.toDouble()) * 100.0
        ).toDouble()
}