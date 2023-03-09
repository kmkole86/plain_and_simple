package com.kmkole86.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kmkole86.local.model.*

@Dao
abstract class MovieDao {

    @Insert
    abstract suspend fun _insertMovies(movies: List<MovieLocal>)

    @Insert
    abstract suspend fun _insertPage(page: PageLocal)

    @Query("SELECT EXISTS(SELECT * FROM ${PageLocal.ENTITY_NAME} WHERE ${PageLocal.ORDINAL} = :id)")
    abstract suspend fun isPageCached(id: Int): Boolean

    @Transaction
    open suspend fun insertPage(value: PopulatedPageLocal) {
        _insertMovies(value.movies)
        _insertPage(value.page)
    }

    @Transaction
    @Query("SELECT * FROM ${PageLocal.ENTITY_NAME} WHERE ${PageLocal.ORDINAL} = :pageOrdinal")
    abstract suspend fun getPopulatedPage(pageOrdinal: Int): PopulatedPageLocal?

    @Query("DELETE FROM ${PageLocal.ENTITY_NAME} WHERE ${PageLocal.ORDINAL} = :pageOrdinal")
    abstract suspend fun _deletePage(pageOrdinal: Int)

    @Query("DELETE FROM ${MovieLocal.ENTITY_NAME} WHERE ${MovieLocal.ID} = :pageOrdinal")
    abstract suspend fun _deleteMoviesPage(pageOrdinal: Int)

    @Transaction
    open suspend fun clearPageData(pageOrdinal: Int) {
        _deleteMoviesPage(pageOrdinal)
        _deletePage(pageOrdinal)
    }
}