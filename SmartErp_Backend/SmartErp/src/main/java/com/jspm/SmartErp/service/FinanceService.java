// FinanceService.java
package com.jspm.SmartErp.service;

import com.jspm.SmartErp.model.Finance;
import com.jspm.SmartErp.model.TransactionType;
import com.jspm.SmartErp.repository.FinanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FinanceService {

    @Autowired
    private FinanceRepository financeRepository;

    public List<Finance> getAllTransactions() {
        return financeRepository.findAll();
    }

    public Optional<Finance> getTransactionById(Integer id) {
        return financeRepository.findById(id);
    }

    public Finance createTransaction(Finance finance) {
        // Validate transaction date is not in the future
        if (finance.getTransactionDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Transaction date cannot be in the future");
        }

        return financeRepository.save(finance);
    }

    public Finance updateTransaction(Integer id, Finance financeDetails) {
        Finance finance = financeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        // Validate transaction date is not in the future
        if (financeDetails.getTransactionDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Transaction date cannot be in the future");
        }

        finance.setTransactionType(financeDetails.getTransactionType());
        finance.setAmount(financeDetails.getAmount());
        finance.setTransactionDate(financeDetails.getTransactionDate());
        finance.setDescription(financeDetails.getDescription());

        return financeRepository.save(finance);
    }

    public void deleteTransaction(Integer id) {
        Finance finance = financeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        financeRepository.delete(finance);
    }

    public List<Finance> getTransactionsByType(TransactionType transactionType) {
        return financeRepository.findByTransactionType(transactionType);
    }

    public List<Finance> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return financeRepository.findByTransactionDateBetween(startDate, endDate);
    }

    public List<Finance> searchTransactions(String query) {
        return financeRepository.findByDescriptionContainingIgnoreCase(query);
    }

    public BigDecimal getTotalIncome() {
        return financeRepository.getTotalIncome();
    }

    public BigDecimal getTotalExpenses() {
        return financeRepository.getTotalExpenses();
    }

    public BigDecimal getNetBalance() {
        BigDecimal income = getTotalIncome();
        BigDecimal expenses = getTotalExpenses();
        return income.subtract(expenses);
    }

    public List<Finance> getRecentTransactions() {
        return financeRepository.findTop10ByOrderByTransactionDateDesc();
    }

    public List<Finance> getTransactionsByMonthAndYear(int month, int year) {
        return financeRepository.findByMonthAndYear(month, year);
    }
}