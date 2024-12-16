package com.bulatmain.conference.infastructure.repository.db;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DbInitializer {
    @Autowired
    private final CustomJdbcTemplate source;
//    @Value("db-init-sql")
    private final String initSql = """
        -- Main Tables
        CREATE TABLE IF NOT EXISTS "organizer" (
          "id" VARCHAR(255) PRIMARY KEY NOT NULL
        );
                
        CREATE TABLE IF NOT EXISTS "conference" (
          "id" VARCHAR(255) PRIMARY KEY NOT NULL,
          "name" VARCHAR(255) NOT NULL,
          "organizer_id" VARCHAR(255) NOT NULL,
          FOREIGN KEY ("organizer_id") REFERENCES "organizer" ("id") ON DELETE CASCADE
        );
                
        CREATE TABLE IF NOT EXISTS "talk" (
          "id" VARCHAR(255) PRIMARY KEY NOT NULL,
          "name" VARCHAR(255) NOT NULL,
          "status" VARCHAR(50) CHECK ("status" IN ('scheduled', 'completed', 'canceled')) NOT NULL,
          "conference_id" VARCHAR(255) NOT NULL,
          FOREIGN KEY ("conference_id") REFERENCES "conference" ("id") ON DELETE CASCADE
        );
           
        -- Auxiliary Tables
        CREATE TABLE IF NOT EXISTS "speaker" (
          "id" VARCHAR(255) PRIMARY KEY NOT NULL
        );
                
        CREATE TABLE IF NOT EXISTS "listener" (
          "id" VARCHAR(255) PRIMARY KEY NOT NULL
        );
                
        -- Many-to-Many Relationship Tables
        CREATE TABLE IF NOT EXISTS "talk_speaker" (
          "talk_id" VARCHAR(255) NOT NULL,
          "speaker_id" VARCHAR(255) NOT NULL,
          PRIMARY KEY ("talk_id", "speaker_id"),
          FOREIGN KEY ("talk_id") REFERENCES "talk" ("id") ON DELETE CASCADE,
          FOREIGN KEY ("speaker_id") REFERENCES "speaker" ("id") ON DELETE CASCADE
        );
                
        CREATE TABLE IF NOT EXISTS "talk_listener" (
          "talk_id" VARCHAR(255) NOT NULL,
          "listener_id" VARCHAR(255) NOT NULL,
          PRIMARY KEY ("talk_id", "listener_id"),
          FOREIGN KEY ("talk_id") REFERENCES "talk" ("id") ON DELETE CASCADE,
          FOREIGN KEY ("listener_id") REFERENCES "listener" ("id") ON DELETE CASCADE
        );
        """;

    @PostConstruct
    protected void create() throws SQLException {
        source.statement(stmt -> {
            stmt.execute(initSql);
        });
        log.debug("Database initialized with sql script \n{}\n", initSql);
    }

}
