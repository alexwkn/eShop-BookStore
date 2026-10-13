package com.eshop.bookstore.dataAccess.entity;

import javax.persistence.*;
import java.math.BigDecimal;



@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;


    @Column(name = "image")
    private String image;


    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @Column(name = "category_id")
    private Integer categoryId;

    protected ProductEntity() {}

    public Integer getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }
    public Integer getCategoryId() { return categoryId; }
    public String getImagePath() { return image; }
    public String getAuthor() { return author; }
}
