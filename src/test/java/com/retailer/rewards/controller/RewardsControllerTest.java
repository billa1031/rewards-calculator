package com.retailer.rewards.controller;

import com.retailer.rewards.entity.Customer;
import com.retailer.rewards.model.CustomerRewards;
import com.retailer.rewards.repository.CustomerRepository;
import com.retailer.rewards.service.RewardsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class RewardsControllerTest {

    @Mock
    private RewardsService rewardsService;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private RewardsController rewardsController;

    @Test
    public void getRewardsByCustomerIdShouldReturnBadRequestErrorIfCustomerNotFound(){
        ResponseEntity responseEntity = rewardsController.getRewardsByCustomerId(100l);

        Assert.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }

    @Test
    public void getRewardsByCustomerIdShouldReturnNotNullResponse(){
        Mockito.when(rewardsService.getRewards(any())).thenReturn(new CustomerRewards());
        Mockito.when(customerRepository.findByCustomerId(any())).thenReturn(new Customer());

        ResponseEntity<CustomerRewards> responseEntity = rewardsController.getRewardsByCustomerId(101l);

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody() instanceof CustomerRewards);
        Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}
