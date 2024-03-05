package lv.rvt.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lv.rvt.entity.Student;

@RestController
public class DefaultRestController {

    @RequestMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
    String getAllStudents() {
        Student student = new Student();
        student.setName("John");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(student);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
