import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ConnectionDataBaseSQL.accessDriver();
        UserDAO userDAO = new UserDAO();
        User user = new User(userDAO.newIdUser(), "userName", "password", "email", "firstName", "lastName", State.ONLINE, LocalDate.now());

        // Test newIdUser() OK
        //System.out.println(userDAO.newIdUser());

        // Test find() OK
        //System.out.println(userDAO.find(1));

        // Test create() OK
        //userDAO.create(user);

        // Test delete() OK
        //userDAO.delete(userDAO.find(4));

        // Test update()
        //user.setUserName("new_username");
        //System.out.println(user);

        // Test allClose() OK
        userDAO.allClose();

    }
}