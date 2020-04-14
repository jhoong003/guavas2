package com.example.guavas.data.entity;

import org.jetbrains.annotations.NotNull;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class Result<T> {
    /**
     * hide the private constructor to limit subclass types (Success, Error)
     */

    private Result() {
    }

    /**
     * Gets the string representation of the <code>Result</code>.
     *
     * @return the string representation of the <code>Result</code>.
     */
    @NotNull
    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success success = (Result.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error) {
            Result.Error error = (Result.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    /**
     * Success sub-class.
     *
     * @param <T> the Type.
     */
    public final static class Success<T> extends Result {
        private T data;

        /**
         * The constructor.
         *
         * @param data the data.
         */
        public Success(T data) {
            this.data = data;
        }

        /**
         * Gets the data.
         *
         * @return the data.
         */
        public T getData() {
            return this.data;
        }
    }

    /**
     * Error sub-class.
     */
    public final static class Error extends Result {
        private Exception error;

        /**
         * The constructor.
         *
         * @param error the error.
         */
        public Error(Exception error) {
            this.error = error;
        }

        /**
         * Gets the exception of the error.
         *
         * @return the thrown exception.
         */
        public Exception getError() {
            return this.error;
        }
    }
}
