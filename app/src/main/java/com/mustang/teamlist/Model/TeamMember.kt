package com.mustang.teamlist.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
class TeamMember(val memberName: String, val email: String, val address: String, val contact: String) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}