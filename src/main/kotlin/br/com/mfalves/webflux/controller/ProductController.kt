package br.com.mfalves.webflux.controller

import br.com.mfalves.webflux.document.Product
import br.com.mfalves.webflux.model.ProductInput
import br.com.mfalves.webflux.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController(val productService: ProductService) {

    @GetMapping
    fun getProducts(): Flux<Product> {
        return productService.findAll()
    }

    @GetMapping("/{productId}")
    fun readProduct(@PathVariable productId:String): Mono<Product>{
        return productService.findById(productId)
            .switchIfEmpty(
                Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Product not Found"))
            )
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody productInput:ProductInput): Mono<Product> {
        val product = Product(UUID.randomUUID().toString(), productInput.name)
        return productService.save(product)
    }

    @PutMapping("/{productId}")
    fun updateProduct(@PathVariable productId:String,
                      @RequestBody productInput: ProductInput): Mono<Product>{

        return productService.findById(productId)
            .switchIfEmpty(
                Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Product not Found"))
            )
            .flatMap { productService.save(Product(it.id, productInput.name)) }
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable productId:String): Mono<Void> {
        return productService.findById(productId)
            .switchIfEmpty(
            Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Product not Found"))
        ).flatMap{ productService.delete(it.id) }
    }

    @GetMapping(value = ["/events"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProductByEvents(): Flux<Tuple2<Long,Product>> {

        val interval = Flux.interval(Duration.ofSeconds(5))
        val events   = productService.findAll()

        println("Registrou evento: ${LocalDateTime.now()}")

        return Flux.zip(interval, events)
    }

}