package webapp;


import org.json.JSONArray;
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

    public JSONObject getJSONObject(HttpServletRequest request) {
        String json = readRequest(request);
        JSONObject jsObject = new JSONObject();
        try {
            jsObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsObject;
    }

    public JSONArray getJSONArray(HttpServletRequest request) {
        String json = readRequest(request);
        JSONArray jsArray = new JSONArray();
        try {
            jsArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsArray;
    }

    private String readRequest(HttpServletRequest request) {
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
        return json;
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

    public void sendJSONArray(JSONArray sendingJsonArray, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(sendingJsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
