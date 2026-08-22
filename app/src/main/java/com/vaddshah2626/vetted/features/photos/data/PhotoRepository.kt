package com.vaddshah2626.vetted.features.photos.data

class PhotoRepository(private val photoDao: PhotoDao) {

    fun getPhotosForItem(itemId: Int) {
        photoDao.getPhotosForItem(itemId)
    }

    fun getPhotosForItemAndType(itemId: Int, photoType: PhotoType) {
        photoDao.getPhotosForItemAndType(itemId, photoType)
    }

    suspend fun insertPhoto(photo: Photo) {
        photoDao.insertPhoto(photo)
    }

    suspend fun insertPhotos(photo: List<Photo>) {
        photoDao.insertPhotos(photo)
    }

    suspend fun deletePhoto(photo: Photo) {
        photoDao.deletePhoto(photo)
    }

    suspend fun deletePhotoById(photoId: Int) {
        photoDao.deletePhotoById(photoId)
    }

}