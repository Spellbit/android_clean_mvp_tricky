package dev.stanislav.testtask.di.module

import dagger.Module
import dagger.Provides
import dev.stanislav.testtask.entity.EEmployee
import dev.stanislav.testtask.entity.ESpecialty
import dev.stanislav.testtask.entity.Employee
import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.mapper.EmployeeMapper
import dev.stanislav.testtask.mapper.IMapper
import dev.stanislav.testtask.mapper.SpecialtiesMapper
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Singleton

@Module
class MapperModule {

    @Singleton
    @Provides
    fun specialtiesMapper(): IMapper<ESpecialty, Specialty> = SpecialtiesMapper()

    @Singleton
    @Provides
    fun employeesMapper(formats: List<DateTimeFormatter>): IMapper<EEmployee, Employee> = EmployeeMapper(formats)

}