package br.com.mfalves.webflux.repository

import br.com.mfalves.webflux.document.Product
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ProductRepository: ReactiveMongoRepository<Product,String> {

}