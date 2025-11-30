// FinanceController.java
package com.jspm.SmartErp.controller;

import com.jspm.SmartErp.model.Finance;
import com.jspm.SmartErp.model.TransactionType;
import com.jspm.SmartErp.service.FinanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
@CrossOrigin(origins = "http://localhost:3000")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @GetMapping("/transactions")
    public ResponseEntity<List<Finance>> getAllTransactions() {
        try {
            List<Finance> transactions = financeService.getAllTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Integer id) {
        try {
            return financeService.getTransactionById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch transaction: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@Valid @RequestBody Finance finance) {
        try {
            Finance createdTransaction = financeService.createTransaction(finance);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create transaction: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Integer id, @Valid @RequestBody Finance financeDetails) {
        try {
            Finance updatedTransaction = financeService.updateTransaction(id, financeDetails);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update transaction: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Integer id) {
        try {
            financeService.deleteTransaction(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Transaction deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete transaction: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/transactions/type/{transactionType}")
    public ResponseEntity<List<Finance>> getTransactionsByType(@PathVariable TransactionType transactionType) {
        try {
            List<Finance> transactions = financeService.getTransactionsByType(transactionType);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/transactions/search")
    public ResponseEntity<List<Finance>> searchTransactions(@RequestParam String query) {
        try {
            List<Finance> transactions = financeService.searchTransactions(query);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/transactions/date-range")
    public ResponseEntity<List<Finance>> getTransactionsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        try {
            List<Finance> transactions = financeService.getTransactionsByDateRange(startDate, endDate);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, BigDecimal>> getFinancialSummary() {
        try {
            Map<String, BigDecimal> summary = new HashMap<>();
            summary.put("totalIncome", financeService.getTotalIncome());
            summary.put("totalExpenses", financeService.getTotalExpenses());
            summary.put("netBalance", financeService.getNetBalance());
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/transactions/recent")
    public ResponseEntity<List<Finance>> getRecentTransactions() {
        try {
            List<Finance> transactions = financeService.getRecentTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}