package com.kmkole86.local.data_source

import com.kmkole86.local.dao.MovieDao
import com.kmkole86.local.model.PopulatedPageLocal
import javax.inject.Inject

interface MovieLocalDataSource {

    suspend fun insert(page: PopulatedPageLocal)

    suspend fun getPopulatedPage(pageOrdinal: Int): PopulatedPageLocal?
}

class MovieLocalDataSourceImpl @Inject constructor(private val dao: MovieDao) :
    MovieLocalDataSource {

    override
    suspend fun insert(page: PopulatedPageLocal) {
        dao.insertPage(page)
    }

    override
    suspend fun getPopulatedPage(pageOrdinal: Int): PopulatedPageLocal? {
        return dao.getPopulatedPage(pageOrdinal)
    }
}