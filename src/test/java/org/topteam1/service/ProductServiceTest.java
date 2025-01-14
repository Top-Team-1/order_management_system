package org.topteam1.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.topteam1.Exceptions.ProductNotFoundException;
import org.topteam1.model.*;
import org.topteam1.repository.ProductRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private ProductService productService;
    private Path newFilePath;



    @BeforeEach
    void setUp() throws IOException {
       newFilePath = Path.of("src/test/java/org/topteam1/products_test.txt");
       Files.createFile(newFilePath);

        ProductRepository productRepository = new ProductRepository(newFilePath.toString());
        productService = new ProductService(productRepository);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(newFilePath);
        Files.deleteIfExists(Path.of("src/test/java/org/topteam1/products_test.txt_id"));
    }

    @Test
    void addProduct_Successful_Addition_Product() {
        //given
        ProductCategory productCategory = ProductCategory.getProductByNumber(1);
        //when
        Product product = productService.addProduct("iPhone", 129999, productCategory);
        //then
        assertNotNull(product);
        assertEquals("iPhone", product.getName());
        assertEquals(129999, product.getPrice());
        assertEquals(productCategory, product.getCategory());
        assertEquals(1, productService.getAllProducts().size());
    }

    @Test
    void getAllProducts_Show_Return_ProductList() {
        //given
        ProductCategory productCategory = ProductCategory.getProductByNumber(2);
        productService.addProduct("TV", 19999, productCategory);
        //when
        List<Product> products = productService.getAllProducts();
        //then
        assertEquals("TV", products.get(0).getName());
    }

    @Test
    void getProductById_Show_Return_Product() {
        //given
        ProductCategory productCategory = ProductCategory.SMARTPHONE;
        Product createProduct = productService.addProduct("Samsung A2", 18888,productCategory);
        createProduct.setId(1L);
        //when
        Product foundProduct = productService.getProductById(Math.toIntExact(createProduct.getId()));
        //then
        assertNotNull(foundProduct);
        assertEquals(createProduct.getId(), foundProduct.getId());
        assertEquals("Samsung A2", foundProduct.getName());
    }

    @Test
    void getProductById_Show_ThrowException_ProductNotFound(){
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(99));
    }

    @Test
    void calculateDiscount() {
        //given
        Product newProduct = productService.addProduct("Nokia", 1000, ProductCategory.SMARTPHONE);
        Customer customer = new Customer(null,"Вася");
        customer.setCustomerType(CustomerType.VIP);
        Discount.getDiscountValue(customer.getCustomerType());
        //when
        productService.calculateDiscount(newProduct, customer);
        //then
        assertEquals(900, newProduct.getPrice());
    }
}