package com.example.todoapp.Repository;
import com.example.todoapp.Entity.Tasks;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaRepository<Tasks, Long> {
    boolean existsByTask(String task);
    Tasks findByTaskId(Long taskId);
    List<Tasks> findByUserId(Long userId);
    Optional<Tasks> findByUserIdAndTaskId(Long userId, Long taskId);
    @Modifying
    @Transactional
    @Query("DELETE FROM tasks t WHERE t.taskId = :taskId AND t.userId = :userId")
    void deleteByTaskIdAndUserId(@Param("taskId") Long taskId, @Param("userId") Long userId);
    @Query("SELECT t FROM tasks t WHERE (:status IS NULL OR t.status = :status) " +
            "AND (:priority IS NULL OR t.priority = :priority) " +
            "AND (:dueDate IS NULL OR t.dueDate = :dueDate) " +
            "AND (:userId IS NULL OR t.userId = :userId) " )
    List<Tasks> findTasksByFilters(
            @Param("status") String status,
            @Param("priority") String priority,
            @Param("dueDate") String dueDate,
            @Param("userId") Long userId);

    List<Tasks> findByTaskContainingIgnoreCaseOrDescriptionStatusContainingIgnoreCase(String title, String description);
}

