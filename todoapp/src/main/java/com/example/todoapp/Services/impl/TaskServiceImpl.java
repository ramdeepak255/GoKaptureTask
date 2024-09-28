package com.example.todoapp.Services.impl;

import com.example.todoapp.Entity.Tasks;
import com.example.todoapp.Models.TaskInput;
import com.example.todoapp.Models.UpdateTaskInput;
import com.example.todoapp.Repository.TasksRepository;
import com.example.todoapp.Services.TaskService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    public TasksRepository taskRepository;

    @Override
    public String createTask(Long userId,TaskInput taskInput) {
        boolean status=taskRepository.existsByTask(taskInput.getTitle());
        if(!status){
            Tasks task=new Tasks();
            task.setTask(taskInput.getTitle());
            task.setDescriptionStatus(taskInput.getDescriptionStatus());
            task.setPriority(taskInput.getPriority());
            task.setDueDate(taskInput.getDueDate());
            task.setStatus(taskInput.getStatus());
            task.setUserId(userId);

            taskRepository.save(task);

            return "The task has been created";
        }
        else{
            return "This Task title is already been used";
        }
    }

    @Override
    public Tasks getTask(Long userId,Long taskId){
        if(canUserAccessTask(userId,taskId)){
            return taskRepository.findByTaskId(taskId);
        }
        return null;
    }

    @Override
    public List<Tasks> getAllTask(Long userId){
        return taskRepository.findByUserId(userId);
    }

    @Override
    public String updateTask(Long userId, Long taskId, UpdateTaskInput updateTaskInput) {
        Optional<Tasks> optionalTask=taskRepository.findByUserIdAndTaskId(userId,taskId);
        if (optionalTask.isPresent()) {
            Tasks task = optionalTask.get();  // Get the actual Tasks object

            task.setStatus(updateTaskInput.getStatus());
            task.setPriority(updateTaskInput.getPriority());
            task.setDueDate(updateTaskInput.getDueDate());
            task.setDescriptionStatus(updateTaskInput.getDescriptionStatus());
            task.setTask(updateTaskInput.getTitle());

            taskRepository.save(task);

            return "Task has been updated successfully";
        } else {
            return "Task not found or access denied";
        }
    }

    @Override
    public boolean deleteTask(Long userId,Long taskId){
        if(canUserAccessTask(userId,taskId))
        {
            taskRepository.deleteByTaskIdAndUserId(userId,taskId);
            return true;
        }
        return false;
    }

    public boolean canUserAccessTask(Long userId, Long taskId) {
        Optional<Tasks> task = taskRepository.findByUserIdAndTaskId(userId, taskId);
        return task.isPresent();
    }

    @Override
    public List<Tasks> filterTasks(String status, String priority, String dueDate,Long userId) {
       return taskRepository.findTasksByFilters(status,priority,dueDate,userId);
    }

    public List<Tasks> searchTasks(String searchTerm) {
        return taskRepository.findByTaskContainingIgnoreCaseOrDescriptionStatusContainingIgnoreCase(searchTerm, searchTerm);
    }

}
