package com.kmkole86.plainandsimple.home.models

import com.kmkole86.domain.entity.Movie
import com.kmkole86.domain.entity.Page

internal fun createPageDummyData(pageOrdinal: Int): Page {
    return Page(
        ordinal = pageOrdinal,
        movies = (1..20).map { createMovieDummyData(it) },
        hasNext = true
    )
}

internal fun createMovieDummyData(id: Int): Movie {
    return Movie(
        id = id,
        title = "title $id",
        overview = "overview $id",
        posterPath = "posterPath $id",
        releaseDate = "n/a",
        voteAverage = id.toFloat(),
        voteCount = id
    )
}