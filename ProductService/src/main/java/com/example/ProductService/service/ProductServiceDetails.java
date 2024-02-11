package com.example.ProductService.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.ProductService.exception.ProductNotFoundException;
import com.example.ProductService.exception.UnauthorizedProductUpdateException;
import com.example.ProductService.external.UserClient;
import com.example.ProductService.model.ProductCategory;
import com.example.ProductService.model.ProductDao;
import com.example.ProductService.model.SellerDao;
import com.example.ProductService.model.UserDao;
import com.example.ProductService.repository.ProductRepo;

import io.swagger.annotations.Authorization;

@Service
public class ProductServiceDetails {

	@Autowired
	ProductRepo productRepo;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private UserClient userClient;

	public void addProduct(UserDao user, ProductDao productDao) {

		SellerDao seller = new SellerDao(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail());
		productDao.setSeller(seller);
		productRepo.save(productDao);
	}

	public List<ProductDao> getAllProducts() {
		return productRepo.findAll();
	}

	public void deleteProduct(String pid) {
		productRepo.deleteById(pid);
	}

	public void isRequestValid(String pid, UserDao currentUser) {
		Optional<ProductDao> productDao = productRepo.findById(pid);
		if (!productDao.isPresent()) {
			throw new ProductNotFoundException("Product " + pid + " is not found");
		}
		SellerDao sellerDao = productDao.get().getSeller();
		if (!(currentUser.getRoles().contains("admin") || sellerDao.getUsername().equals(currentUser.getUsername()))) {
			throw new UnauthorizedProductUpdateException("Unauthorised");
		}
	}

	public void updateProduct(String pid, Map<String, Object> fields) {

		Optional<ProductDao> findProduct = productRepo.findById(pid);
		for (Map.Entry<String, Object> entry : fields.entrySet()) {
			if (entry.getKey().equals("category")) {
				findProduct.get().setCategory((ProductCategory.valueOf((String) entry.getValue())));
			} else if (entry.getKey().equals("productname")) {
				findProduct.get().setProductname((String) entry.getValue());
			} else if (entry.getKey().equals("price")) {
				findProduct.get().setPrice(((Integer) entry.getValue()).doubleValue());
			}
		}
		kafkaTemplate.send("product_update", findProduct.get());
		productRepo.save(findProduct.get());
	}
}