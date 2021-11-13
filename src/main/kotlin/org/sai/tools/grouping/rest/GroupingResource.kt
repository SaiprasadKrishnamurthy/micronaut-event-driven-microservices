package org.sai.tools.grouping.rest

import org.sai.tools.grouping.model.GroupInput
import org.sai.tools.grouping.model.GroupOutput
import org.sai.tools.grouping.service.GroupingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/group")
class GroupingResource(private val groupingService: GroupingService) {

    @PostMapping("/")
    fun createGroups(@RequestBody groupInput: GroupInput): ResponseEntity<GroupOutput> {
        val groups = groupingService.groups(groupInput)
        return ResponseEntity.ok(groups)
    }

}