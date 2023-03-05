package com.rbt.searcher.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedDaysRange {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int weekDaysCount;
}
