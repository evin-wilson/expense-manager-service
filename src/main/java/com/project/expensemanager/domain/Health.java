package com.project.expensemanager.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Health {
    private int version;
    private String status;
}
