package com.kmkole86.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = PageLocal.ENTITY_NAME)
data class PageLocal(
    @PrimaryKey @ColumnInfo(name = ORDINAL) val ordinal: Int,
    @ColumnInfo(name = HAS_NEXT) val hasNext: Boolean
) {

    companion object {
        const val ENTITY_NAME = "page_local"
        const val ORDINAL = "ordinal"
        const val HAS_NEXT = "has_next"
    }
}