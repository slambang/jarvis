package com.jarvis.app.domain.usecases

import com.jarvis.app.data.AppJsonMapper
import com.jarvis.app.domain.fields.JarvisFieldRepository
import com.jarvis.client.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateFieldUseCase @Inject constructor(
    private val jsonMapper: AppJsonMapper,
    private val jarvisFieldRepository: JarvisFieldRepository
) {
    suspend operator fun invoke(
        jarvisField: JarvisField<*>,
        newValue: Any,
        isPublished: Boolean
    ): Unit = withContext(Dispatchers.IO) {
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
