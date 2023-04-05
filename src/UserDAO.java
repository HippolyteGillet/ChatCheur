import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class UserDAO extends DAO<User>{

    /*public User create(User object){
        try {
            ResultSet result = this.connect.createStatement(*//*ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE*//*).executeQuery("SELECT id FROM table");
            if (result.first()){
                int id = result.getInt("id");
                PreparedStatement preparedStatement = this.connect.prepareStatement("INSERT INTO table (id) VALUES ?");
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
                object = this.find(id);
            }
        }catch (SQLException e) {

            e.printStackTrace();
        }
        return object;
    }*/

    /*public User find(int id){
        User user = new User();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("");
            if (result.first()){
                user = new User(id, result.getString("userName"), result.getString("password"), result.getString("email"), result.getString("firstName"), result.getString("lastName"), result.getObject("state", ))
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }*/

    public User update(User object) {
        try {
            this.connect.createStatement().executeUpdate("UPDATE table SET userName = '" + object.getUserName() + "', password = '" + object.getPassword() + "', email = '" + object.getEmail() + "', firstName = '" + object.getFirstName() + "', lastName = '" + object.getLastName() + "', permission = '" + object.getPermission() + "', status = '" + object.getStatus() + "', state = '" + object.getState() + "', lastConnectionTime = '" + object.getLastConnectionTime() + "' WHERE id = " + object.getId());
        }catch (SQLException e){
            e.printStackTrace();
        }
        return object;
    }

    public void delete(User object) {}




}
