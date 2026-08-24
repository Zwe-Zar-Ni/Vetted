package com.vaddshah2626.vetted.features.sources.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(source: Source): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sources: List<Source>)

    @Update
    suspend fun updateSource(source: Source)

    @Delete
    suspend fun deleteSource(source: Source)

    @Query("SELECT * FROM sources WHERE item_id = :itemId ORDER BY created_at DESC")
    fun getSourcesForItem(itemId: Int): List<Source>
}