package com.example.OrderService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OrderService.exception.OrderNotFoundException;
import com.example.OrderService.exception.UnauthorizedUpdateException;
import com.example.OrderService.external.UserClient;
import com.example.OrderService.model.OrderDao;
import com.example.OrderService.model.BuyerSellerDao;
import com.example.OrderService.model.UserDao;
import com.example.OrderService.repository.OrderRepo;

@Service
public class OrderServiceDetails {

	@Autowired
	OrderRepo orderRepo;

	public void isRequestValid(String oid, UserDao currentUser) {
		Optional<OrderDao> orderDao = orderRepo.findById(oid);
		if (!orderDao.isPresent()) {
			throw new OrderNotFoundException("Order with " + oid + " is not found");
		}
		BuyerSellerDao buyersellerDao = orderDao.get().getBuyer();
		if (!(currentUser.getRoles().contains("admin")
				|| buyersellerDao.getUsername().equals(currentUser.getUsername()))) {
			throw new UnauthorizedUpdateException("Unauthorised User");
		}
	}

	public void placeOrder(OrderDao orderDao, UserDao user) {
		BuyerSellerDao buyer = new BuyerSellerDao(user.getUsername(), user.getFirstname(), user.getLastname(),
				user.getEmail());
		orderDao.setBuyer(buyer);
		orderRepo.save(orderDao);
	}

	public List<OrderDao> getOrders(String buyer, UserDao currentUser) {
		if (!(currentUser.getRoles().contains("admin")
				|| buyer.equals(currentUser.getUsername()))) {
			throw new UnauthorizedUpdateException("Unauthorised User");
		}
		return orderRepo.findOrdersByUsername(buyer);
	}

	public void updateOrder(OrderDao order) {
		Optional<OrderDao> updatedOrder = orderRepo.findById(order.getOid());
		updatedOrder.get().setProducts(order.getProducts());
		orderRepo.save(updatedOrder.get());
	}

	public void deleteOrder(String oid) {
		orderRepo.deleteById(oid);
	}
}