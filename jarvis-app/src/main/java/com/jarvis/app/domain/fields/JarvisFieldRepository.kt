package com.jarvis.app.domain.fields

import com.jarvis.app.data.database.JarvisFieldDao
import com.jarvis.app.data.database.JarvisFieldEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JarvisFieldRepository @Inject constructor(
    private val jarvisFieldDao: JarvisFieldDao
) {
    fun refreshConfig(fieldsEntities: List<JarvisFieldEntity>) {
        deleteAllFields()

        fieldsEntities.forEach {
            jarvisFieldDao.insertField(it)
        }
    }

    fun deleteAllFields() {
        jarvisFieldDao.deleteAllFields()
    }

    fun getAllFields(): Flow<List<JarvisFieldEntity>> =
        jarvisFieldDao.getAllFields()

    fun getField(name: String): JarvisFieldEntity? =
        jarvisFieldDao.getField(name)

    fun updateField(field: JarvisFieldEntity) =
        jarvisFieldDao.updateField(field)
}
