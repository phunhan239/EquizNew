package bap.training.com.equizlocal.utlis;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dvan on 2/13/2017.
 */

public class Utils {
    public static String readAssetJSONFile(Context context, String jsonFile) throws IOException {
        AssetManager am = context.getAssets();
        InputStream file = am.open(jsonFile);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }
}
