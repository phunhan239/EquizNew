package bap.training.com.equizlocal.model;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by dvan on 2/11/2017.
 */

public class User extends RealmObject {
    public static final String DATABASE_NAME = "Quiz";
    public static final String TABLE_NAME = "User";
    public static final String FIELD_ID = "id";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
