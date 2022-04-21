package com.jarvis.app.domain.usecases

import com.jarvis.app.data.AppJsonMapper
import com.jarvis.app.domain.fields.JarvisFieldRepository
import com.jarvis.client.data.JarvisConfig
import java.io.InputStream
import javax.inject.Inject

class RefreshConfigUseCase @Inject constructor(
    private val jsonMapper: AppJsonMapper,
    private val jarvisFieldRepository: JarvisFieldRepository
) {
    operator fun invoke(inputStream: InputStream): JarvisConfig {
        val jarvisConfig = jsonMapper.readConfig(inputStream)
        val fieldsEntities = jarvisConfig.fields.map(jsonMapper::mapToJarvisFieldEntity)
        jarvisFieldRepository.refreshConfig(fieldsEntities)
        return jarvisConfig
    }
}
