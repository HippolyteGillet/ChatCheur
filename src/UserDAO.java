/*import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserDAO extends DAO<User>{

    public User create(User object){
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
    }

    public User find(int id){
        User user = new User();
        return user;
    }



}*/
