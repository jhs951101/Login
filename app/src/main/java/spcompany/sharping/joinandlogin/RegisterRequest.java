package spcompany.sharping.joinandlogin;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userGender, Response.Listener<String> listener){
        super(Request.Method.POST, ServerInfo.URL + "/signup.php", listener, null);

        parameters = new HashMap<>();
        parameters.put("username", userID);
        parameters.put("password", userPassword);
        parameters.put("name", userName);
        parameters.put("gender", userGender);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
