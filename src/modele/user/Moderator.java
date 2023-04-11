package modele.user;

import java.time.LocalDate;

public class Moderator extends User{

    Moderator(int id, String userName, String password, String email, String firstName, String lastName, State state, LocalDate lastConnectionTime) {
        super(id, userName, password, email, firstName, lastName, state, lastConnectionTime);
        setStatus(Status.MODERATOR);
        //java.DAO update:
        //userDAO.update(this);
    }

    public void banUser(User user){
        user.setPermission(Permission.BANNED);
        //java.DAO update:
        //userDAO.update(modele.modele.user);
    }

    public void unbanUser(User user){
        user.setPermission(Permission.ACCEPTED);
        //java.DAO update:
        //userDAO.update(modele.modele.user);
    }


}
