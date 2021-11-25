package br.com.alura.dogin.model

import com.google.gson.annotations.SerializedName

/**
 * Data Class to received a Get Requisition
 */
data class DogsResponse(
    @SerializedName("status") var status: String,//Success or fail
    @SerializedName("message") var images: List<String>//Images of Get requisition
)
