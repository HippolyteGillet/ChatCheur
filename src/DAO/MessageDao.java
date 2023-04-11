package DAO;

import modele.Log;
import modele.Message;

public interface MessageDao {
    Message find(int id);
    void create(Message object);
    void update(Message obj);
    void delete(int id);
}
