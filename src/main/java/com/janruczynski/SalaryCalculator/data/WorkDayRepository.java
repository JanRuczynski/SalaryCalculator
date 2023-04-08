package com.janruczynski.SalaryCalculator.data;

import com.janruczynski.SalaryCalculator.biz.model.WorkDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkDayRepository extends CrudRepository<WorkDay, Long> {

}
