package com.example.todoapp.controllers;

import com.example.todoapp.Entity.Tasks;
import com.example.todoapp.Models.TaskInput;
import com.example.todoapp.Models.UpdateTaskInput;
import com.example.todoapp.Services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/Task")
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;
    @Operation(summary = "Create todo item")
    @PostMapping("/create")
    public ResponseEntity<Object> createTask(@RequestParam("userId") Long userId,@RequestBody TaskInput taskInput)
    {
        String response="";
       try{
            response=taskService.createTask(userId,taskInput);
       }
       catch (Exception e)
       {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
       }
       return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a todo item")
    @GetMapping("/getTask")
    public ResponseEntity<?> getTask(@RequestParam("userId")  Long userId,@RequestParam("taskId")  Long taskId) {
        try {
            Tasks task = taskService.getTask(userId,taskId);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Task not found or an error occurred: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get a todo item")
    @GetMapping("/getAllTask")
    private ResponseEntity<?> getAllTask(@RequestParam("userId")  Long userId)
    {
        try {
            List<Tasks> taskList = taskService.getAllTask(userId);
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No tasks were created: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a todo item")
    @PutMapping("/updateTask")
    private ResponseEntity<?> updateTask(@RequestParam("userId")  Long userId, @RequestParam("taskId")  Long taskId, @RequestBody UpdateTaskInput updateTaskInput)
    {
        try {
            String response = taskService.updateTask(userId,taskId,updateTaskInput);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid user access: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Update a todo item")
    @DeleteMapping("/deleteTask")
    private ResponseEntity<?> deleteTask(@RequestParam("userId")  Long userId, @RequestParam("taskId")  Long taskId)
    {

           if(taskService.deleteTask(userId,taskId)){
               return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
           }
           else{
               return new ResponseEntity<>("Unable to delete the task", HttpStatus.BAD_REQUEST);
           }

    }

    @GetMapping("/filter")
    public ResponseEntity<List<Tasks>> filterTasks(
            @RequestParam(name = "status",required = false) String status,
            @RequestParam(name = "priority",required = false) String priority,
            @RequestParam(name = "dueDate",required = false) String dueDate,
        @RequestParam("userId")  Long userId){
        List<Tasks> filteredTasks = taskService.filterTasks(status, priority, dueDate,userId);
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }


    @GetMapping("/search")
    public List<Tasks> searchTasks(@RequestParam(name = "inputTerm") String inputTerm) {
        return taskService.searchTasks(inputTerm);
    }


}
