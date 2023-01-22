package com.jarvis.client.data

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.lang.IllegalArgumentException

fun <T> JarvisField<T>.toJson(): String =
    Json.encodeToString(JarvisInternalFieldSerializer, this)

fun JarvisConfig.toJson(): String =
    Json.encodeToString(this)

/**
 * This is an internal class shared with the Jarvis App. Do not use.
 */
/* internal */ object JarvisInternalFieldSerializer :
    JsonContentPolymorphicSerializer<JarvisField<*>>(JarvisField::class) {

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out JarvisField<*>> =
        when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            StringField::class.java.simpleName -> StringField.serializer()
            LongField::class.java.simpleName -> LongField.serializer()
            DoubleField::class.java.simpleName -> DoubleField.serializer()
            BooleanField::class.java.simpleName -> BooleanField.serializer()
            StringListField::class.java.simpleName -> StringListField.serializer()
            else -> throw IllegalArgumentException("Invalid field type: $element")
        }
}
