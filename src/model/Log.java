package model;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.*;

public class Log {
    private int id;
    private int user_id;
    private LocalDateTime localDateTime;
    private TypeLog type;

    /**
     * Log enum
     */
    public enum TypeLog {
        CONNECTION, DISCONNECTION, MESSAGE, BAN, UNBAN, SET_ROLE, UNKNOWN;
    }

    /**
     * Constructors
     */
    public Log(int user_id, TypeLog type) {
        this.user_id = user_id;
        this.type = type;
        this.localDateTime = now();
    }
    public Log(int id, int user_id, LocalDateTime time, TypeLog type) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.localDateTime = time;
    }

    /**
     * getters and setters for id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * getters and setters for user id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * getters and setters for the log date ans time
     */
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    /**
     * getters and setters for the type
     */
    public TypeLog getType() {
        return type;
    }

    /**
     * To string method
     */
    @Override
    public String toString() {
        return "Id : " + this.id + "| User_id : " + this.user_id + " | TimeStamp : " + this.localDateTime + " | Type : " + this.type;
    }
}
