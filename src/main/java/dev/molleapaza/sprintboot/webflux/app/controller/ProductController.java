package dev.molleapaza.sprintboot.webflux.app.controller;

import dev.molleapaza.sprintboot.webflux.app.SprintBootWebfluxApplication;
import dev.molleapaza.sprintboot.webflux.app.model.dao.ProductDao;
import dev.molleapaza.sprintboot.webflux.app.model.document.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(SprintBootWebfluxApplication.class);

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping({"/list","/"})
    public String list(Model model){

        Flux<Product> products = productDao.findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());
                    return product;
                });

        products.subscribe(prod -> log.info(prod.getName()));

        model.addAttribute("products",products);
        model.addAttribute("title", "list product");
        return "list";
    }

    @GetMapping("/list-data-driver")
    public String listDataDriver(Model model){

        Flux<Product> products = productDao.findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());
                    return product;
                }).delayElements(Duration.ofSeconds(1));

        products.subscribe(prod -> log.info(prod.getName()));

        model.addAttribute("products",new ReactiveDataDriverContextVariable(products, 2));
        model.addAttribute("title", "list product");
        return "list";
    }
}
