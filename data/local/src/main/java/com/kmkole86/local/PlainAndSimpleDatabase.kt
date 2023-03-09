package com.kmkole86.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kmkole86.local.dao.MovieDao
import com.kmkole86.local.model.MovieLocal
import com.kmkole86.local.model.PageLocal

@Database(
    version = 1,
    entities = [
        PageLocal::class,
        MovieLocal::class
    ]
)

abstract class PlainAndSimpleDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME: String = "plain_and_simple_database"
    }

    abstract fun moviesDao(): MovieDao
}