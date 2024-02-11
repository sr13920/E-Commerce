package com.example.OrderService.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDao {

	@NotNull
	private String pid;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ProductCategory category;

	@Size(min = 5, max = 15)
	@NotEmpty(message = "Enter valid product name")
	private String productname;

	@NotNull
	private double price;

	@NotNull
	private BuyerSellerDao seller;

	public ProductDao(@NotNull ProductCategory category,
			@Size(min = 5, max = 15) @NotEmpty(message = "Enter valid product name") String productname,
			@NotNull double price, @NotNull BuyerSellerDao seller) {
		this.category = category;
		this.productname = productname;
		this.price = price;
		this.seller = seller;
	}
}