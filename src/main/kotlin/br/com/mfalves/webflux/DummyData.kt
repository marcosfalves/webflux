package br.com.mfalves.webflux

import br.com.mfalves.webflux.document.Product
import br.com.mfalves.webflux.repository.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.*

@Component
class DummyData(val productRepository: ProductRepository): CommandLineRunner {

    override fun run(vararg args: String?) {
        productRepository.deleteAll()
            .thenMany(Flux.just("Smartphone Samsung M52 5G", "Notebook Dell Inspiron 15.6\"", "Apple Macbook M1 13\"",
                                "Headphone JBL", "Router TP-Link Archer C6")
                .map{ Product(UUID.randomUUID().toString(), it) }
                .flatMap(productRepository::save)
            ).subscribe { println(it) }
    }

}