package com.retailer.rewards.service;

import com.retailer.rewards.model.CustomerRewards;

import java.sql.Timestamp;

public interface RewardsService {
    public CustomerRewards getRewards(Long customerId);
}
