package dev.stanislav.testtask.api.response

import com.squareup.moshi.Json
import dev.stanislav.testtask.entity.EEmployee


data class EmployeesResponse(
    @Json(name = "response") val employees: List<EEmployee>
)