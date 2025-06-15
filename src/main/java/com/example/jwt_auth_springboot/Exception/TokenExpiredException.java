package com.example.jwt_auth_springboot.Exception;

public class TokenExpiredException extends RuntimeException{
        // Default constructor
    public TokenExpiredException() {
        super("Token has expired");
    }
    
    // Constructor with custom message
    public TokenExpiredException(String message) {
        super(message);
    }

    // Constructor with custom message and cause (another exception)
    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause (useful if chaining exceptions)
    public TokenExpiredException(Throwable cause) {
        super("Token has expired", cause);
    }

}
