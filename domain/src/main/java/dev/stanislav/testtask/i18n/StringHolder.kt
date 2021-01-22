package dev.stanislav.testtask.i18n

import com.toxicbakery.logging.Arbor
import java.io.Serializable
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

class StringHolder(val stringProvider: IStringProvider) : Serializable {

    init {
        this::class.memberProperties.forEach { field: KProperty1<out StringHolder, Any?> ->
            if (field is KMutableProperty<*>) {
                if (field.getter.returnType.jvmErasure.isSubclassOf(String::class)) {
                    stringProvider.getStringResourceByName(field.name)?.let {
                        field.setter.call(this@StringHolder, it)
                    } ?: let {
                        field.setter.call(this@StringHolder, "?${field.name}")
                        Arbor.e("Field error [MISSING]: ${field.name}")
                    }
                }
            }
        }
    }

    lateinit var nav_specialties: String

    lateinit var specialties_retry_message: String
    lateinit var specialties_retry_action: String

    lateinit var specialty_retry_message: String
    lateinit var specialty_retry_action: String
    lateinit var specialty_employee_age_placeholder: String

    lateinit var employee_birthday_placeholder: String
    lateinit var employee_age_placeholder: String
}