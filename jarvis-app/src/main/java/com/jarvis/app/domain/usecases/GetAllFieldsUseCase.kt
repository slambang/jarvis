package com.jarvis.app.domain.usecases

import com.jarvis.app.data.AppJsonMapper
import com.jarvis.app.domain.fields.JarvisFieldRepository
import com.jarvis.client.data.JarvisField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllFieldsUseCase @Inject constructor(
    private val jsonMapper: AppJsonMapper,
    private val jarvisFieldRepository: JarvisFieldRepository
) {
    suspend operator fun invoke(): Flow<List<JarvisField<Any>>> =
        withContext(Dispatchers.IO) {
            jarvisFieldRepository.getAllFields().map {
                it.map(jsonMapper::mapJarvisFieldDomain)
            }
        }
}
