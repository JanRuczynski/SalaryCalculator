package com.janruczynski.SalaryCalculator.biz.model;

import com.janruczynski.SalaryCalculator.Validation.ValidateTime;
import com.janruczynski.SalaryCalculator.data.WorkDayRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.StreamSupport;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ValidateTime(message = "Please provide correct start time, end time, and date")
public class WorkDay {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message="Date must be specified")
    private LocalDate date;

    @NotNull(message="Starting time must be specified")
    private LocalTime startTime;

    private LocalTime endTime;

    @DecimalMin(value="0", message="Number of orders must be greater than 0")
    private int orders;

    private String annotation;

    @Transient
    private static final MathContext mc = new MathContext(6, RoundingMode.HALF_UP);


    public static BigDecimal divideWithScaleAndRounding(BigDecimal dividend, BigDecimal divisor, int scale, RoundingMode roundingMode) {
        return dividend.divide(divisor, scale, roundingMode);
    }
    private Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    public BigDecimal getDurationDecimal() {
        return BigDecimal.valueOf(getDuration().toMinutes()).divide(new BigDecimal("60.00"), 2, RoundingMode.HALF_UP);
    }

    public String getWorkTime() {
        Duration duration = getDuration();
        return String.format("%d:%02d", duration.toHours(), duration.toMinutesPart());
    }

    public BigDecimal getOPH() {
        return new BigDecimal(orders).divide(getDurationDecimal(),2, RoundingMode.HALF_UP);
    }

    public BigDecimal getHourlyPay() {
        return getDailyEarnings().divide(getDurationDecimal(), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getDailyEarnings() {

        BigDecimal result;
        if(getOPH().compareTo(BigDecimal.valueOf(2)) > 0) {
            BigDecimal durationPart = getDurationDecimal().multiply(new BigDecimal("12.80"));
            BigDecimal ordersPart = new BigDecimal(orders).multiply(new BigDecimal("5"));
            result = durationPart.add(ordersPart);
        }
        else {
            result = new BigDecimal("22.80").multiply(getDurationDecimal());
        }
        return result.setScale(2, RoundingMode.HALF_UP);
    }

    public static Duration getDurationSum(WorkDayRepository workDayRepository) {
        return StreamSupport.stream(workDayRepository.findAll().spliterator(), false)
                .map(WorkDay::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public static int getOrdersSum(WorkDayRepository workDayRepository) {
        return StreamSupport.stream(workDayRepository.findAll().spliterator(), false)
                .map(WorkDay::getOrders)
                .reduce(0, Integer::sum);
    }

    public static BigDecimal getAverageOPH(WorkDayRepository workDayRepository) {
        return BigDecimal.valueOf(getOrdersSum(workDayRepository))
                .divide(BigDecimal.valueOf(getDurationSum(workDayRepository)
                        .toMinutes()).divide(new BigDecimal("60.00"), 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getTotalEarnings(WorkDayRepository workDayRepository) {
        return StreamSupport.stream(workDayRepository.findAll().spliterator(), false)
                .map(WorkDay::getDailyEarnings)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal getAverageHourlyPay(WorkDayRepository workDayRepository) {
        return getTotalEarnings(workDayRepository)
                .divide(BigDecimal.valueOf(getDurationSum(workDayRepository).toMinutes())
                        .divide(new BigDecimal("60.00"), 2 ,RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP);
    }
}
