package com.janruczynski.SalaryCalculator.web.controller;

import com.janruczynski.SalaryCalculator.biz.model.WorkDay;
import com.janruczynski.SalaryCalculator.data.WorkDayRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="/calculator")
public class WorkDaysController {

    private WorkDayRepository workDayRepository;


    @Autowired
    public WorkDaysController(WorkDayRepository workDayRepository) {
        this.workDayRepository = workDayRepository;
    }

    @ModelAttribute("workdays")
    public Iterable<WorkDay> getWorkDays() {
        return workDayRepository.findAll();
    }

    @ModelAttribute
    public WorkDay getWorkDay() {
        return new WorkDay();
    }

    @GetMapping
    public String showCalculatorPage(Model model) {
        Duration durationSum = WorkDay.getDurationSum(workDayRepository);
        model.addAttribute("durationSum", String.format("%d:%02d", durationSum.toHours(), durationSum.toMinutesPart()));
        model.addAttribute("ordersSum", WorkDay.getOrdersSum(workDayRepository));
        model.addAttribute("averageOPH", WorkDay.getAverageOPH(workDayRepository));
        model.addAttribute("totalEarnings", WorkDay.getTotalEarnings(workDayRepository));
        model.addAttribute("averageHourlyPay", WorkDay.getAverageHourlyPay(workDayRepository));
        return "calculator";
    }

    @PostMapping
    public String saveWorkDay(Model model, @Valid WorkDay workDay, Errors errors) {

        if (!errors.hasErrors()) {
            workDayRepository.save(workDay);
            return "redirect:calculator";
        } else {
            model.addAttribute("errorMsg", "Please enter valid information");
            return "calculator";
        }
    }

    @PostMapping(params = "action=delete")
    public String deleteWorkDay(@RequestParam Optional<List<Long>> selections) {
        if (selections.isPresent()) {
            workDayRepository.deleteAllById(selections.get());
        }
        return "redirect:calculator";
    }

    @PostMapping(params = "action=edit")
    public String editWorkDay(@RequestParam Optional<List<Long>> selections, Model model) {
        if (selections.isPresent()) {
            Optional<WorkDay> workDay = workDayRepository.findById(selections.get().get(0));
            model.addAttribute("workDay", workDay);
        }
        return "calculator";
    }
}
