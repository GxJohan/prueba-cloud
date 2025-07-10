package com.example.demo.service;

import com.example.demo.model.Alumno;
import com.example.demo.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.findAll();
    }

    public Alumno saveAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }
}