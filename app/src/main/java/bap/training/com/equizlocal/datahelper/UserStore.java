package bap.training.com.equizlocal.datahelper;

/**
 * Created by dvan on 2/11/2017.
 */

public class UserStore {

    private static UserStore sInstance = null;

    public static UserStore sGetInstance() {
        if (sInstance == null) {
            sInstance = new UserStore();
        }
        return sInstance;
    }
}
