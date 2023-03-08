package com.rbt.searcher.controller;

import com.rbt.searcher.model.dto.AddedUsedDaysResponse;
import com.rbt.searcher.model.dto.TotalDaysResponse;
import com.rbt.searcher.model.dto.UsedDaysResponse;
import com.rbt.searcher.service.VacationDaysService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/vacation-days")
@AllArgsConstructor
@SecurityRequirement(name = "rbt")
public class VacationDaysController {

    private final VacationDaysService vacationDaysService;

    @GetMapping(value = "total-days/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TotalDaysResponse> getTotalDays(@PathVariable Integer year) {
        return ResponseEntity.ok(vacationDaysService.getTotalDays(year));
    }

    @GetMapping(value = "used-days", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UsedDaysResponse> getUsedDays(
            @RequestParam @Schema(description = "From date in yyyy-MM-dd format", type = "string", format = "date", example = "2022-12-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam @Schema(description = "To date in yyyy-MM-dd format", type = "string", format = "date", example = "2022-12-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return ResponseEntity.ok(vacationDaysService.getUsedDays(from, to));
    }

    @PostMapping(value = "used-days", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AddedUsedDaysResponse> addUsedDays(
            @RequestParam @Schema(description = "From date in yyyy-MM-dd format", type = "string", format = "date", example = "2022-12-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam @Schema(description = "To date in yyyy-MM-dd format", type = "string", format = "date", example = "2022-12-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        return ResponseEntity.ok(vacationDaysService.addUsedDays(from, to));
    }
}
