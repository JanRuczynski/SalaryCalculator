package com.janruczynski.SalaryCalculator;

import com.janruczynski.SalaryCalculator.data.WorkDayRepository;
import com.janruczynski.SalaryCalculator.web.controller.WorkDaysController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SalaryCalculatorApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalaryCalculatorApplication.class, args);

	}

}
