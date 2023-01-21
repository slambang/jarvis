package com.jarvis.client.data

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.lang.IllegalArgumentException

internal class ClientJsonMapper {
    fun mapToJsonString(config: JarvisConfig): String =
        Json.encodeToString(config)
}

// Shared with the Jarvis App.
/* internal */ object JarvisFieldSerializer :
    JsonContentPolymorphicSerializer<JarvisField<*>>(JarvisField::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out JarvisField<*>> =
        when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            StringField::class.java.simpleName -> StringField.serializer()
            LongField::class.java.simpleName -> LongField.serializer()
            DoubleField::class.java.simpleName -> DoubleField.serializer()
            BooleanField::class.java.simpleName -> BooleanField.serializer()
            StringListField::class.java.simpleName -> StringListField.serializer()
            else -> throw IllegalArgumentException("Unknown JarvisField type: $element")
        }
}
