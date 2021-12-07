package com.ingenioussys.microfinance.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("response")
    private JsonArray data;

    public Result(Boolean error, String message, JsonArray data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public JsonArray getData() {
        return data;
    }


}

