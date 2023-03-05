package com.rbt.searcher.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalDaysResponse {
    private Integer year;
    private int totalDays;
    private int totalDaysLeft;
    private int totalDaysUsed;

}
