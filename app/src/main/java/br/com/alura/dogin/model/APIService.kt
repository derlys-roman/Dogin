package br.com.alura.dogin.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getDogsByBrands(@Url url: String): Response<DogsResponse>
}