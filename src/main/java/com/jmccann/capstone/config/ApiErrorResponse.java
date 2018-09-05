package com.jmccann.capstone.config;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
class ApiErrorResponse {

    private HttpStatus status;
    private int error_code;
    private String message;
    private String detail;

    static final class ApiErrorResponseBuilder {
        private HttpStatus status;
        private int error_code;
        private String message;
        private String detail;

        ApiErrorResponseBuilder() {
        }

        ApiErrorResponseBuilder withStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        ApiErrorResponseBuilder withError_code(int error_code) {
            this.error_code = error_code;
            return this;
        }

        ApiErrorResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        ApiErrorResponseBuilder withDetail(String detail) {
            this.detail = detail;
            return this;
        }

        ApiErrorResponse build() {
            ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
            apiErrorResponse.status = this.status;
            apiErrorResponse.error_code = this.error_code;
            apiErrorResponse.detail = this.detail;
            apiErrorResponse.message = this.message;
            return apiErrorResponse;
        }
    }
}
