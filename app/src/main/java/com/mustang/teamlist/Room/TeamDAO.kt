package com.mustang.teamlist.Room

import androidx.room.*
import com.mustang.teamlist.Model.TeamMember
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDAO {

    @Insert
    suspend fun insert(teamMember: TeamMember)

    @Update
    suspend fun update(teamMember: TeamMember)

    @Delete
    suspend fun delete(teamMember: TeamMember)

    @Query("DELETE FROM team_table")
    suspend fun deleteAllMembers()

    @Query("SELECT * FROM team_table ORDER BY id ASC")
    fun getAllMembers() : Flow<List<TeamMember>>

}