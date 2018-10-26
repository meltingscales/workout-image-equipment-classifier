package scanfit;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ScanFitLib {
    /**
     * @param context The App's context.
     * @param path    Path to resource.
     * @return
     * @throws IOException
     */
    public static String loadJSONFromAsset(Context context, String path) throws IOException {
        String json = null;

        InputStream is = context.getAssets().open(path);

        int size = is.available();

        byte[] buffer = new byte[size];

        is.read(buffer);

        is.close();

        return json;
    }

    public static String stringFromFile(File file) throws FileNotFoundException {
        return new Scanner(file).useDelimiter("\\Z").next();
    }

}
