package com.example.msa.practice.product.products.redis;

import com.example.msa.practice.product.core.Product;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("product")
public class ProductRedis {

    @Id
    private Long id;
    private Integer quantity;

    public ProductRedis(Product product) {
        this.id = product.getId();
        this.quantity = product.getQuantity();
    }
}
