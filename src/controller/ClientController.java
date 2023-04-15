package controller;

import model.Log;
import model.Message;
import model.user.User;
import view.Menu;

import java.util.List;

public class ClientController {
    private List<User> users;
    private List<Log> logs;
    private List<Message> messages;
    private final Menu view;

    public ClientController(List<User> users, List<Log> logs, List<Message> messages, Menu view) {
        this.users = users;
        this.logs = logs;
        this.messages = messages;
        this.view = view;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
