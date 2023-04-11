package modele;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.*;

public class Log {
    private int id;
    private int user_id;
    private LocalDateTime localDateTime;
    private TypeLog type;
    public enum TypeLog {
        CONNECTION, DISCONNECTION, MESSAGE, UNKNOWN;
    }
    //Constructeurs
    public Log() {
        this.id = -1;
        this.user_id = -1;
        this.type = TypeLog.UNKNOWN;
        this.localDateTime = now();
    }
    public Log(int user_id, TypeLog type) {
        this.user_id = user_id;
        this.type = type;
        this.localDateTime = now();
    }
    public Log(int id, int user_id, LocalDateTime time, TypeLog type) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.localDateTime = time;
    }
    public Log(Log l){
        this.id = l.id;
        this.user_id = l.user_id;
        this.type = l.type;
        this.localDateTime = l.localDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public TypeLog getType() {
        return type;
    }

    public void setType(TypeLog type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Id : " + this.id + "| User_id : " + this.user_id + " | TimeStamp : " + this.localDateTime + " | Type : " + this.type;
    }
}
