package com.mustang.teamlist.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mustang.teamlist.Model.TeamMember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TeamMember::class], version = 1)
abstract class TeamDatabase : RoomDatabase() {

    abstract fun getTeamDao() : TeamDAO

    //Singleton
    companion object{

        @Volatile
        private var INSTANCE : TeamDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope) : TeamDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,TeamDatabase::class.java,"team_database")
                    .addCallback(TeamDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

    private class TeamDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let {database ->

                scope.launch {
                    val teamDao = database.getTeamDao()

                    teamDao.insert(TeamMember("Name","Email id","Address","Contact number"))
                    teamDao.insert(TeamMember("Name 2","Email id","Address","Contact number"))
                }

            }

        }

    }

}