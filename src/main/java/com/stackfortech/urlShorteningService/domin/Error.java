package com.stackfortech.urlShorteningService.domin;


import java.util.Objects;

public class Error {
	
    private  int errorCode;
    private  String errorMessage;

    public Error withMessage(final String errorMessage){
        this.errorMessage = errorMessage;
        return  this;
    }

    public Error withCode(final int code){
        this.errorCode = code;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "Error{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Error)) return false;
        Error error = (Error) o;
        return errorCode == error.errorCode &&
                errorMessage.equals(error.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, errorMessage);
    }
}

