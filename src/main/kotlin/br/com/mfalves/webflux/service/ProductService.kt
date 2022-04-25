package br.com.mfalves.webflux.service

import br.com.mfalves.webflux.document.Product
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductService {

    fun findAll(): Flux<Product>
    fun findById(id: String): Mono<Product>
    fun save(product: Product): Mono<Product>
    fun delete(id: String): Mono<Void>
}