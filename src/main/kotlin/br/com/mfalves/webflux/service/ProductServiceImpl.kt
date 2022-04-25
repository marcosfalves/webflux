package br.com.mfalves.webflux.service

import br.com.mfalves.webflux.document.Product
import br.com.mfalves.webflux.repository.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProductServiceImpl(val productRepository: ProductRepository) : ProductService {

    override fun findAll(): Flux<Product> = productRepository.findAll()

    override fun findById(id: String): Mono<Product> = productRepository.findById(id)

    override fun save(product: Product): Mono<Product> = productRepository.save(product)

    override fun delete(id: String): Mono<Void> = productRepository.deleteById(id)

}