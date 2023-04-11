package DAO;

import modele.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDAO extends DAO<User> {

/*
    public User create(User object){
        //ConnectionDataBaseSQL.accessDriver();


        try {
            //ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user2");
            //if (!result.first()){}
            while (result.next()){
                PreparedStatement preparedStatement = this.connect.prepareStatement("INSERT INTO user2 VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setInt(1, object.getId());
                preparedStatement.setString(2, object.getUserName());
                preparedStatement.setString(3, object.getFirstName());
                preparedStatement.setString(4, object.getLastName());
                preparedStatement.setString(5, object.getEmail());
                preparedStatement.setString(6, object.getPassword());
                preparedStatement.setObject(7, object.getPermission().name());
                preparedStatement.setObject(8, object.getLastConnectionTime());
                preparedStatement.setObject(9, object.getStatus().name());
                preparedStatement.setObject(10, object.getState().name());

                preparedStatement.executeUpdate();
                object = this.find(object.getId());
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {

            try {
                this.connect.close();
                //this.connect.createStatement().close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return object;
    }
*/

    public User create(User object){
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement("INSERT INTO user2 VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getUserName());
            preparedStatement.setString(3, object.getFirstName());
            preparedStatement.setString(4, object.getLastName());
            preparedStatement.setString(5, object.getEmail());
            preparedStatement.setString(6, object.getPassword());
            preparedStatement.setObject(7, object.getPermission().name());
            preparedStatement.setObject(8, object.getLastConnectionTime());
            preparedStatement.setObject(9, object.getStatus().name());
            preparedStatement.setObject(10, object.getState().name());

            preparedStatement.executeUpdate();

            System.out.println("Insertion OK");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public User find(int id) {
        //ConnectionDataBaseSQL.accessDriver();
        User user = new User();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user2 WHERE id = " + id);
            //if (result.first()){}
            while (result.next()){
                user = new User(id, result.getString("user_name"), result.getString("password"),
                        result.getString("email"), result.getString("first_name"),
                        result.getString("last_name"), State.valueOf(result.getString("state")),
                        LocalDate.now());
            }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                this.connect.close();
                //this.connect.createStatement().close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }

    public int newIdUser(){
        int previousId = 0;
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT MAX(id) as id FROM user2");
            while (result.next()) previousId = result.getInt("id");
        }catch (SQLException e) {
            e.printStackTrace();
        } /*finally {

            try {
                this.connect.close();
                //this.connect.createStatement().close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }*/
        return previousId + 1;
    }

    public User update(User object) {
        //ConnectionDataBaseSQL.accessDriver();
        try {

            this.connect.createStatement().executeUpdate("UPDATE table SET userName = '" + object.getUserName()
                    + "', password = '" + object.getPassword() + "', email = '" + object.getEmail() +
                    "', firstName = '" + object.getFirstName() + "', lastName = '" + object.getLastName() +
                    "', permission = '" + object.getPermission() + "', status = " + object.getStatus() +
                    ", state = '" + object.getState() + "', lastConnectionTime = '" + object.getLastConnectionTime() +
                    "' WHERE id = " + object.getId());

        }catch (SQLException e){

            e.printStackTrace();

        }finally {

            try {
                this.connect.close();
                //this.connect.createStatement().close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return object;
    }

    public void delete(User object) {
        //ConnectionDataBaseSQL.accessDriver();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user2 WHERE id = " + object.getId());
            if (result.first()){
                this.connect.createStatement().executeUpdate("DELETE FROM table WHERE id = " + object.getId());
            }

        }catch (SQLException e){

            e.printStackTrace();

        }finally {

            try {
                this.connect.close();
                //this.connect.createStatement().close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
