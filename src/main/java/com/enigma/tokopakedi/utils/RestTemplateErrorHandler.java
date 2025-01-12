package com.enigma.tokopakedi.utils;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String body = readResponseBody(response);
        throw new ResponseStatusException(response.getStatusCode(), body);
    }

    public String readResponseBody(ClientHttpResponse response) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody()))) {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                body.append(line);
            }
            return body.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
