package com.rbt.searcher.error;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiErrorResponse {
    private int code;

    private String timestamp;

    private String message;
}
