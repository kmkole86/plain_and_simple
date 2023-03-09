package com.kmkole86.domain.entity

data class Page(val movies: List<Movie>, val ordinal: Int, val hasNext: Boolean)