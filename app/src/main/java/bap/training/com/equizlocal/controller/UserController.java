package bap.training.com.equizlocal.controller;

import bap.training.com.equizlocal.model.User;
import io.realm.Realm;

/**
 * Created by dvan on 2/11/2017.
 */

public class UserController {
    private static UserController sInstance = null;

    public static UserController sGetInstance() {
        if (sInstance == null) {
            sInstance = new UserController();
        }
        return sInstance;
    }

    public void addUser(String username, String password, Realm mRealm) {
        mRealm.beginTransaction();
        User user = mRealm.createObject(User.class);
        //get id based on max id + 1
        int id = (int) ((Long) mRealm.where(User.class).max(User.FIELD_ID) + 1);
        //set
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        mRealm.commitTransaction();
    }
    //TODO crud một user
}
