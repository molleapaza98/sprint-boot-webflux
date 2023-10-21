package dev.molleapaza.sprintboot.webflux.app;

import dev.molleapaza.sprintboot.webflux.app.model.dao.ProductDao;
import dev.molleapaza.sprintboot.webflux.app.model.document.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SprintBootWebfluxApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SprintBootWebfluxApplication.class);

    private final ProductDao productDao;

    private final ReactiveMongoTemplate mongoTemplate;

    public SprintBootWebfluxApplication(ProductDao productDao, ReactiveMongoTemplate mongoTemplate) {
        this.productDao = productDao;
        this.mongoTemplate = mongoTemplate;
    }


    public static void main(String[] args) {
        SpringApplication.run(SprintBootWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        mongoTemplate.dropCollection("products").subscribe();

        Flux.just(new Product("TV Panasonic screen LCD",456.89),
                new Product("Sony Camara HD Digital",177.89),
                new Product("Apple Ipod",46.89),
                new Product("Sony Notebook",846.89),
                new Product("Hewlett Packard Multifunctional",200.89),
                new Product("Bianchi Bicycle",70.89),
                new Product("HP Notebook Omen 17",2500.89),
                new Product("Apple TV 4K",2255.89),
                new Product("TV Sony Bravia Oled 4k Ultra HD",150.89)
        ).flatMap(product -> {
                    product.setCreateAt(new Date());
                    return productDao.save(product);
                })
        .subscribe(productMono -> log.info("Insert: " + productMono.getId() + productMono.getName()));
    }
}
