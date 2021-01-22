package dev.stanislav.testtask.mapper

import com.toxicbakery.logging.Arbor
import dev.stanislav.testtask.entity.EEmployee
import dev.stanislav.testtask.entity.Employee
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class EmployeeMapper(val formatters: List<DateTimeFormatter>) : IMapper<EEmployee, Employee> {

    override fun map(src: EEmployee) = Employee(
        firstName = src.f_name,
        lastName = src.l_name,
        birthday = src.birthday?.let { dateString ->
            try {
                formatters.forEach { formatter ->
                    return@let LocalDate.parse(dateString, formatter)
                }
            } catch (t: Throwable) {
                Arbor.e(t)
            }
            return@let null
        },
        specialtyIds = src.specialty.map { it.specialty_id }
    )

}
