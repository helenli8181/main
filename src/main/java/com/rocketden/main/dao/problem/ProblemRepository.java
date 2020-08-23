package com.rocketden.main.dao.problem;

import com.rocketden.main.dto.problem.Problem;
import org.springframework.data.repository.CrudRepository;

public interface ProblemRepository extends CrudRepository<Problem, Integer> {
  
}
