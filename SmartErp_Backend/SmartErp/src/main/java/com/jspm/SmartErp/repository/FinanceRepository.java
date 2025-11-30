// FinanceRepository.java
package com.jspm.SmartErp.repository;

import com.jspm.SmartErp.model.Finance;
import com.jspm.SmartErp.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Integer> {

    // Find transactions by type
    List<Finance> findByTransactionType(TransactionType transactionType);

    // Find transactions by date range
    List<Finance> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);

    // Find transactions by description (search)
    List<Finance> findByDescriptionContainingIgnoreCase(String description);

    // Get total income (FEE, GRANT, DONATION)
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Finance f WHERE f.transactionType IN (com.jspm.SmartErp.model.TransactionType.FEE, com.jspm.SmartErp.model.TransactionType.GRANT, com.jspm.SmartErp.model.TransactionType.DONATION)")
    BigDecimal getTotalIncome();

    // Get total expenses (EXPENSE, SALARY, OTHER)
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM Finance f WHERE f.transactionType IN (com.jspm.SmartErp.model.TransactionType.EXPENSE, com.jspm.SmartErp.model.TransactionType.SALARY, com.jspm.SmartErp.model.TransactionType.OTHER)")
    BigDecimal getTotalExpenses();

    // Get transactions by month and year
    @Query("SELECT f FROM Finance f WHERE YEAR(f.transactionDate) = :year AND MONTH(f.transactionDate) = :month")
    List<Finance> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    // Get recent transactions
    List<Finance> findTop10ByOrderByTransactionDateDesc();
}