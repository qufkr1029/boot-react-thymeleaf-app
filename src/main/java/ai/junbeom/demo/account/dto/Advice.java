package ai.junbeom.demo.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Advice(String message, String author) {
}
