package dev.stanislav.testtask.repo

import com.toxicbakery.logging.Arbor
import dev.stanislav.testtask.api.IDataSource
import dev.stanislav.testtask.entity.EEmployee
import dev.stanislav.testtask.entity.ESpecialty
import dev.stanislav.testtask.entity.Employee
import dev.stanislav.testtask.entity.Specialty
import dev.stanislav.testtask.mapper.IMapper
import dev.stanislav.testtask.network.INetworkStatus
import dev.stanislav.testtask.storage.IEmployeesStorage
import dev.stanislav.testtask.storage.ISpecialtiesStorage
import dev.stanislav.testtask.storage.room.entity.StorageEmployee
import dev.stanislav.testtask.storage.room.entity.StorageEmployeeSpecialty
import dev.stanislav.testtask.storage.room.entity.StorageSpecialty
import dev.stanislav.testtask.storage.room.mapper.IStorageMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SpecialtiesRepo(
    val networkStatus: INetworkStatus,
    val api: IDataSource,
    val specialtiesMapper: IMapper<ESpecialty, Specialty>,
    val employeesMapper: IMapper<EEmployee, Employee>,
    val specialtiesStorage: ISpecialtiesStorage,
    val employeesStorage: IEmployeesStorage,
    val specialtiesStorageMapper: IStorageMapper<Specialty, StorageSpecialty>,
    val employeesStorageMapper: IStorageMapper<Employee, StorageEmployee>
) : ISpecialtiesRepo {

    override fun getSpecialties() = networkStatus.isOnline().let { isOnline ->
        if (isOnline) {
            api.getEmployees()
                .flatMap { response ->
                    val employees: List<Employee> = response.employees.map { employeesMapper.map(it) }
                    val specialties: List<Specialty> = response.employees
                        .map { it.specialty }
                        .flatten()
                        .distinctBy { it.specialty_id }
                        .map { specialtiesMapper.map(it) }

                    val storageSpecialties = specialties.map { specialtiesStorageMapper.mapTo(it) }.filterNotNull()

                    val storageEmployees = mutableListOf<StorageEmployee>()
                    val storageEmployeesSpecialties = mutableListOf<StorageEmployeeSpecialty>()

                    employees.forEach { employee ->
                        employeesStorageMapper.mapTo(employee)?.let { storageEmployee ->
                            employee.specialtyIds.forEach { specialtyId ->
                                storageEmployees.add(storageEmployee)
                                storageEmployeesSpecialties.add(
                                    StorageEmployeeSpecialty(
                                        specialtyId,
                                        employee.firstName,
                                        employee.lastName,
                                    )
                                )
                            }
                        }
                    }

                    return@flatMap specialtiesStorage.putSpecialties(storageSpecialties)
                        .andThen(employeesStorage.putEmployees(storageEmployees))
                        .andThen(specialtiesStorage.putEmployeesSpecialties(storageEmployeesSpecialties))
                        .andThen(Single.just(specialties))
                }
        } else {
            specialtiesStorage.getSpecialties().map { storageSpecialties ->
                storageSpecialties.map { specialtiesStorageMapper.mapFrom(it) }
            }
        }
    }.subscribeOn(Schedulers.io())

    override fun getSpecialtiesLocal(ids: List<String>) = specialtiesStorage.getSpecialties(ids)
        .map { storageSpecialties ->
            storageSpecialties.map { specialtiesStorageMapper.mapFrom(it) }
        }


    override fun getSpecialtyLocal(id: String) = specialtiesStorage.getSpecialty(id)
        .map { storageSpecialty ->
            specialtiesStorageMapper.mapFrom(storageSpecialty)
        }.subscribeOn(Schedulers.io())
}