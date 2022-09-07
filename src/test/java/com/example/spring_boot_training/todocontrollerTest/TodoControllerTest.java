package com.example.spring_boot_training.todocontrollerTest;

import com.example.spring_boot_training.controller.ToDoController;
import com.example.spring_boot_training.entity.ToDo;
import com.example.spring_boot_training.service.ToDoServiceimplements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ToDoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ToDoServiceimplements toDoServiceimplements;

    @MockBean
    private ModelMapper modelMapper;

    /** Teste einzelnes Todo **/
    @Test
    public void getTodo() throws Exception {

        ToDo toDo = new ToDo(2L, "putzen", "02.09.2022", false);

        when(toDoServiceimplements.getToDo(any(Long.class))).thenReturn(toDo);
        this.mockMvc.perform(get("/todo/2"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
        {
            "id" : 2,
            "aufgabe" : "putzen",
            "datum" : "02.09.2022",
            "isDone" : false
        }
        """
                ));
    }



    /** Teste alle Todos **/
    @Test
    public void getTodos() throws Exception{

        ToDo toDo1 = new ToDo(1L, "putzen", "02.09.2022", true);
        ToDo toDo2 = new ToDo(2L, "kochen", "09.09.2022", false);

        List<ToDo> myTodos = Arrays.asList(toDo1, toDo2);

        when(toDoServiceimplements.getToDoListe()).thenReturn(myTodos);
        this.mockMvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
        [{
            "id" : 1,
            "aufgabe" : "putzen",
            "datum" : "02.09.2022",
            "isDone" : true
        },
        {
            "id" : 2,
            "aufgabe" : "kochen",
            "datum" : "09.09.2022",
            "isDone" : false
        }
        ]
        """
                ));
    }



    /** Teste erstellen eines Todos **/
    @Test
    public void createTodo() throws Exception{

        this.mockMvc.perform(post("/todo").content("""                
                
                {
                    "id":10,
                    "aufgabe" : "CompuSafe Event",
                    "datum" : "07.10.2022",
                    "isDone" : false        
                }                
                
                
                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /** Teste updaten eines Todos **/
    @Test
    public void updateTodo(){

    }

    /** Teste löschen eines Todos **/
    @Test
    public void deleteTodo(){

    }
}