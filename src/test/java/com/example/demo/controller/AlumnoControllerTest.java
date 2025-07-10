package com.example.demo.controller;

import com.example.demo.model.Alumno;
import com.example.demo.service.AlumnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlumnoController.class)
public class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumnoService alumnoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllAlumnos_shouldReturnListOfAlumnos() throws Exception {
        Alumno alumno1 = new Alumno();
        alumno1.setId(1L);
        alumno1.setNombre("Juan");
        alumno1.setApellido("Perez");

        Alumno alumno2 = new Alumno();
        alumno2.setId(2L);
        alumno2.setNombre("Maria");
        alumno2.setApellido("Gomez");

        List<Alumno> alumnos = Arrays.asList(alumno1, alumno2);

        when(alumnoService.getAllAlumnos()).thenReturn(alumnos);

        mockMvc.perform(get("/alumnos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Juan")))
                .andExpect(jsonPath("$[1].nombre", is("Maria")));
    }

    @Test
    public void createAlumno_shouldReturnCreatedAlumno() throws Exception {
        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Carlos");
        alumno.setApellido("Lopez");

        when(alumnoService.saveAlumno(any(Alumno.class))).thenReturn(alumno);

        mockMvc.perform(post("/alumnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumno)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Carlos")))
                .andExpect(jsonPath("$.apellido", is("Lopez")));
    }
}