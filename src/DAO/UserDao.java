package DAO;

import model.Log;
import model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements DAO<User> {
    private final Connection connect = ConnectionDataBaseSQL.getInstance();

    @Override
    public User create(User object) {
        try {
            //ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE
            //ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM user2");
            //if (!result.first()){}
            //while (result.next()){}
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

            System.out.println("Insertion OK");
            //object = this.find(object.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return object;
    }

    @Override
    public User find(int id) {
        //ConnectionDataBaseSQL.accessDriver();
        User user = new User();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE ID = " + id);
            //ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM user WHERE id = " + id);
            //if (result.first()){}
            while (result.next()) {
                user = new User(id, result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDate.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int newIdUser() {
        int previousId = 0;
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT MAX(id) as id FROM user");
            while (result.next()) previousId = result.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return previousId + 1;
    }

    @Override
    public User update(User object) {
        //ConnectionDataBaseSQL.accessDriver();
        try {
            //ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM user WHERE id = " + object.getId());
            //if (result.first()){}
            this.connect.createStatement().executeUpdate("UPDATE user SET user_name = '" + object.getUserName() +
                    "', first_name = '" + object.getFirstName() + "', last_name = '" + object.getLastName() +
                    "', email = '" + object.getEmail() + "', password = '" + object.getPassword() +
                    "', permission = '" + object.getPermission() + "', last_connection_t = '" + object.getLastConnectionTime() +
                    "', ACCESS = '" + object.getAccess() +
                    "', state = '" + object.getState() +
                    "' WHERE id = " + object.getId());

            System.out.println("Update OK.");

            //else System.out.println("User not found.");

        } catch (SQLException e) {

            e.printStackTrace();

        }
        return object;
    }

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

    public User findUserName(String stringUserName) {
        //ConnectionDataBaseSQL.accessDriver();
        User user = new User();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE USER_NAME = '" + stringUserName + "'");
            //ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM user WHERE id = " + id);
            //if (result.first()){}
            while (result.next()) {
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDate.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Functions for statistics

    public ArrayList<User> findNumberUsersOnline(){
        ArrayList<User> usersOnline = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE STATE = 'ONLINE'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDate.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                usersOnline.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return usersOnline;
    }

    public ArrayList<User> findNumberUsersAway(){
        ArrayList<User> usersAway = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE STATE = 'AWAY'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDate.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                usersAway.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return usersAway;
    }

    public ArrayList<User> findNumberUsersOffline(){
        ArrayList<User> usersOffline = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE STATE = 'OFFLINE'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDate.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                usersOffline.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return usersOffline;
    }

    public ArrayList<User> findNumberUser(){
        ArrayList<User> users = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE PERMISSION = 'USER'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDate.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                users.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public ArrayList<User> findNumberModerator(){
        ArrayList<User> moderators = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE PERMISSION = 'MODERATOR'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDate.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                moderators.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return moderators;
    }

    public ArrayList<User> findNumberAdministrator(){
        ArrayList<User> administrators = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE PERMISSION = 'ADMINISTRATOR'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDate.now());
                user.setPermission(User.Permission.valueOf(result.getString("PERMISSION")));
                user.setAccess(User.Access.valueOf(result.getString("ACCESS")));

                administrators.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return administrators;
    }

    public ArrayList<User> findNumberBanned(){
        ArrayList<User> banned = new ArrayList<>();
        User user = new User();

        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE ACCESS = 'BANNED'");
            while (result.next()){
                user = new User(result.getInt("ID"), result.getString("USER_NAME"), result.getString("PASSWORD"),
                        result.getString("EMAIL"), result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"), User.State.valueOf(result.getString("STATE")),
                        LocalDate.now());
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
