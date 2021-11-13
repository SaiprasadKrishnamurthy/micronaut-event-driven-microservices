package org.sai.tools.grouping.repository

import org.sai.tools.grouping.entity.Member
import org.sai.tools.grouping.model.Cardinality
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    @Query(value = "SELECT value as value, count(value) cardinality from members where jobid=:jobId group by value having cardinality > 1 order by cardinality desc, value asc", nativeQuery = true)
    fun getAllCardinalities(@Param("jobId") jobId: String): List<Cardinality>

    @Query(value = "SELECT value as value, count(value) cardinality from members where fullinput like %:fullinput% and jobid=:jobId group by value having cardinality > 1 order by cardinality desc", nativeQuery = true)
    fun getBestCardinalities(@Param("fullinput") fullinput: String, @Param("jobId") jobId: String): List<Cardinality>
}