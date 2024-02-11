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

import com.example.ProductService.config.JwtTokenUtil;
import com.example.ProductService.controller.ProductController;
import com.example.ProductService.external.UserClient;
import com.example.ProductService.model.ProductCategory;
import com.example.ProductService.model.ProductDao;
import com.example.ProductService.model.SellerDao;
import com.example.ProductService.model.UserDao;
import com.example.ProductService.service.ProductServiceDetails;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
    @Mock
    ProductServiceDetails productServiceDetails;
    @Mock
    UserClient userClient;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @InjectMocks
    ProductController productController;

    private MockMvc mockMvc;

    String token = "valid_token";
    Principal principal = Mockito.mock(Principal.class);
    String username = "swapnil13";
    ProductCategory productCategory;
    ProductDao product;
    UserDao user;
    SellerDao seller;
    String pid;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        List<String> role = new ArrayList<>();
        role.add("admin");
        user = new UserDao("swapnil13", "swapnil", "rathi", "rathi13", "swapnil@gmail.com", role);
        seller = new SellerDao("swapnil13", "swapnil", "rathi", "swapnil@gmail.com");
        product = new ProductDao(productCategory.BOOKS, "Pencil", 100.00, seller);
    }

    @Test
    public void addProduct() throws Exception {

        String s = "{\"category\":\"BOOKS\",\"productname\":\"Pencil\",\"price\":100.00}";
        Mockito.when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
        Mockito.when(userClient.getUser(username, token)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/addProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
                .principal(principal)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getProducts() throws Exception {

        Mockito.when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
        Mockito.when(userClient.getUser(username, token)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/getProducts")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteProduct() throws Exception {
        Mockito.when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
        Mockito.when(userClient.getUser(username, token)).thenReturn(user);
        // doNothing().when(isRequestValid(pid, user));
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/deleteProduct?pid=" + pid)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateProduct() throws Exception {
        String s = "{\"category\":\"BOOKS\",\"productname\":\"Pencil\",\"price\":100.00}";
        Mockito.when(jwtTokenUtil.getUsernameFromToken(token)).thenReturn(username);
        Mockito.when(userClient.getUser(username, token)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/updateProduct?pid=" + pid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
                .principal(principal)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
