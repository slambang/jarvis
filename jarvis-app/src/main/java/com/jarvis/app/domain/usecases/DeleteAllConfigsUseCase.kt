package com.jarvis.app.domain.usecases

import com.jarvis.app.data.database.dao.JarvisFieldDao
import com.jarvis.app.data.database.dao.JarvisGroupDao
import com.jarvis.app.di.BackgroundCoroutine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAllConfigsUseCase @Inject constructor(
    private val jarvisGroupDao: JarvisGroupDao,
    private val jarvisFieldDao: JarvisFieldDao,
    @BackgroundCoroutine private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Unit =
        withContext(coroutineDispatcher) {
            jarvisGroupDao.deleteAllGroups()
            jarvisFieldDao.deleteAllFields()
        }
}
