package com.example.weatherBotForBobrAi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "log_message_table")
@Data
public class LogMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long telegramUserId;

    private String userMessage;

    private String botAnswer;

    @CurrentTimestamp
    private Timestamp messageTime;

}
