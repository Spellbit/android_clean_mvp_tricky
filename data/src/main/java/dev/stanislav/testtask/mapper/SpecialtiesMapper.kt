package dev.stanislav.testtask.mapper

import dev.stanislav.testtask.entity.ESpecialty
import dev.stanislav.testtask.entity.Specialty

class SpecialtiesMapper : IMapper<ESpecialty, Specialty> {
    override fun map(src: ESpecialty) = Specialty(
        id = src.specialty_id,
        name = src.name
    )
}
