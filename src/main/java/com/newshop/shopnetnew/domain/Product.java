package com.newshop.shopnetnew.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    private static final String SEQ_NAME = "product_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    private String name;
    private Double price;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "category_products", joinColumns = @JoinColumn(name = "product_id"),
            inverseForeignKey = @ForeignKey(name = "category_id"))
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "brand_products", joinColumns = @JoinColumn(name = "product_id"),
            inverseForeignKey = @ForeignKey(name = "brand_id"))
    private Brand brand;
}
