package webapp;


import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class InputOutputHandler {

    public JSONObject getJSONString(HttpServletRequest request) {
        String json = "";
        Scanner scanner;
        try {
            scanner = new Scanner(new InputStreamReader(request.getInputStream(), "UTF-8"));
            while (scanner.hasNextLine()) {
                json += scanner.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        JSONObject jsObject = new JSONObject();
        try {
            jsObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsObject;
    }

    public void sendJSONObject(JSONObject sendingJSONObject, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(sendingJSONObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
