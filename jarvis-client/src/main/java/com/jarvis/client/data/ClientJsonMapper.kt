package com.jarvis.client.data

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.lang.RuntimeException

internal class ClientJsonMapper {
    fun mapToJsonString(config: JarvisConfig): String =
        Json.encodeToString(config)
}

object JarvisFieldSerializer :
    JsonContentPolymorphicSerializer<JarvisField<*>>(JarvisField::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out JarvisField<*>> =
        when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            StringField::class.java.simpleName -> StringField.serializer()
            LongField::class.java.simpleName -> LongField.serializer()
            DoubleField::class.java.simpleName -> DoubleField.serializer()
            BooleanField::class.java.simpleName -> BooleanField.serializer()
            StringListField::class.java.simpleName -> StringListField.serializer()
            else -> throw RuntimeException("Unknown JarvisField type: $element")
        }
}
