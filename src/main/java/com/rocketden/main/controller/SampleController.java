package com.rocketden.main.controller;

import com.rocketden.main.dao.problem.ProblemRepository;
import com.rocketden.main.dto.problem.Problem;
import com.rocketden.main.model.SampleObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  @Autowired
  private ProblemRepository problemRepository;

  @GetMapping("/hello")
  public SampleObject hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new SampleObject("Hello, " + name + "!");
  }

  @PostMapping("/addProblem")
  public @ResponseBody String addNewProblem(@RequestParam String name, @RequestParam String description) {

    Problem problem = new Problem();
    problem.setName(name);
    problem.setDescription(description);
    problemRepository.save(problem);
    return "Saved";
  }
}
