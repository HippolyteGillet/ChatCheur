package model.user;

import model.Message;

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

    /**
     * Automatic constructor mainly for the creation of a user in the DAO's classes
     */
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

    /**
     * We decrypt a message with all parameters of an administrator, a moderator or a user, and we create the user
     * @return a type (ADMINISTRATOR, MODERATOR, USER)
     */
    public static User convertionMessageIntoUser(String[] user) {
        String[] realUser = new String[15];
        //For each user cell, we get the value
        for (int i = 0; i < user.length - 5; i++) {
            //The values to be recovered are from box 5
            //Separate the string with the '='.
            String temp = user[i + 5].split("=")[1];
            //Remove the trailing comma
            realUser[i] = temp.substring(0, temp.length() - 1);
        }
        if (user[0].equals("Connection:")){
            realUser[8] = "ONLINE";
        }
        try {
            switch (realUser[6]) {
                case "ADMINISTRATOR" -> {
                    return new Administrator(Integer.parseInt(realUser[0]), realUser[1], realUser[2], realUser[3], realUser[4], realUser[5], User.State.valueOf(realUser[8]), LocalDateTime.now());
                }
                case "MODERATOR" -> {
                    return new Moderator(Integer.parseInt(realUser[0]), realUser[1], realUser[2], realUser[3], realUser[4], realUser[5], User.State.valueOf(realUser[8]), LocalDateTime.now());
                }
                case "USER" -> {
                    return new User(Integer.parseInt(realUser[0]), realUser[1], realUser[2], realUser[3], realUser[4], realUser[5], User.State.valueOf(realUser[8]), LocalDateTime.now());
                }
            }
        } catch (Exception e) {
            System.out.println("Error in converting message to user");
            return null;
        }
        return null;
    }

    /**
     * We decrypt a message with all parameters of a message, and we create a new message
     * @return a MESSAGE
     */
    public static Message convertionMessageIntoMessage(String[] message) {
        String[] realMessage = new String[15];
        for (int i = 0; i < message.length - 4; i++) {
            //La fin est différente pour les messages avec espaces
            if (i > 3) {
                realMessage[3] += " " + message[i+4];
            } else if (i == 3) {
                realMessage[i] = message[i + 4].split("=")[1];

            }else{
                //Les valeurs à récupérer sont à partir de la case 4
                //On sépare le string avec le '='
                String temp = message[i + 4].split("=")[1];
                //On enlève la virgule de fin
                realMessage[i] = temp.substring(0, temp.length()-1);
            }

        }
        return new Message(Integer.parseInt(realMessage[0]), Integer.parseInt(realMessage[1]), LocalDateTime.now(), realMessage[3]);
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
     * getters and setters for username
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }

    /**
     * getters and setters for password
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    /**
     * getters and setters for email
     */
    public String getEmail() {
        return email;
    }

    /**
     * getters and setters for first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * getters and setters for last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * getters and setters for permission
     */
    public Permission getPermission() {
        return this.permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;

    }

    /**
     * getters and setters for access
     */
    public Access getAccess() {
        return this.access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    /**
     * getters and setters for state
     */
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;

    }

    /**
     * getters and setters for the last connection
     */
    public LocalDateTime getLastConnectionTime() {
        return lastConnectionTime;
    }

    public void setLastConnectionTime(LocalDateTime lastConnectionTime) {
        this.lastConnectionTime = lastConnectionTime;

    }

    /**
     * To string method
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", permission=" + permission + ", status=" + access + ", state=" + state + ", lastConnectionTime=" + lastConnectionTime + '}';
    }

    /**
     * All the enums
     */
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
}
