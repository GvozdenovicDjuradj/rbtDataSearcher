package com.rbt.searcher.controller;

import com.rbt.searcher.model.dto.AddedUsedDaysResponse;
import com.rbt.searcher.model.dto.TotalDaysResponse;
import com.rbt.searcher.model.dto.UsedDaysResponse;
import com.rbt.searcher.service.VacationDaysService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/vacationDays")
@AllArgsConstructor
@SecurityRequirement(name = "rbt")
public class VacationDaysController {

    private final VacationDaysService vacationDaysService;

    @GetMapping(value = "totalDays/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TotalDaysResponse> getTotalDays(@PathVariable Integer year){
        return ResponseEntity.ok(vacationDaysService.getTotalDays(year));
    }

    @GetMapping(value = "findUsedDays", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UsedDaysResponse> getUsedDays(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to){
        return ResponseEntity.ok(vacationDaysService.getUsedDays(from, to));
    }

    @GetMapping(value = "addUsedDays", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AddedUsedDaysResponse> addUsedDays(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to){
        return ResponseEntity.ok(vacationDaysService.addUsedDays(from, to));
    }
}
