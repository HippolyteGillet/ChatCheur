package model.user;

import java.time.LocalDateTime;

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
    private LocalDateTime lastConnectionTime;

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

    public User(int id, String userName, String password, String email, String firstName, String lastName, State state, LocalDateTime lastConnectionTime) {
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

    public LocalDateTime getLastConnectionTime() {
        return lastConnectionTime;
    }

    public void setLastConnectionTime(LocalDateTime lastConnectionTime) {
        this.lastConnectionTime = lastConnectionTime;

    }

    public void connect() {
        this.state = State.ONLINE;
        this.lastConnectionTime = LocalDateTime.now();
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

    public static User convertionMessageIntoUser(String[] user) {
        String[] realUser = new String[100];
        for (int i = 0; i < user.length-5; i++) {
            String temp = user[i + 5].split("=")[1];
            realUser[i] = temp.substring(0,temp.length()-1);
        }
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXXXXx");
        //LocalDateTime localDateTime = LocalDateTime.parse(realUser[9], formatter);
        return new User(Integer.parseInt(realUser[0]), realUser[1], realUser[2], realUser[3], realUser[4], realUser[5], User.State.valueOf(realUser[8]), LocalDateTime.now());
    }
}
