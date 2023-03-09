package com.kmkole86.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class PopulatedPageLocal(
    @Embedded val page: PageLocal,
    @Relation(
        parentColumn = PageLocal.ORDINAL,
        entityColumn = MovieLocal.PAGE_ORDINAL
    )
    val movies: List<MovieLocal>
)