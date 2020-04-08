package com.retailer.rewards.service;

import com.retailer.rewards.model.CustomerRewards;
import com.retailer.rewards.entity.Transaction;
import com.retailer.rewards.repository.TransactionRepository;
import com.retailer.rewards.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:rewards-config.properties")
public class RewardsServiceImpl implements RewardsService{

    @Value("${singleRewardThreshold}")
    private int singleRewardThreshold;


    @Value("${doubleRewardThreshold}")
    private int doubleRewardThreshold;

    @Autowired
    TransactionRepository transactionRepository;

    public CustomerRewards getRewards(Long customerId) {

        Timestamp lastMonthTimestamp = getDateBeforeOffSetDays(Constants.thirtyDayOffset);
        Timestamp lastSecondMonthTimestamp = getDateBeforeOffSetDays(Constants.sixtyDayOffset);
        Timestamp lastThirdMonthTimestamp = getDateBeforeOffSetDays(Constants.ninetyDayOffset);

        List<Transaction> lastMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId,lastMonthTimestamp,Timestamp.from(Instant.now()));
        List<Transaction> lastSecondMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId,lastSecondMonthTimestamp,lastMonthTimestamp);
        List<Transaction> lastThirdMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(customerId,lastThirdMonthTimestamp,lastSecondMonthTimestamp);

        Long lastMonthRewards = getRewardsPerMonth(lastMonthTransactions);
        Long lastSecondMonthRewards = getRewardsPerMonth(lastSecondMonthTransactions);
        Long lastThirdMonthRewards = getRewardsPerMonth(lastThirdMonthTransactions);

        CustomerRewards customerRewards = new CustomerRewards();
        customerRewards.setCustomerId(customerId);
        customerRewards.setLastMonthRewards(lastMonthRewards);
        customerRewards.setLastSecondMonthRewards(lastSecondMonthRewards);
        customerRewards.setLastThirdMonthRewards(lastThirdMonthRewards);
        customerRewards.setTotalRewards(lastMonthRewards+lastSecondMonthRewards+lastThirdMonthRewards);

        return customerRewards;

    }

    private Long getRewardsPerMonth(List<Transaction> transactions) {
        return transactions.stream().map(transaction -> calculateRewards(transaction)).
                collect(Collectors.summingLong(r->r.longValue()));
    }

    private Long calculateRewards(Transaction t) {
        if(t.getTransactionAmount() > singleRewardThreshold && t.getTransactionAmount() <= doubleRewardThreshold){
            return Math.round(t.getTransactionAmount() - singleRewardThreshold);
        }else if(t.getTransactionAmount() > doubleRewardThreshold){
            return Math.round(t.getTransactionAmount() - doubleRewardThreshold)*2 + (doubleRewardThreshold - singleRewardThreshold);
        }else
            return 0l;

    }

    public Timestamp getDateBeforeOffSetDays(int offSet) {
        return Timestamp.valueOf(LocalDateTime.now().minusDays(offSet));
    }

}
