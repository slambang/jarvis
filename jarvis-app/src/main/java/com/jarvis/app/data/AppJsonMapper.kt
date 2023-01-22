package com.jarvis.app.data

import com.jarvis.app.data.database.entity.JarvisFieldEntity
import com.jarvis.app.data.database.entity.JarvisGroupEntity
import com.jarvis.client.data.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.*
import java.io.InputStream
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class AppJsonMapper @Inject constructor() {

    fun readConfig(inputStream: InputStream): JarvisConfig =
        Json.decodeFromStream(inputStream)

    @Suppress("UNCHECKED_CAST")
    fun mapJarvisFieldDomain(jarvisFieldEntity: JarvisFieldEntity): JarvisField<Any> =
        Json.decodeFromString(JarvisInternalFieldSerializer, jarvisFieldEntity.jsonModel) as JarvisField<Any>

    fun mapToJarvisGroupEntity(
        jarvisGroup: JarvisConfigGroup
    ): JarvisGroupEntity =
        JarvisGroupEntity(
            name = jarvisGroup.name,
            isCollapsable = jarvisGroup.isCollapsable,
            startCollapsed = jarvisGroup.startCollapsed
        )

    fun mapToJarvisFieldEntity(
        groupName: String,
        jarvisField: JarvisField<*>
    ): JarvisFieldEntity =
        JarvisFieldEntity(
            name = jarvisField.name,
            groupName = groupName,
            jsonModel = jarvisField.toJson()
        )
}
