package dev.molleapaza.sprintboot.webflux.app.model.services;


import dev.molleapaza.sprintboot.webflux.app.model.document.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> findAll();
    Flux<Product> findAllWithNameUpperCase();
    Flux<Product> findAllWithNameUpperCaseRepeat();
    Mono<Product> findById(String id);
    Mono<Product> save(Product product);
    Mono<Void> delete(Product product);

}
