package com.example.OrderService.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.OrderService.config.JwtTokenUtil;
import com.example.OrderService.external.UserClient;
import com.example.OrderService.model.OrderDao;
import com.example.OrderService.model.UserDao;
import com.example.OrderService.service.OrderServiceDetails;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private OrderServiceDetails orderServiceDetails;

	@Autowired
	private UserClient userClient;

	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	public ResponseEntity<?> placeOrder(@RequestHeader("Authorization") String authorizationHeader,
			@Valid @RequestBody OrderDao orderDao)
			throws Exception {

		String username = jwtTokenUtil.getUsernameFromToken(authorizationHeader.substring(7));
		UserDao user = userClient.getUser(username, authorizationHeader);
		orderServiceDetails.placeOrder(orderDao, user);

		return ResponseEntity.ok("Order Placed");
	}

	@RequestMapping(value = "/getOrders/{buyerUsername}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrders(@PathVariable(name = "buyerUsername") String buyer,
			@RequestHeader("Authorization") String authorizationHeader)
			throws Exception {

		String username = jwtTokenUtil.getUsernameFromToken(authorizationHeader.substring(7));
		UserDao user = userClient.getUser(username, authorizationHeader);
		List<OrderDao> orders = orderServiceDetails.getOrders(buyer, user);

		return ResponseEntity.ok(orders);
	}

	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
	public ResponseEntity<?> getOrders(@Valid @RequestBody OrderDao order,
			@RequestHeader("Authorization") String authorizationHeader)
			throws Exception {

		String username = jwtTokenUtil.getUsernameFromToken(authorizationHeader.substring(7));
		UserDao user = userClient.getUser(username, authorizationHeader);
		orderServiceDetails.isRequestValid(order.getOid(), user);
		orderServiceDetails.updateOrder(order);
		return ResponseEntity.ok("Order Updated Successfully");
	}

	@RequestMapping(value = "/deleteOrder/{oid}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOrder(@PathVariable(name = "oid") String oid,
			@RequestHeader("Authorization") String authorizationHeader)
			throws Exception {

		String username = jwtTokenUtil.getUsernameFromToken(authorizationHeader.substring(7));
		UserDao user = userClient.getUser(username, authorizationHeader);
		orderServiceDetails.isRequestValid(oid, user);
		orderServiceDetails.deleteOrder(oid);
		return ResponseEntity.ok("Order deleted Successfully");
	}

}