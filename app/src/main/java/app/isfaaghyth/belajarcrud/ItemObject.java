package app.isfaaghyth.belajarcrud;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Isfahani on 30-Jul-16.
 */

public class ItemObject {
    public class ObjectBelajar {
        @SerializedName("belajar")
        public List<Results> belajar;

        public class Results {
            @SerializedName("id")
            public String id;

            @SerializedName("name")
            public String name;

            @SerializedName("office")
            public String office;

            @SerializedName("email")
            public String email;
        }
    }
}
