package ru.yandex.practicum.statsclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.yandex.practicum.statsdto.dto.EndpointHitDto;
import ru.yandex.practicum.statsdto.dto.ViewStatDto;

import java.util.List;
import java.util.Map;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<List<ViewStatDto>> get(String path, @Nullable Map<String, Object> parameters) {
        return statSendRequest(path, parameters);
    }

    protected <T> ResponseEntity<EndpointHitDto> post(String path, T body) {
        return hitSendRequest(path, body);
    }
    private <T> ResponseEntity<EndpointHitDto> hitSendRequest(String path, T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body);
        ResponseEntity<EndpointHitDto> statServerResponse;
        try {
            statServerResponse = rest.exchange(path, HttpMethod.POST, requestEntity, EndpointHitDto.class);
        } catch (HttpStatusCodeException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getStatusText());
        }
        return statServerResponse;
    }


    private ResponseEntity<List<ViewStatDto>> statSendRequest(String path, @Nullable Map<String, Object> parameters) {
        ResponseEntity<List<ViewStatDto>> statServerResponse;
        try {
            if (parameters != null) {
                statServerResponse = rest.exchange(path, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                }, parameters);
            } else {
                statServerResponse = rest.exchange(path, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
            }
        } catch (HttpStatusCodeException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getStatusText());
        }
        return statServerResponse;
    }

    private HttpHeaders defaultHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (userId != null) {
            headers.set("X-Sharer-User-Id", String.valueOf(userId));
        }
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
