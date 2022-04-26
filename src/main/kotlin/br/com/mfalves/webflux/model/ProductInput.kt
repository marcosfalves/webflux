package br.com.mfalves.webflux.model

import javax.validation.constraints.NotBlank

data class ProductInput(
    @field:NotBlank(message = "{product.name.not.blank}")
    val name: String
)