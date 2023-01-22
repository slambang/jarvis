package com.jarvis.app.domain.usecases

import com.jarvis.app.data.AppJsonMapper
import com.jarvis.app.data.database.dao.JarvisFieldDao
import com.jarvis.app.domain.fields.JarvisFieldRepository
import com.jarvis.client.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateFieldUseCase @Inject constructor(
    private val jsonMapper: AppJsonMapper,
    private val jarvisFieldDao: JarvisFieldDao,
    private val jarvisFieldRepository: JarvisFieldRepository
) {
    suspend operator fun invoke(
        field: JarvisField<*>,
        newValue: Any,
        isPublished: Boolean
    ): Unit = withContext(Dispatchers.IO) {
        val updatedField = updateField(field, newValue, isPublished)
        val fieldGroupName = jarvisFieldDao.getGroup(field.name)
        val entity = jsonMapper.mapToJarvisFieldEntity(fieldGroupName, updatedField)
        jarvisFieldRepository.updateField(entity)
    }

    private fun updateField(
        jarvisField: JarvisField<*>,
        newValue: Any,
        isPublished: Boolean
    ): JarvisField<*> =
        when (jarvisField) {

            is StringField ->
                jarvisField.copy(value = newValue as String, isPublished = isPublished)

            is LongField ->
                jarvisField.copy(value = newValue as Long, isPublished = isPublished)

            is DoubleField ->
                jarvisField.copy(value = newValue as Double, isPublished = isPublished)

            is BooleanField ->
                jarvisField.copy(value = newValue as Boolean, isPublished = isPublished)

            is StringListField ->
                jarvisField.copy(currentSelection = newValue as Int, isPublished = isPublished)
        }
}
