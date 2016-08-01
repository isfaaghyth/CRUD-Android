package app.isfaaghyth.belajarcrud;

import android.net.Uri;

/**
 * Created by Isfahani on 31-Jul-16.
 */
public class URLServices {
    public static String URL_SHOW = "http://dev.daeng.id/crud/show.php";
    public static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    public static String URL_INSERT(String name, String office, String email) {
        return Uri.encode("http://dev.daeng.id/crud/insert.php?name=" + name + "&office=" + office + "&email=" + email, ALLOWED_URI_CHARS);
    }

    public static String URL_DELETE(String id) {
        return "http://dev.daeng.id/crud/delete.php?id=" + id;
    }

    public static String URL_MODIFY(String id, String name, String office, String email) {
        return Uri.encode("http://dev.daeng.id/crud/modify.php?id=" + id + "&name=" + name + "&office=" + office + "&email=" + email, ALLOWED_URI_CHARS);
    }
}
