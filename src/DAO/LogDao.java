package DAO;
import modele.Log;

public interface LogDao {
    Log find(int id);
    void create(Log object);
    void update(Log obj);
    void delete(int id);
}
