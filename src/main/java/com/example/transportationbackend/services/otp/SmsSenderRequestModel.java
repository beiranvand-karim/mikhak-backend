package com.example.transportationbackend.services.otp;

import lombok.Getter;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Getter
public class SmsSenderRequestModel {

    private String username;
    private String password;
    private String from;
    private String url;
    private String number;
    private JSONObject json;
    private String text;
    private String otp;

    public SmsSenderRequestModel(String username, String password, String from, String url) {
        this.username = username;
        this.password = password;
        this.from = from;
        this.url = url;
        text = " کد تایید شما : otp " +
                "سپاس از همراهی شما";
        json = new JSONObject();
    }

    public void setReceiverMessageInfo(String number) {
        this.number = number;
        this.text = this.text.replace("otp", otp);
        createJson();
    }

    private void createJson() {
        try {
            json.put("username", username);
            json.put("password", password);
            json.put("from", from);
            json.put("text", text);
            json.put("to", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return json.toString();
    }

    public JSONObject getJson() {
        return json;
    }

    public void setOtp(int otp) {
        this.otp = String.valueOf(otp);
    }
}
