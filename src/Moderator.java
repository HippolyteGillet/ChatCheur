import java.time.LocalDate;

public class Moderator extends User{

    Moderator(int id, String userName, String password, String email, String firstName, String lastName, State state, LocalDate lastConnectionTime) {
        super(id, userName, password, email, firstName, lastName, state, lastConnectionTime);
        setStatus(Status.MODERATOR);
        //DAO update:
        //userDAO.update(this);
    }

    public void banUser(User user){
        user.setPermission(Permission.BANNED);
        //DAO update:
        //userDAO.update(user);
    }

    public void unbanUser(User user){
        user.setPermission(Permission.ACCEPTED);
        //DAO update:
        //userDAO.update(user);
    }


}
