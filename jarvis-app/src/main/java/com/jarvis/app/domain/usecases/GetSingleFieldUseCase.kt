package com.jarvis.app.domain.usecases

import com.jarvis.app.data.AppJsonMapper
import com.jarvis.app.data.database.dao.JarvisFieldDao
import com.jarvis.client.data.JarvisField
import javax.inject.Inject

class GetSingleFieldUseCase @Inject constructor(
    private val jsonMapper: AppJsonMapper,
    private val jarvisFieldDao: JarvisFieldDao
) {
    operator fun invoke(fieldName: String): JarvisField<Any>? =
        jarvisFieldDao.getField(fieldName)?.let {
            jsonMapper.mapJarvisFieldDomain(it)
        }
}
