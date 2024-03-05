package lv.rvt.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lv.rvt.entity.Student;
import lv.rvt.repository.StudentRepository;

@Controller
public class DefaultController {

    @Autowired
    private StudentRepository studentRepository;
    
    @GetMapping(value = "/")
    ModelAndView index(@RequestParam(name="name", required=false, defaultValue="null") String name) {
        Student student = Student.builder()
            .name("John")
            .surname("Smith")
            .email("e@rvt.lv")
            .studentGroup("DP2-5")
            .build()
        ;
        List<Student> students = new ArrayList<>();
        students.add(student);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("students", students);
        modelAndView.addObject("date", new Date().toString());
        return modelAndView;
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public String testAction() {
        Student student = Student.builder()
            .name("John")
            .surname("Smith")
            .email("e@rvt.lv")
            .studentGroup("DP2-5")
            .build()
        ;

        studentRepository.save(student);

        Optional<Student> saved = studentRepository.findById(student.getId());
        
        return saved.toString();
    }
}
