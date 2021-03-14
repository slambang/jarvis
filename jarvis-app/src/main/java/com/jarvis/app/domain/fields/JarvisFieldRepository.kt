package com.jarvis.app.domain.fields

import com.jarvis.app.data.fields.JarvisFieldDao
import com.jarvis.app.data.fields.JarvisFieldEntity
import kotlinx.coroutines.flow.Flow

class JarvisFieldRepository(
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
