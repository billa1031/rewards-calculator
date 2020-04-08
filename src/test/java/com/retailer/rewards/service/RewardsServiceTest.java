package com.retailer.rewards.service;

import com.retailer.rewards.model.CustomerRewards;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RewardsServiceTest {

    @Autowired
    RewardsService rewardsService;

    @Test
    public void getDateBefore30OffSetDaysTest(){
        Timestamp timestamp = Timestamp.from(Instant.now());
        int currentMonth = timestamp.toLocalDateTime().getMonth().getValue();
        RewardsServiceImpl rewardsServiceImpl = new RewardsServiceImpl();
        Assert.assertEquals(currentMonth,rewardsServiceImpl.getDateBeforeOffSetDays(30).toLocalDateTime().plusDays(30).getMonth().getValue());
    }

    @Test
    public void getDateBefore60OffSetDaysTest(){
        Timestamp timestamp = Timestamp.from(Instant.now());
        int currentMonth = timestamp.toLocalDateTime().getMonth().getValue();
        RewardsServiceImpl rewardsServiceImpl = new RewardsServiceImpl();
        Assert.assertEquals(currentMonth,rewardsServiceImpl.getDateBeforeOffSetDays(60).toLocalDateTime().plusDays(60).getMonth().getValue());
    }

    @Test
    public void getDateBefore90OffSetDaysTest(){
        Timestamp timestamp = Timestamp.from(Instant.now());
        int currentMonth = timestamp.toLocalDateTime().getMonth().getValue();
        RewardsServiceImpl rewardsServiceImpl = new RewardsServiceImpl();
        Assert.assertEquals(currentMonth,rewardsServiceImpl.getDateBeforeOffSetDays(90).toLocalDateTime().plusDays(90).getMonth().getValue());
    }

    @Test
    public void testCalculateRewardsWhenCustomerIdIs101(){
        CustomerRewards customerRewards = rewardsService.getRewards(101L);
        Assert.assertEquals(125, customerRewards.getLastMonthRewards());
        Assert.assertEquals(185,customerRewards.getLastSecondMonthRewards());
        Assert.assertEquals(185,customerRewards.getLastThirdMonthRewards());
        Assert.assertEquals(495,customerRewards.getTotalRewards());
    }

    @Test
    public void testCalculateRewardsWhenCustomerIdIs102(){
        CustomerRewards customerRewards = rewardsService.getRewards(102L);
        Assert.assertEquals(95, customerRewards.getLastMonthRewards());
        Assert.assertEquals(205,customerRewards.getLastSecondMonthRewards());
        Assert.assertEquals(5,customerRewards.getLastThirdMonthRewards());
        Assert.assertEquals(305,customerRewards.getTotalRewards());
    }

    @Test
    public void testCalculateRewardsWhenCustomerIdIs103(){
        CustomerRewards customerRewards = rewardsService.getRewards(103L);
        Assert.assertEquals(265, customerRewards.getLastMonthRewards());
        Assert.assertEquals(850,customerRewards.getLastSecondMonthRewards());
        Assert.assertEquals(0,customerRewards.getLastThirdMonthRewards());
        Assert.assertEquals(1115,customerRewards.getTotalRewards());
    }
}
