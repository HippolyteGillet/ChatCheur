package DAO;

import model.Log;
import model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements DAO<User> {
    private final Connection connect = ConnectionDataBaseSQL.getInstance();

    /**
     * Create a new User with java's data
     * @param object Object is that we want to send in the database
     * @return the created user
     */
    @Override
    public User create(User object) {
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getUserName());
            preparedStatement.setString(3, object.getFirstName());
            preparedStatement.setString(4, object.getLastName());
            preparedStatement.setString(5, object.getEmail());
            preparedStatement.setString(6, object.getPassword());
            preparedStatement.setObject(7, object.getPermission().name());
            preparedStatement.setObject(8, object.getLastConnectionTime());
            preparedStatement.setObject(9, object.getAccess().name());
            preparedStatement.setObject(10, object.getState().name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * Find a user in the database with the ID's user
     * @param id ID is single
     * @return the retrieved user
     */
    @Override
    public User find(int id) {
        User user = new User();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE ID = " + id);
            while (result.next()) {
                user = new User(id, result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDateTime.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Update a user put in the parameters
     * @param object obj is the object that we want tu update in the database
     * @return the updated user in the database
     */
    @Override
    public User update(User object) {
        try {
            this.connect.createStatement().executeUpdate("UPDATE user SET user_name = '" + object.getUserName() +
                    "', first_name = '" + object.getFirstName() + "', last_name = '" + object.getLastName() +
                    "', email = '" + object.getEmail() + "', password = '" + object.getPassword() +
                    "', permission = '" + object.getPermission() + "', last_connection_t = '" + object.getLastConnectionTime() +
                    "', ACCESS = '" + object.getAccess() +
                    "', state = '" + object.getState() +
                    "' WHERE id = " + object.getId());
        } catch (SQLException e) {

            e.printStackTrace();

        }
        return object;
    }

    /**
     * Delete a user with his id
     * @param id The ID is the only identification of the object
     */
    @Override
    public void delete(int id) {
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM user WHERE id = " + id);
            if (result.first()) {
                this.connect.createStatement().executeUpdate("DELETE FROM user WHERE id = " + id);
                System.out.println("Delete completed.");

            } else System.out.println("User doesn't exist.");


        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    /**
     * Retrieve all the users from the database
     * @return all the users
     */
    public List<User> retrieveUsersFromDB() {
        List<User> users = new ArrayList<>();
        int id = 0;
        try {
            ResultSet rs = this.connect.createStatement().executeQuery("SELECT * FROM user");
            do {
                id++;
                if (find(id) != null) {
                    users.add(find(id));
                }
            } while (rs.next());
            //On remove derniere case car null
            users.remove(id-1);
            //On ferme les connections
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Find a user with a username in the parameters of the function
     * @param stringUserName
     * @return the user found
     */
    public User findUserName(String stringUserName) {
        User user = new User();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE USER_NAME = '" + stringUserName + "'");
            while (result.next()) {
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDateTime.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Find the number of the online users in database
     * @return a list with those users
     */
    public ArrayList<User> findNumberUsersOnline(){
        ArrayList<User> usersOnline = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE STATE = 'ONLINE'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDateTime.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                usersOnline.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return usersOnline;
    }

    /**
     * Find the number of the away users in database
     * @return a list with those users
     */
    public ArrayList<User> findNumberUsersAway(){
        ArrayList<User> usersAway = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE STATE = 'AWAY'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDateTime.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                usersAway.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return usersAway;
    }

    /**
     * Find the number of the offline users in database
     * @return a list with those users
     */
    public ArrayList<User> findNumberUsersOffline(){
        ArrayList<User> usersOffline = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE STATE = 'OFFLINE'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDateTime.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                usersOffline.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return usersOffline;
    }

    /**
     * Find the number of the users who are user in database
     * @return a list with those users
     */
    public ArrayList<User> findNumberUser(){
        ArrayList<User> users = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE PERMISSION = 'USER'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDateTime.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                users.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Find the number of the users who are moderator in database
     * @return a list with those users
     */
    public ArrayList<User> findNumberModerator(){
        ArrayList<User> moderators = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE PERMISSION = 'MODERATOR'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDateTime.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                moderators.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return moderators;
    }

    /**
     * Find the number of the users who are administrator in database
     * @return a list with those users
     */
    public ArrayList<User> findNumberAdministrator(){
        ArrayList<User> administrators = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE PERMISSION = 'ADMINISTRATOR'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDateTime.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                administrators.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return administrators;
    }

    /**
     * Find the number of the users who are banned in database
     * @return a list with those users
     */
    public ArrayList<User> findNumberBanned(){
        ArrayList<User> banned = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE ACCESS = 'BANNED'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDateTime.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                banned.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return banned;
    }


}
