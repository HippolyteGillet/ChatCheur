package model.user;

import java.time.LocalDateTime;

/**
 * User's extended class
 */
public class Moderator extends User{

    public Moderator(int id, String userName, String password, String email, String firstName, String lastName, State state, LocalDateTime lastConnectionTime) {
        super(id, userName, password, email, firstName, lastName, state, lastConnectionTime);
        setPermission(Permission.MODERATOR);
    }

}
