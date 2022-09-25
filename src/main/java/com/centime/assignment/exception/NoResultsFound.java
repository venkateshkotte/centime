package com.centime.assignment.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoResultsFound extends Exception {
    private String message;
    private HttpStatus httpStatus;
}
