package model;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;

public class Message {
    private int id;
    private int user_id;
    private LocalDateTime localDateTime;
    private String content;

    /**
     * Constructors
     */
    public Message() {
        this.id = -1;
        this.user_id = -1;
        this.localDateTime = now();
        this.content = "unknown";
    }

    public Message(int user_id, String content, int id) {
        this.id = id;
        this.user_id = user_id;
        localDateTime = now();
        this.content = content;
    }

    public Message(int id, int user_id, LocalDateTime time, String content) {
        this.id = id;
        this.user_id = user_id;
        this.localDateTime = time;
        this.content = content;
    }

    public Message(Message message) {
        this.id = message.getId();
        this.user_id = message.getUser_id();
        this.localDateTime = message.getLocalDateTime();
        this.content = message.getContent();
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
     * getters and setters for message date and time
     */
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    /**
     * getters and setters for the content
     */
    public String getContent() {
        return content;
    }

    /**
     * To string method
     */
    @Override
    public String toString() {
        return "Message{" + "id=" + this.id + ", User_id=" + this.user_id + ", TimeStamp=" + this.localDateTime + ", Content=" + this.content;
    }
}
