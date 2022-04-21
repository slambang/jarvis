package com.jarvis.app.domain.usecases

import com.jarvis.app.data.AppJsonMapper
import com.jarvis.app.domain.fields.JarvisFieldRepository
import com.jarvis.client.data.JarvisField
import javax.inject.Inject

class GetSingleFieldUseCase @Inject constructor(
    private val jsonMapper: AppJsonMapper,
    private val jarvisFieldRepository: JarvisFieldRepository
) {
    operator fun invoke(fieldName: String): JarvisField<Any>? =
        jarvisFieldRepository.getField(fieldName)?.let {
            jsonMapper.mapJarvisFieldDomain(it)
        }
}
