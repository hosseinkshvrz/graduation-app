package webapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hossein on 11/02/2018.
 */
public class ProfileFieldChecker {
    private final int MINIMUM_BIRTH_YEAR = 1300;
    private final int MAXIMUM_BIRTH_YEAR = 1390;
    public String checkFields(JSONObject readingJSONObject) {
        String responseMessage = "";
        try {
            if (readingJSONObject.getString("firstName").isEmpty()) {
                responseMessage = "empty first name field";
            }
            else if (readingJSONObject.getString("firstName").matches("\"^[\\u0600-\\u06FF\\uFB8A\\u067E\\u0686\\u06AF\\u200C\\u200F ]+$\"")) {
                responseMessage = "نام باید به حروف فارسی باشد";
            }
            else if (readingJSONObject.getString("lastName").isEmpty()) {
                responseMessage = "empty last name field";
            }
            else if (readingJSONObject.getString("lastName").matches("\"^[\\u0600-\\u06FF\\uFB8A\\u067E\\u0686\\u06AF\\u200C\\u200F ]+$\"")) {
                responseMessage = "نام خانوادگی باید به حروف فارسی باشد";
            }
            else if (readingJSONObject.getString("studentID").isEmpty()) {
                responseMessage = "empty student id field";
            }
            else if (!readingJSONObject.getString("studentID").matches("[0-9]{8}")) {
                responseMessage = "شماره دانشجویی وارد شده صحیح نیست";
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[0]) < MINIMUM_BIRTH_YEAR
                    || Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[0]) > MAXIMUM_BIRTH_YEAR) {
                responseMessage = "سال تولد نامعتبر است";
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) < 1
                    || Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) > 12) {
                responseMessage = "ماه تولد نامعتبر است";
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]) < 1) {
                responseMessage = "روز تولد نامعتبر است";
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]) == 31) {
                if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) > 6) {
                    responseMessage = "روز تولد نامعتبر است";
                }
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]) > 30) {
                if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) > 6) {
                    responseMessage = "روز تولد نامعتبر است";
                }
            }
            else if(readingJSONObject.getString("email").isEmpty()) {
                responseMessage = "empty email field";
            }
            else if(!readingJSONObject.getString("email").matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                responseMessage = "فرمت ایمیل صحیح نیست";
            }
            else if (readingJSONObject.getString("password").isEmpty()) {
                responseMessage = "empty password field";
            }
            else if (readingJSONObject.getString("confirmPassword").isEmpty()) {
                responseMessage = "empty confirm password field";
            }
            else if (!readingJSONObject.getString("password").equals(readingJSONObject.getString("confirmPassword"))) {
                responseMessage = "کلمه عبور باید عیناً تکرار شود";
            }
            else if (readingJSONObject.getString("password").length() < 8 || readingJSONObject.getString("password").length() > 22) {
                responseMessage = "طول کلمه عبور باید حداقل ۸ و حداکثر ۲۲ حرف باشد";
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }
}
