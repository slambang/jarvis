package com.jarvis.client.data

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.lang.IllegalArgumentException

/**
 * This is an internal file shared with the Jarvis App. Do not use.
 */

/* internal */ fun <T> JarvisField<T>.toJson(): String =
    Json.encodeToString(JarvisInternalFieldSerializer, this)

/* internal */ fun JarvisConfig.toJson(): String =
    Json.encodeToString(this)

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
