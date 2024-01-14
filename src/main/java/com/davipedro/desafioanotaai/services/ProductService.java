package com.davipedro.desafioanotaai.services;

import com.davipedro.desafioanotaai.domain.category.Category;
import com.davipedro.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.davipedro.desafioanotaai.domain.products.Product;
import com.davipedro.desafioanotaai.domain.products.ProductDTO;
import com.davipedro.desafioanotaai.domain.products.exceptions.ProductNotFoundException;
import com.davipedro.desafioanotaai.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private CategoryService categoryService;
    private ProductRepository productRepository;

    public ProductService(CategoryService categoryService, ProductRepository productRepository){
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    //Para a logica de negocio, é necessário existir uma categoria para então criar-se um produto
    //Logo se a categoria passada (productData.categoryId()) não existir, não deve ser possível
    // criar um novo produto
    public Product insert(ProductDTO productData){
        Category category = this.categoryService.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.productRepository.save(newProduct);
        return newProduct;
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    //Não é necessário fazer a verificação da categoria pois, se o produto foi criado
    //e o método que cria o produto já possui a verificação, então basta verificar
    // se o produto existe
    public Product update(String id, ProductDTO productData){
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if (productData.categoryId() != null) {
            this.categoryService.getById(productData.categoryId())
                    .ifPresent(product::setCategory);
        }

        if (!productData.title().isEmpty()) product.setTitle(productData.title());
        if (!productData.description().isEmpty()) product.setDescription(productData.description());
        if (productData.price() != null) product.setPrice(productData.price());

        this.productRepository.save(product);
        return product;
    }

    public void delete(String id){
        Product product = this.productRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.productRepository.delete(product);
    }
}
