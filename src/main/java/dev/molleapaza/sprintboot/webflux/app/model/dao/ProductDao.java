package dev.molleapaza.sprintboot.webflux.app.model.dao;

import dev.molleapaza.sprintboot.webflux.app.model.document.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductDao extends ReactiveMongoRepository<Product, String> {
}
