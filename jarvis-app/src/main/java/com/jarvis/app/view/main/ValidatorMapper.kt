package com.jarvis.app.view.main

import com.jarvis.app.R
import com.jarvis.app.view.util.ResourceProvider
import com.jarvis.client.data.*
import javax.inject.Inject

class ValidatorMapper @Inject constructor(
    private val resources: ResourceProvider
) {
    fun getStringFieldValidator(field: StringField): (String) -> String? =
        { input ->
            when {
                input.isEmpty() ->
                    resources.getString(R.string.validation_empty_not_allowed)

                field.minLength != null && input.length < field.minLength!! ->
                    resources.getString(
                        R.string.validation_min_length,
                        field.minLength!!
                    )

                field.maxLength != null && input.length > field.maxLength!! ->
                    resources.getString(
                        R.string.validation_max_length,
                        field.maxLength!!
                    )

                field.regex != null && !field.regex!!.toRegex().matches(input) ->
                    resources.getString(
                        R.string.validation_string_regex_failed,
                        field.regex!!
                    )

                else -> null
            }
        }

    fun getLongFieldValidator(field: LongField): (Long) -> String? =
        { input ->
            when {
                field.min != null && input < field.min!! ->
                    resources.getString(R.string.validation_min_value, field.min!!)

                field.max != null && input > field.max!! ->
                    resources.getString(R.string.validation_max_value, field.max!!)

                else -> null
            }
        }

    fun getDoubleFieldValidator(field: DoubleField): (Double) -> String? =
        { input ->
            when {
                field.min != null && input < field.min!! ->
                    resources.getString(R.string.validation_min_value, field.min!!)

                field.max != null && input > field.max!! ->
                    resources.getString(R.string.validation_max_value, field.max!!)

                else -> null
            }
        }

    @Suppress("UNUSED_PARAMETER")
    fun getBooleanFieldValidator(field: BooleanField): (Boolean) -> String? =
        NO_VALIDATION

    @Suppress("UNUSED_PARAMETER")
    fun getStringListFieldValidator(field: StringListField): (List<String>) -> String? =
        NO_VALIDATION

    companion object {
        private val NO_VALIDATION: (Any) -> String? = { null }
    }
}
