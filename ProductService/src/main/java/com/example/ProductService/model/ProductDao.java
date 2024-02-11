package com.example.ProductService.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "Products")
public class ProductDao {

	@Id
	private String pid;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ProductCategory category;

	@Size(min = 5, max = 15)
	@NotEmpty(message = "Enter valid product name")
	private String productname;

	@NotNull
	private double price;

	private SellerDao seller;

	public ProductDao(ProductCategory category, String productname, double price, SellerDao seller) {
		this.category = category;
		this.productname = productname;
		this.price = price;
		this.seller = seller;
	}
}