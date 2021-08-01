package br.com.usp.uspfit.controller;

import br.com.usp.uspfit.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    @GetMapping("/dashboard/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity dashboard(@PathVariable("id") Integer id){
        var map = new HashMap<String, Object>();

        var informations = service.getBasicClient(id);
        var instructors = service.getInstructorsAndHoursOfDay();

        map.put("client", informations);
        map.put("instructors", instructors);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/instructors")
    @CrossOrigin(origins = "*")
    public ResponseEntity instructors(){
        var response = service.getInstructorsAndHoursOfDay().values().toArray();

        return ResponseEntity.ok(response);

    }


    @GetMapping("/trains/user/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity getTrainingFromCliente(@PathVariable("id") Integer id ){
        var response = service.getTrainingFromClient(id).values().toArray();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/trains/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity getSingleTrainFromClient(@PathVariable("id") Integer id){
        var response = service.getSingle(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exams/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity method(@PathVariable("id") Integer id){

        var response = service.examsFromClient(id);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/exercises")
    @CrossOrigin(origins = "*")
    public ResponseEntity exercises(){
        var response = service.getExercises();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{method}/{value}")
    @CrossOrigin(origins = "*")
    public ResponseEntity exercises(@PathVariable("method") String method, @PathVariable("value") String value){

        var result = switch (method.toUpperCase()){
            case "CPF" -> service.getClientByCPF(value);
            case "EMAIL" -> service.getClientByEmail(value);
            default -> null;
        };

        return ResponseEntity.ok(result);
    }
}
