package com.rbt.searcher;

import com.rbt.searcher.service.VacationDaysService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
class SearcherApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testCountWeekdays_noWeekend() {
        assertEquals(VacationDaysService.countWeekdays(LocalDate.of(2023, Month.MARCH, 6), LocalDate.of(2023, Month.MARCH, 10)), 5);
    }
    @Test
    void testCountWeekdays_containsWeekend() {
        assertEquals(VacationDaysService.countWeekdays(LocalDate.of(2023, Month.MARCH, 3), LocalDate.of(2023, Month.MARCH, 6)), 2);
    }

    @Test
    void testCountWeekdays_sameDay() {
        assertEquals(VacationDaysService.countWeekdays(LocalDate.of(2023, Month.MARCH, 1), LocalDate.of(2023, Month.MARCH, 1)), 1);
    }

    @Test
    void testCountWeekdays_sameDayWeekend() {
        assertEquals(VacationDaysService.countWeekdays(LocalDate.of(2023, Month.MARCH, 5), LocalDate.of(2023, Month.MARCH, 5)), 0);
    }
}
