package com.vaddshah2626.vetted.features.photos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: Photo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<Photo>)

    @Delete
    suspend fun deletePhoto(photo: Photo)

    @Query("DELETE FROM photos WHERE id = :photoId")
    suspend fun deletePhotoById(photoId: Int)

    @Query("SELECT * FROM photos WHERE item_id = :itemId ORDER BY created_at DESC")
    fun getPhotosForItem(itemId: Int): List<Photo>

    @Query("SELECT * FROM photos WHERE item_id = :itemId AND photo_type = :photoType ORDER BY created_at DESC")
    fun getPhotosForItemAndType(itemId: Int, photoType: PhotoType): List<Photo>
}