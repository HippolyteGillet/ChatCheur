package modele.user;

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

    public enum Permission {
        BANNED,
        ACCEPTED
    }

    public enum State {
        ONLINE,
        AWAY,
        OFFLINE
    }

    public enum Access {
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
        this.permission = Permission.ACCEPTED;
        this.access = Access.USER;
        this.state = state;
        this.lastConnectionTime = lastConnectionTime;

        //DAO update:
        //userDAO.update(this);
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
        //DAO update:
        //userDAO.update(this);
        // OU
        //userDAO.update(userDAO.find(this.getId()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        //DAO update:
        //userDAO.update(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        //DAO update:
        //userDAO.update(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        //DAO update:
        //userDAO.update(this);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        //DAO update:
        //userDAO.update(this);
    }

    public Permission getPermission() {
        return this.permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
        //DAO update:
        //userDAO.update(this);
    }

    public Access getAccess() {
        return this.access;
    }

    public void setAccess(Access access) {
        this.access = access;
        //DAO update:
        //userDAO.update(this);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        //DAO update:
        //userDAO.update(this);
    }

    public LocalDate getLastConnectionTime() {
        return lastConnectionTime;
    }

    public void setLastConnectionTime(LocalDate lastConnectionTime) {
        this.lastConnectionTime = lastConnectionTime;
        //DAO update:
        //userDAO.update(this);
    }

    /*
    public void save(){
        userDAO.save(this);
    }

    public void update(){
        userDAO.update(this);
    }

    public void delete(){
        userDAO.delete(this);
    }

    public static User find(int id){
        return userDAO.find(id);
    }

    */

    public void connect() {
        this.state = State.ONLINE;
        this.lastConnectionTime = LocalDate.now();
        //DAO update:
        //userDAO.update(this);
    }

    public void disconnect() {
        this.state = State.OFFLINE;
        //DAO update:
        //userDAO.update(this);
    }

    public void away() {
        this.state = State.AWAY;
        //DAO update:
        //userDAO.update(this);
    }

    public void back() {
        this.state = State.ONLINE;
        //DAO update:
        //userDAO.update(this);
    }

    public void sendMessage(String message) {
        //TODO
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", permission=" + permission + ", status=" + access + ", state=" + state + ", lastConnectionTime=" + lastConnectionTime + '}';
    }


}
