package com.Repository;

import com.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long>
{
    boolean existsByAccount_AccountId(Long accountId);
}
