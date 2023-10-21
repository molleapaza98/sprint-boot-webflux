package dev.molleapaza.sprintboot.webflux.app.controller;

import dev.molleapaza.sprintboot.webflux.app.model.dao.ProductDao;
import dev.molleapaza.sprintboot.webflux.app.model.document.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping({"/list","/"})
    public String list(Model model){

        Flux<Product> products = productDao.findAll();
        model.addAttribute("products",products);
        model.addAttribute("title", "list product");
        return "list";
    }
}
