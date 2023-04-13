package DAO;

import modele.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserDAO implements DAO<User>{

    public User create(User object){
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

    public User find(int id) {
        //ConnectionDataBaseSQL.accessDriver();
        User user = new User();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM user WHERE id = " + id);
            //ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM user WHERE id = " + id);
            //if (result.first()){}
            while (result.next()){
                user = new User(id, result.getString("user_name"), result.getString("password"),
                        result.getString("email"), result.getString("first_name"),
                        result.getString("last_name"), User.State.valueOf(result.getString("state")),
                        LocalDate.now());
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int newIdUser(){
        int previousId = 0;
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT MAX(id) as id FROM user");
            while (result.next()) previousId = result.getInt("id");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return previousId + 1;
    }

    public User update(User object) {
        //ConnectionDataBaseSQL.accessDriver();
        try {
            //ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM user WHERE id = " + object.getId());
            //if (result.first()){}
                this.connect.createStatement().executeUpdate("UPDATE user SET user_name = '" + object.getUserName() +
                        "', first_name = '" + object.getFirstName() + "', last_name = '" + object.getLastName() +
                        "', email = '" + object.getEmail()  + "', password = '" + object.getPassword() +
                        "', permission = '" + object.getPermission() + "', last_connection_t = '" + object.getLastConnectionTime() +
                        "', ACCESS = '" + object.getAccess() +
                        "', state = '" + object.getState() +
                        "' WHERE id = " + object.getId());

                System.out.println("Update OK.");

            //else System.out.println("User not found.");

        }catch (SQLException e){

            e.printStackTrace();

        }
        return object;
    }

    public void delete(User object){
        try {
            ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT * FROM user WHERE id = " + object.getId());
            if (result.first()){
                this.connect.createStatement().executeUpdate("DELETE FROM user WHERE id = " + object.getId());
                System.out.println("Delete completed.");

            }

            else System.out.println("User doesn't exist.");


        }catch (SQLException e){

            e.printStackTrace();

        }
    }

    public void allClose(){
        try {
            this.connect.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // TODO update() | findPassword() | findUserName
}
