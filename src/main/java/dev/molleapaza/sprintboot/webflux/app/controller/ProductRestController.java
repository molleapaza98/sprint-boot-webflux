package dev.molleapaza.sprintboot.webflux.app.controller;

import dev.molleapaza.sprintboot.webflux.app.SprintBootWebfluxApplication;
import dev.molleapaza.sprintboot.webflux.app.model.dao.ProductDao;
import dev.molleapaza.sprintboot.webflux.app.model.document.Product;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private static final Logger log = LoggerFactory.getLogger(SprintBootWebfluxApplication.class);

    private final ProductDao productDao;

    public ProductRestController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public Flux<Product> index() {
        return productDao.findAll().map(product -> {
            product.setName(product.getName().toUpperCase());
            return product;
        }).doOnNext(product -> log.info(product.getName()));
    }

    @GetMapping("/{id}")
    public Mono<Product> show(@PathVariable String id) {
        Flux<Product> products = productDao.findAll();
        return products.filter(product -> product.getId().equals(id))
                .next()
                .doOnNext(product -> log.info(product.getName()));
    }
}
