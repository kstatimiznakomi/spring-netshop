package com.newshop.shopnetnew.dto;

import com.newshop.shopnetnew.domain.Brand;
import com.newshop.shopnetnew.domain.Category;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private Brand brand;
    private Category category;
    private String img;
}
