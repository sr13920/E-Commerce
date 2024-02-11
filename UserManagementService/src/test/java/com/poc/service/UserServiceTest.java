package com.poc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import com.poc.exception.InvalidRoleNameException;
import com.poc.model.UserDao;
import com.poc.repository.UserRepo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserServiceTest {

    private UserDao user;

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Before
    public void setUp() {
        List<String> role = new ArrayList<>();
        role.add("user");
        user = new UserDao("swapnil13", "swapnil", "rathi", "rathi13", "swapnil@gmail.com", role);
    }

    @Test
    public void testCreateUser() {
        when(userRepo.save(any(UserDao.class))).thenReturn(user);
        UserDao user1 = jwtUserDetailsService.createUser(user);
        assertEquals(user.getUsername(), user1.getUsername());
    }

    @Test
    public void testDeleteUser(){
        when(userRepo.findByUsername(user.getUsername())).thenReturn(user);    
        jwtUserDetailsService.deleteUser(user.getUsername());
        verify(userRepo, times(1)).delete(user);
    }

    @Test
    public void testGetUser(){
        when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        assertEquals(user, jwtUserDetailsService.getUser(user.getUsername()));
    }

    @Test
    public void testUpdateUser(){
        when(userRepo.save(any(UserDao.class))).thenReturn(user);
        Map<String,Object> hp=new HashMap<>();
        hp.put("firstname", "swapnil");
        hp.put("lastname", "rathi");
        when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        UserDao updatedUser = jwtUserDetailsService.updateUser(user.getUsername(),hp);
        verify(userRepo, times(1)).save(updatedUser);
        assertEquals(user.getFirstname(), updatedUser.getFirstname());
    }

    @Test
    public void testUpdateUserRole() throws InvalidRoleNameException{
        when(userRepo.save(any(UserDao.class))).thenReturn(user);
        List<String> roles=new ArrayList<>();
        roles.add("user");
        try{
            when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
            UserDao updatedUserRole = jwtUserDetailsService.updateUserRole(user.getUsername(),roles);
            assertEquals(user.getRoles(), updatedUserRole.getRoles());
        }catch(Exception ex){
            throw new InvalidRoleNameException("Please enter valid role"); 
        }
        
    }

}
