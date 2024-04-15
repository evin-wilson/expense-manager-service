package com.project.expensemanager.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Summary {
    @JsonProperty("annual_expense")
    private BigDecimal annualExpense;

    @JsonProperty("annual_income")
    private BigDecimal annualIncome;

    @JsonProperty("monthly_transaction_summary")
    private List<MonthSummary> monthSummary;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthSummary {
        private BigDecimal expense;
        private BigDecimal income;
        private String month;
    }
}
