package com.newshop.shopnetnew.endpoint;

import com.newshop.shopnetnew.dto.ProductDTO;
import com.newshop.shopnetnew.service.ProductService;
import com.newshop.shopnetnew.ws.Products.GetProductsRequest;
import com.newshop.shopnetnew.ws.Products.GetProductsResponse;
import com.newshop.shopnetnew.ws.Products.ProductsWS;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class ProductsEndpoint {
    public static final String NAMESPACE_URL = "http://www.newnetshop.com/shopnetnew/ws/products";
    private ProductService productService;

    public ProductsEndpoint(ProductService productService){
        this.productService = productService;
    }
    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProductWs(@RequestPayload GetProductsRequest request)
            throws DatatypeConfigurationException{
            GetProductsResponse response = new GetProductsResponse();
            productService.getAll()
                    .forEach(product -> response.getProducts().add(createProductWS(product)));
            return response;
    }

    private ProductsWS createProductWS(ProductDTO product) {
        ProductsWS ws = new ProductsWS();
        ws.setId(product.getId());
        ws.setPrice(product.getPrice());
        ws.setName(product.getName());
        ws.setBrand(product.getBrand().getName());
        ws.setCategory(product.getCategory().getName());
        return ws;
    }
}
