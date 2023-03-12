package com.mustang.teamlist.Repository

import androidx.annotation.WorkerThread
import com.mustang.teamlist.Model.TeamMember
import com.mustang.teamlist.Room.TeamDAO
import kotlinx.coroutines.flow.Flow

class TeamRepository(private val teamDAO: TeamDAO) {

    val allMembers : Flow<List<TeamMember>> =  teamDAO.getAllMembers()

    @WorkerThread
    suspend fun insert(teamMember: TeamMember) {
        teamDAO.insert(teamMember)
    }

    @WorkerThread
    suspend fun update(teamMember: TeamMember) {
        teamDAO.update(teamMember)
    }

    @WorkerThread
    suspend fun delete(teamMember: TeamMember) {
        teamDAO.delete(teamMember)
    }

    @WorkerThread
    suspend fun deleteAllMembers() {
        teamDAO.deleteAllMembers()
    }

}