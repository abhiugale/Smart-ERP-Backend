package com.jspm.SmartErp.repository;
import com.jspm.SmartErp.model.Finance;
import com.jspm.SmartErp.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceRepository extends JpaRepository<Finance, Integer> {
    List<Finance> findByUser_UserId(Integer userId);
    List<Finance> findByTransactionType(TransactionType type);
}
