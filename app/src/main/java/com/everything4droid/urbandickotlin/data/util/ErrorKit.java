package com.everything4droid.urbandickotlin.data.util;

/**
 * Created by Khajiev Nizomjon on 05/10/2018.
 */
public class ErrorKit extends Throwable{

    private ERROR_STATUS errorStatus;
    private String message;

    public ErrorKit(ERROR_STATUS errorStatus, String message) {
        this.errorStatus = errorStatus;
        this.message = message;
    }

    public ERROR_STATUS getErrorStatus() {
        return errorStatus;
    }

    public ErrorKit(ERROR_STATUS errorStatus) {
        this.errorStatus = errorStatus;
    }

    public ErrorKit(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
