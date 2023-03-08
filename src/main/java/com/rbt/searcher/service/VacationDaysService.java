package com.rbt.searcher.service;

import com.rbt.searcher.error.RbtException;
import com.rbt.searcher.error.ResourceNotFoundException;
import com.rbt.searcher.model.dto.*;
import com.rbt.searcher.model.entity.EmployeeEntity;
import com.rbt.searcher.model.entity.UsedDaysEntity;
import com.rbt.searcher.repository.DaysPerYearRepository;
import com.rbt.searcher.repository.EmployeeRepository;
import com.rbt.searcher.repository.UsedDaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;

@Service
@AllArgsConstructor
public class VacationDaysService {
    private final EmployeeRepository employeeRepository;
    private final UsedDaysRepository usedDaysRepository;
    private final DaysPerYearRepository daysPerYearRepository;

    public TotalDaysResponse getTotalDays(Integer year) {
        try {
            var employee = getAuthenticatedEmployee();
            if (year == null) {
                var daysLeft = employee.getTotalVacationDaysLeft();
                var daysUsed = employee.getTotalVacationDaysUsed();
                return new TotalDaysResponse(
                        null,
                        daysLeft + daysUsed,
                        daysLeft,
                        daysUsed
                );
            } else {
                var totalDaysPerYear = daysPerYearRepository.findByEmployeeAndYear(employee, year).orElseThrow(() -> new ResourceNotFoundException("No vacation days for specified year")).getNumberOfDays();

                LocalDate startOfYear = LocalDate.of(year, 1, 1);
                LocalDate endOfYear = LocalDate.of(year, 12, 31);
                var usedDaysList = usedDaysRepository.findAllByDateFromAfterAndDateToBeforeAndEmployee(startOfYear, endOfYear, employee);
                var usedDays = usedDaysList.stream().mapToInt(UsedDaysEntity::getWorkDaysCount).sum();
                return new TotalDaysResponse(
                        year,
                        totalDaysPerYear,
                        totalDaysPerYear - usedDays,
                        usedDays
                );

            }
        } catch (RbtException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RbtException(500, ex.getMessage());
        }
    }

    public UsedDaysResponse getUsedDays(LocalDate from, LocalDate to) {
        try {
            if (from.isAfter(to)) {
                throw new RbtException(400, "invalid dates");
            }
            var employee = getAuthenticatedEmployee();
            var usedDays = usedDaysRepository.findAllByDateFromAfterAndDateToBeforeAndEmployee(from, to, employee).stream().map(range -> new UsedDaysRange(
                    range.getDateFrom(),
                    range.getDateTo(),
                    range.getWorkDaysCount()
            )).toList();

            return new UsedDaysResponse(usedDays);
        } catch (RbtException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RbtException(500, ex.getMessage());
        }
    }

    public AddedUsedDaysResponse addUsedDays(LocalDate from, LocalDate to) {
        try {
            if (from.isAfter(to)) {
                throw new RbtException(400, "invalid dates");
            }
            int currentYear = Year.now().getValue();
            var vacationStartYear = from.getYear();
            if ((currentYear - vacationStartYear) > 1 || (vacationStartYear - currentYear) > 1) {
                throw new RbtException(400, "invalid start of vacation");
            }
            var employee = getAuthenticatedEmployee();

            var weekDays = countWeekdays(from, to);
            var newVacationDays = new UsedDaysEntity(
                    null,
                    employee,
                    from,
                    to,
                    weekDays
            );
            usedDaysRepository.save(newVacationDays);

            return new AddedUsedDaysResponse(
                    weekDays
            );
        } catch (RbtException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RbtException(500, ex.getMessage());
        }
    }


    private EmployeeEntity getAuthenticatedEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principalEmail = authentication.getName();
        return employeeRepository.findByEmail(principalEmail).orElseThrow(() -> new ResourceNotFoundException("requested employee does not exist"));
    }

    public static int countWeekdays(LocalDate start, LocalDate end) {
        if (start.equals(end)) {
            if (start.getDayOfWeek() != DayOfWeek.SATURDAY && start.getDayOfWeek() != DayOfWeek.SUNDAY) {
                return 1;
            } else {
                return 0;
            }
        }
        int weekdays = 0;
        LocalDate date = start;
        while (!date.isAfter(end)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                weekdays++;
            }
            date = date.plusDays(1);
        }
        return weekdays;
    }
}
