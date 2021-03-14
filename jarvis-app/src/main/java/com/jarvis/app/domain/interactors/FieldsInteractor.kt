package com.jarvis.app.domain.interactors

import com.jarvis.app.domain.fields.JarvisFieldRepository
import com.jarvis.app.data.AppJsonMapper
import com.jarvis.client.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.InputStream

class FieldsInteractor(
    private val jsonMapper: AppJsonMapper,
    private val jarvisFieldRepository: JarvisFieldRepository,
) {
    // Called by ContentProvider
    fun refreshConfig(inputStream: InputStream): JarvisConfig {
        val jarvisConfig = jsonMapper.readConfig(inputStream)
        val fieldsEntities = jarvisConfig.fields.map(jsonMapper::mapToJarvisFieldEntity)
        jarvisFieldRepository.refreshConfig(fieldsEntities)
        return jarvisConfig
    }

    // Called by ContentProvider
    fun getJarvisField(fieldName: String): JarvisField<Any>? =
        jarvisFieldRepository.getField(fieldName)?.let {
            jsonMapper.mapJarvisFieldDomain(it)
        }

    // Called by ViewModel
    fun getAllFields(): Flow<List<JarvisField<Any>>> =
        jarvisFieldRepository.getAllFields().map {
            it.map(jsonMapper::mapJarvisFieldDomain)
        }

    // Called by ViewModel
    suspend fun deleteAllFields() = withContext(Dispatchers.IO) {
        jarvisFieldRepository.deleteAllFields()
    }

    // Called by ViewModel
    suspend fun onFieldUpdated(
        jarvisField: JarvisField<*>,
        newValue: Any,
        isPublished: Boolean
    ) = withContext(Dispatchers.IO) {
        val updatedDomain = updateDomain(jarvisField, newValue, isPublished)
        val entity = jsonMapper.mapToJarvisFieldEntity(updatedDomain)
        jarvisFieldRepository.updateField(entity)
    }

    private fun updateDomain(
        jarvisField: JarvisField<*>,
        newValue: Any,
        isPublished: Boolean
    ): JarvisField<*> =
        when (jarvisField) {
            is StringField ->
                StringField(
                    jarvisField.type,
                    jarvisField.name,
                    newValue as String,
                    jarvisField.defaultValue,
                    jarvisField.description,
                    isPublished,
                    jarvisField.hint,
                    jarvisField.minLength,
                    jarvisField.maxLength,
                    jarvisField.regex
                )
            is LongField ->
                LongField(
                    jarvisField.type,
                    jarvisField.name,
                    newValue as Long,
                    jarvisField.defaultValue,
                    jarvisField.description,
                    isPublished,
                    jarvisField.hint,
                    jarvisField.min,
                    jarvisField.max,
                    jarvisField.asRange
                )
            is DoubleField ->
                DoubleField(
                    jarvisField.type,
                    jarvisField.name,
                    newValue as Double,
                    jarvisField.defaultValue,
                    jarvisField.description,
                    isPublished,
                    jarvisField.hint,
                    jarvisField.min,
                    jarvisField.max,
                    jarvisField.asRange
                )
            is BooleanField ->
                BooleanField(
                    jarvisField.type,
                    jarvisField.name,
                    newValue as Boolean,
                    jarvisField.defaultValue,
                    jarvisField.description,
                    isPublished
                )
            is StringListField ->
                StringListField(
                    jarvisField.type,
                    jarvisField.name,
                    jarvisField.value,
                    jarvisField.defaultValue,
                    jarvisField.description,
                    isPublished,
                    jarvisField.defaultSelection,
                    newValue as Int
                )
        }
}
