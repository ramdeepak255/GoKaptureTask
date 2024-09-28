package com.example.todoapp.Services;

import com.example.todoapp.Entity.Tasks;
import com.example.todoapp.Models.TaskInput;
import com.example.todoapp.Models.UpdateTaskInput;

import java.util.List;

public interface TaskService {

    public String createTask(Long userId,TaskInput taskInput);

    public Tasks getTask(Long userId,Long taskId );

    public List<Tasks> getAllTask(Long userId);

    public String updateTask(Long userId, Long taskId, UpdateTaskInput updateTaskInput);

    public boolean deleteTask(Long userId,Long taskId);

    public List<Tasks> filterTasks(String status, String priority, String dueDate,Long userId);

    public List<Tasks> searchTasks(String term);
}
