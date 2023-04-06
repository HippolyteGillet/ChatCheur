import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class UserDAO extends DAO<User>{

    public User create(User object){
        try {
            ResultSet result = this.connect.createStatement(/*ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE*/).executeQuery("SELECT id FROM table");
            if (!result.first()){
                PreparedStatement preparedStatement = this.connect.prepareStatement("INSERT INTO table (*) VALUES ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
                preparedStatement.setInt(1, object.getId());
                preparedStatement.setString(2, object.getUserName());
                preparedStatement.setString(3, object.getPassword());
                preparedStatement.setString(4, object.getEmail());
                preparedStatement.setString(5, object.getFirstName());
                preparedStatement.setString(6, object.getFirstName());
                preparedStatement.setObject(7, object.getPermission());
                preparedStatement.setObject(8, object.getStatus());
                preparedStatement.setObject(9, object.getState());
                preparedStatement.setObject(10, object.getLastConnectionTime());

                preparedStatement.executeUpdate();
                object = this.find(object.getId());
            }
        }catch (SQLException e) {

            e.printStackTrace();
        }finally {

            try {
                this.connect.close();
                this.connect.createStatement().close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return object;
    }

    public User find(int id){
        User user = new User();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM table WHERE id = " + id);
            if (result.first()){
                user = new User(id, result.getString("userName"), result.getString("password"),
                        result.getString("email"), result.getString("firstName"),
                        result.getString("lastName"), (State) result.getObject("state"),
                        (LocalDate) result.getObject("lastConnectionTime"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {

            try {
                this.connect.close();
                this.connect.createStatement().close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }

    public User update(User object) {
        try {

            this.connect.createStatement().executeUpdate("UPDATE table SET userName = '" + object.getUserName() + "', password = '" + object.getPassword() + "', email = '" + object.getEmail() + "', firstName = '" + object.getFirstName() + "', lastName = '" + object.getLastName() + "', permission = '" + object.getPermission() + "', status = " + object.getStatus() + ", state = '" + object.getState() + "', lastConnectionTime = '" + object.getLastConnectionTime() + "' WHERE id = " + object.getId());

        }catch (SQLException e){

            e.printStackTrace();

        }finally {

            try {
                this.connect.close();
                this.connect.createStatement().close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return object;
    }

    public void delete(User object) {
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM table WHERE id = " + object.getId());
            if (result.first()){
                this.connect.createStatement().executeUpdate("DELETE FROM table WHERE id = " + object.getId());
            }

        }catch (SQLException e){

            e.printStackTrace();

        }finally {

            try {
                this.connect.close();
                this.connect.createStatement().close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
