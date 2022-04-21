package com.jarvis.app.domain.usecases

import com.jarvis.app.domain.fields.JarvisFieldRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAllConfigsUseCase @Inject constructor(
    private val jarvisFieldRepository: JarvisFieldRepository
) {
    suspend operator fun invoke(): Unit =
        withContext(Dispatchers.IO) {
            jarvisFieldRepository.deleteAllFields()
        }
}
