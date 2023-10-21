package dev.molleapaza.sprintboot.webflux.app.model.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private Double price;
    private Date createAt;

    public Product() {}

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}
