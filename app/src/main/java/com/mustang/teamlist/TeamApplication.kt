package com.mustang.teamlist

import android.app.Application
import com.mustang.teamlist.Repository.TeamRepository
import com.mustang.teamlist.Room.TeamDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TeamApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { TeamDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { TeamRepository(database.getTeamDao()) }

}