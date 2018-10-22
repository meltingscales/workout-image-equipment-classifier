package scanfit;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

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

    /**
     * A version of {@link #loadJSONFromAsset(Context, String)} that is meant for test cases on the desktop.
     *
     * @param path URI to file.
     * @return JSON contents of the file.
     * @throws IOException
     */
    public static String loadJSONFromAsset(String path) throws IOException {
        String json = null;


        return "{\"ain't\": \"implemented\"}";
    }

}
