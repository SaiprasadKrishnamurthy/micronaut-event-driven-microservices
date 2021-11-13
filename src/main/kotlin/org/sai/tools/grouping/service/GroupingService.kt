package org.sai.tools.grouping.service

import org.sai.tools.grouping.entity.Member
import org.sai.tools.grouping.model.GroupInput
import org.sai.tools.grouping.model.GroupOutput
import org.sai.tools.grouping.repository.MemberRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupingService(private val memberRepository: MemberRepository) {

    fun groups(groupInput: GroupInput): GroupOutput {

        val delimiter = groupInput.delimiter
        val inputs = groupInput.entries
        val mapped = inputs.map { "|" + it.split(delimiter).map { s -> s.trim() }.filter { s -> s.isNotBlank() }.joinToString("|") + "|" }
        val jobId = UUID.randomUUID().toString()
        val distinctValues = mutableSetOf<String>()

        mapped.forEach { i ->
            val values = i.split("|").map { it.trim() }.filter { it.isNotBlank() }
            values.forEach { v ->
                memberRepository.save(Member(value = v, fullinput = i, jobid = jobId))
                distinctValues.add(v)
            }
        }

        // Get all cardinalities (max first)
        val allCardinalities = memberRepository.getAllCardinalities(jobId)

        val groups = mutableListOf<LinkedHashSet<String>>()

        allCardinalities.forEach { cardinality ->
            val group = linkedSetOf<String>()
            val allValuesFlattened = groups.map { it }.flatten()
            if (!allValuesFlattened.contains(cardinality.getValue())) {
                val value = cardinality.getValue()
                gatherCardinalities(jobId, allValuesFlattened, group, listOf(value))
            }
            if (group.size > 1) {
                groups.add(group)
            }
        }
        val groupValues = groups.flatten()
        val ungroupedValues = distinctValues.filter { !groupValues.contains(it) }
        return GroupOutput(distinctValues.size, groups.size + ungroupedValues.size, ungroupedValues, groups.map { it.sorted().toSortedSet() })
    }

    private fun gatherCardinalities(
        jobId: String,
        alreadyGrouped: List<String>,
        group: LinkedHashSet<String>,
        runningValues: List<String>
    ) {
        if (runningValues.size == 1) {
            group.add(runningValues[0])
        }
        val fullinput =
            if (runningValues.size > 1) "|" + runningValues.joinToString("") { "$it|" } else runningValues.joinToString(
                ""
            ) { "|$it|" }
        val bestCardinalities = memberRepository.getBestCardinalities(fullinput, jobId)
            .map { it.getValue() }
            .filter { !alreadyGrouped.contains(it) }

        if (bestCardinalities.isNotEmpty() && bestCardinalities.size > runningValues.size) {
            val nextBestValue = bestCardinalities[runningValues.size]
            group.add(nextBestValue)
            gatherCardinalities(jobId, alreadyGrouped, group, runningValues + listOf(nextBestValue))
        }
    }
}