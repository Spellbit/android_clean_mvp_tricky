package dev.stanislav.testtask.api

import dev.stanislav.testtask.api.response.EmployeesResponse
import io.reactivex.Single
import retrofit2.http.GET

interface IDataSource {

    //https://gist.githubusercontent.com/Spellbit/eaba02504a1a2ed18b3941dac57b8995/raw/dee8f2bc057d5f23ab0e92a3c34a04b8f249c630/tt
    @GET("Spellbit/eaba02504a1a2ed18b3941dac57b8995/raw/dee8f2bc057d5f23ab0e92a3c34a04b8f249c630/tt")
    fun getEmployees() : Single<EmployeesResponse>

}