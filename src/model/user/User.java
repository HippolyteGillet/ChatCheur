package model.user;

import java.time.LocalDate;

public class User {

    //DAO Attributes:
    private int id;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Permission permission;
    private Access access;
    private State state;
    private LocalDate lastConnectionTime;

    public enum Access {
        BANNED,
        ACCEPTED
    }

    public enum State {
        ONLINE,
        AWAY,
        OFFLINE
    }

    public enum Permission {
        USER,
        MODERATOR,
        ADMINISTRATOR
    }
    public User() {
    }

    public User(int id, String userName, String password, String email, String firstName, String lastName, State state, LocalDate lastConnectionTime) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permission = Permission.USER;
        this.access = Access.ACCEPTED;
        this.state = state;
        this.lastConnectionTime = lastConnectionTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

        // Pas de update pour l'id car on ne peut pas le changer

        //DAO update:
        //userDAO.update(this);

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;

    }

    public Permission getPermission() {
        return this.permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;

    }

    public Access getAccess() {
        return this.access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;

    }

    public LocalDate getLastConnectionTime() {
        return lastConnectionTime;
    }

    public void setLastConnectionTime(LocalDate lastConnectionTime) {
        this.lastConnectionTime = lastConnectionTime;

    }

    public void connect() {
        this.state = State.ONLINE;
        this.lastConnectionTime = LocalDate.now();
    }

    public void disconnect() {
        this.state = State.OFFLINE;
    }

    public void away() {
        this.state = State.AWAY;
    }

    public void back() {
        this.state = State.ONLINE;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", permission=" + permission + ", status=" + access + ", state=" + state + ", lastConnectionTime=" + lastConnectionTime + '}';
    }


}
