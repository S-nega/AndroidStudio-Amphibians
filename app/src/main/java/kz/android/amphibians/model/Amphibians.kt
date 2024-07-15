package kz.android.amphibians.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Amphibians(
    @SerialName(value = "img_src") val imgSrc: String,
    val name: String,
    val type: String,
    val description: String
)