package com.jarvis.app.domain.usecases

import com.jarvis.app.data.AppJsonMapper
import com.jarvis.app.data.database.dao.JarvisFieldDao
import com.jarvis.app.data.database.dao.JarvisGroupDao
import com.jarvis.client.data.JarvisConfig
import com.jarvis.client.data.JarvisConfigGroup
import com.jarvis.client.data.JarvisField
import java.io.InputStream
import javax.inject.Inject

class RefreshConfigUseCase @Inject constructor(
    private val jsonMapper: AppJsonMapper,
    private val jarvisGroupDao: JarvisGroupDao,
    private val jarvisFieldDao: JarvisFieldDao
) {
    operator fun invoke(inputStream: InputStream): JarvisConfig {
        val jarvisConfig = jsonMapper.readConfig(inputStream)

        jarvisConfig.groups.forEach { group ->
            insertGroup(group)
            insertFields(group.name, group.fields)
        }

        return jarvisConfig
    }

    private fun insertGroup(group: JarvisConfigGroup) {
        val entity = jsonMapper.mapToJarvisGroupEntity(group)
        jarvisGroupDao.insertGroup(entity)
    }

    private fun insertFields(groupName: String, fields: List<JarvisField<Any>>): Unit =
        fields.forEach {
            val entity = jsonMapper.mapToJarvisFieldEntity(groupName, it)
            jarvisFieldDao.insertField(entity)
        }
}
