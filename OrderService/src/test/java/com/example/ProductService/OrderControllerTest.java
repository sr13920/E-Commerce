package com.example.ProductService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.OrderService.config.JwtTokenUtil;
import com.example.OrderService.controller.OrderController;
import com.example.OrderService.external.UserClient;
import com.example.OrderService.model.BuyerSellerDao;
import com.example.OrderService.model.ProductCategory;
import com.example.OrderService.model.ProductDao;
import com.example.OrderService.model.UserDao;
import com.example.OrderService.service.OrderServiceDetails;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    @Mock
    OrderServiceDetails orderServiceDetails;
    @Mock
    UserClient userClient;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @InjectMocks
    OrderController orderController;

    private MockMvc mockMvc;

    String token = "valid_token";
    Principal principal = Mockito.mock(Principal.class);
    String username = "swapnil13";
    ProductCategory productCategory;
    ProductDao product;
    UserDao user;
    BuyerSellerDao buyerseller;
    String oid;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        List<String> role = new ArrayList<>();
        role.add("admin");
        user = new UserDao("swapnil13", "swapnil", "rathi", "rathi13", "swapnil@gmail.com", role);
        buyerseller = new BuyerSellerDao("swapnil13", "swapnil", "rathi", "swapnil@gmail.com");
        product = new ProductDao(productCategory.BOOKS, "Pencil", 100.00, buyerseller);
    }

    @Test
    public void placeOrder() throws Exception {

        String s = "{\"products\":[{\"pid\":\"64032e39fe238a15a01981e6\",\"category\":\"ELECTRONICS\",\"productname\":\"pencil\",\"price\":600.0,\"seller\":{\"username\":\"palash10\",\"firstname\":\"palash\",\"lastname\":\"jhanwar\",\"email\":\"palash@gmail.com\"}}]}";
        Mockito.when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
        Mockito.when(userClient.getUser(username, token)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/placeOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getOrders() throws Exception {

        Mockito.when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
        Mockito.when(userClient.getUser(username, token)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/getOrders/" + username)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteOrder() throws Exception {
        Mockito.when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
        Mockito.when(userClient.getUser(username, token)).thenReturn(user);
        // doNothing().when(isRequestValid(pid, user));
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/deleteOrder/" + oid)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateProduct() throws Exception {
        String s = "{\"oid\":\"64039d4ffe238a1b3cf44692\",\"products\":[{\"pid\":\"64032e39fe238a15a01981e6\",\"category\":\"ELECTRONICS\",\"productname\":\"pencil\",\"price\":600.0,\"seller\":{\"username\":\"palash10\",\"firstname\":\"palash\",\"lastname\":\"jhanwar\",\"email\":\"palash@gmail.com\"}},{\"pid\":\"64041d4420250b0424836a2a\",\"category\":\"BOOKS\",\"productname\":\"Pencil\",\"price\":100.0,\"seller\":{\"username\":\"palash10\",\"firstname\":\"palash\",\"lastname\":\"jhanwar\",\"email\":\"palash@gmail.com\"}}]}";
        Mockito.when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
        Mockito.when(userClient.getUser(username, token)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/updateOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
