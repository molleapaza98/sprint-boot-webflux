package dev.molleapaza.sprintboot.webflux.app.controller;

import dev.molleapaza.sprintboot.webflux.app.SprintBootWebfluxApplication;
import dev.molleapaza.sprintboot.webflux.app.model.document.Product;
import dev.molleapaza.sprintboot.webflux.app.model.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.monitor.MonitorNotification;
import java.time.Duration;

@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(SprintBootWebfluxApplication.class);

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping({"/list","/"})
    public String list(Model model){

        Flux<Product> products = service.findAllWithNameUpperCase();

        products.subscribe(prod -> log.info(prod.getName()));

        model.addAttribute("products",products);
        model.addAttribute("title", "list product");
        return "list";
    }

    @GetMapping("/form")
    public Mono<String> create(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("title", "form product");
        return Mono.just("form");
    }

    @PostMapping("/form")
    public Mono<String> save(Product product){
        return service.save(product).doOnNext(
                prod -> log.info("product saved: " + prod.getName() + " id: " + prod.getId())
        ).thenReturn("redirect:/list");
    }

    @GetMapping("/list-data-driver")
    public String listDataDriver(Model model){

        Flux<Product> products = service.findAllWithNameUpperCase().delayElements(Duration.ofSeconds(1));

        products.subscribe(prod -> log.info(prod.getName()));

        model.addAttribute("products",new ReactiveDataDriverContextVariable(products, 2));
        model.addAttribute("title", "list product");
        return "list";
    }

    @GetMapping("/list-full")
    public String listFull(Model model){

        Flux<Product> products = service.findAllWithNameUpperCaseRepeat();

        model.addAttribute("products",products);
        model.addAttribute("title", "list product");
        return "list";
    }

    @GetMapping("/list-chunked")
    public String listChunked(Model model){

        Flux<Product> products = service.findAllWithNameUpperCaseRepeat();

        model.addAttribute("products",products);
        model.addAttribute("title", "list product");
        return "list-chunked";
    }
}
