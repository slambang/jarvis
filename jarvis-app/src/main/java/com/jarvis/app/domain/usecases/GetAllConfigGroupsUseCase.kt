package com.jarvis.app.domain.usecases

import com.jarvis.app.data.AppJsonMapper
import com.jarvis.app.data.database.dao.JarvisFieldDao
import com.jarvis.app.data.database.dao.JarvisGroupDao
import com.jarvis.app.di.BackgroundCoroutine
import com.jarvis.client.data.JarvisConfigGroup
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllConfigGroupsUseCase @Inject constructor(
    private val jsonMapper: AppJsonMapper,
    private val jarvisGroupDao: JarvisGroupDao,
    private val jarvisFieldDao: JarvisFieldDao,
    @BackgroundCoroutine private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<List<JarvisConfigGroup>> =
        flow {
            jarvisFieldDao.getAllFields().collect { allFields ->

                val mappedGroups = jarvisGroupDao.getAllGroups().map { group ->

                    val mappedFields = allFields
                        .filter { it.groupName == group.name }
                        .map(jsonMapper::mapJarvisFieldDomain)

                    JarvisConfigGroup(
                        group.name,
                        group.isCollapsable,
                        group.startCollapsed,
                        mappedFields
                    )
                }

                emit(mappedGroups)
            }
        }.flowOn(coroutineDispatcher)
}
