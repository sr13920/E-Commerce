package com.poc.service;

import com.poc.config.JwtTokenUtil;
import com.poc.controller.JwtAuthenticationController;
import com.poc.controller.UserController;
import com.poc.model.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
        @InjectMocks
        JwtAuthenticationController jwtAuthenticationController;

        @InjectMocks
        UserController userController;

        @Mock
        JwtUserDetailsService userService;

        @Mock
        JwtTokenUtil jwtTokenUtil;

        @Mock
        AuthenticationManager authenticationManager;

        private MockMvc mockMvc1;

        private MockMvc mockMvc2;

        UserDao temp;
        String token = "valid_token";
        Principal principal = Mockito.mock(Principal.class);
        String username = "swapnil13";

        @Before
        public void setup() {

                mockMvc1 = MockMvcBuilders.standaloneSetup(jwtAuthenticationController).build();
                mockMvc2 = MockMvcBuilders.standaloneSetup(userController).build();
                List<String> role = new ArrayList<>();
                role.add("admin");
                temp = new UserDao("swapnil13", "swapnil", "rathi", "rathi13", "swapnil@gmail.com", role);

        }

        @Test
        public void testSignup() throws Exception {

                String s = "{\n" +
                                "    \"username\":\"hardik14\",\n" +
                                "    \"firstname\":\"hardik\",\n" +
                                "    \"lastname\":\"singhavi\",\n" +
                                "    \"password\":\"singhavi14\",\n" +
                                "    \"email\":\"hardik@gmail.com\",\n" +
                                "    \"roles\":[\"user\"]\n" +
                                "}";

                Mockito.when(userService.createUser(any(UserDao.class))).thenReturn(temp);
                mockMvc1.perform(MockMvcRequestBuilders
                                .post("/api/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                                .andExpect(status().isOk())
                                .andDo(print());
        }

        @Test
        public void testLogin() throws Exception {

                String s1 = "{\"username\":\"swapnil13\",\"password\":\"rathi13\"}";

                Mockito.when(authenticationManager.authenticate(any(Authentication.class)))
                                .thenReturn(new UsernamePasswordAuthenticationToken("swapnil13", "rathi13"));

                Mockito.when(jwtTokenUtil.generateToken(anyString())).thenReturn("token");

                mockMvc1.perform(MockMvcRequestBuilders
                                .post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s1))
                                .andExpect(status().isOk())
                                .andDo(print());

        }

        @Test
        public void testdeleteUser() throws Exception {
                Mockito.when(userService.getUser(anyString())).thenReturn(temp);
                Mockito.when(principal.getName()).thenReturn("swapnil13");

                // Mockito.when(temp.getRoles()).thenReturn("admin");

                mockMvc2.perform(MockMvcRequestBuilders
                                .delete("/api/deleteUser?username=" + username)
                                .principal(principal)
                                .header("Authorization", "Bearer " + token))
                                .andExpect(status().isOk())
                                .andDo(print());
        }

        @Test
        public void testUpdateUser() throws Exception {

                String s1 = "{\"firstname\":\"hardik\",\"lastname\":\"singhavi\",\"email\":\"hardik@gmail.com\"}";

                Mockito.when(userService.getUser(anyString())).thenReturn(temp);
                Mockito.when(principal.getName()).thenReturn("swapnil13");

                mockMvc2.perform(MockMvcRequestBuilders
                                .post("/api/updateUser?username=" + username)
                                .principal(principal)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s1)
                                .header("Authorization", "Bearer" + token))
                                .andExpect(status().isOk())
                                .andDo(print());

        }

        @Test
        public void testGetUser() throws Exception {

                Mockito.when(userService.getUser(anyString())).thenReturn(temp);
                mockMvc2.perform(MockMvcRequestBuilders
                                .get("/api/getUser?username=+username")
                                .header("Authorization", "Bearer" + token))
                                .andExpect(status().isOk());
        }
}
