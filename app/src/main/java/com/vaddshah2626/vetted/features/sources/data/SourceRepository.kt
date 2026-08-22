package com.vaddshah2626.vetted.features.sources.data

class SourceRepository(private val sourceDao: SourceDao) {

    fun getSourcesForItem(itemId: Int) {
        sourceDao.getSourcesForItem(itemId)
    }

    suspend fun insertSource(source: Source) {
        sourceDao.insertSource(source)
    }

    suspend fun insertSources(sources: List<Source>) {
        sourceDao.insertSources(sources)
    }

    suspend fun deleteSource(source: Source) {
        sourceDao.deleteSource(source)
    }

    suspend fun deleteSourceById(sourceId: Int) {
        sourceDao.deleteSourceById(sourceId)
    }

}