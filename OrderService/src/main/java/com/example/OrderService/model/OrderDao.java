package com.example.OrderService.model;

import java.util.List;

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
@Document(collection = "Orders")
public class OrderDao {

	@Id
	private String oid;

	@Size(min = 1)
	private List<ProductDao> products;

	private BuyerSellerDao buyer;

	public OrderDao(List<ProductDao> products, BuyerSellerDao buyer) {
		this.products = products;
		this.buyer = buyer;
	}

}