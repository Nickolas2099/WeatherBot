package com.example.weatherBotForBobrAi.repository;

import com.example.weatherBotForBobrAi.domain.entity.LogMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface LogMessageRepository extends JpaRepository<LogMessage, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM log_message_table ORDER BY message_time DESC LIMIT 500")
    List<LogMessage> getRecentMessages();

    @Query(nativeQuery = true, value = "SELECT * FROM log_message_table WHERE telegram_user_id = :user_id ORDER BY message_time DESC LIMIT 500")
    List<LogMessage> getRecentMessagesByUserId(@Param("user_id") Long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM log_message_table WHERE message_time BETWEEN :startDate AND :endDate")
    Page<LogMessage> findByMessageTimeBetween(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM log_message_table ORDER BY message_time DESC")
    Page<LogMessage> findAllMessages(Pageable pageable);
}
