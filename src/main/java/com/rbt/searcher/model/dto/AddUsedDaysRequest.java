package com.rbt.searcher.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUsedDaysRequest {
    private LocalDate timeFrom;
    private LocalDate timeTo;
}
