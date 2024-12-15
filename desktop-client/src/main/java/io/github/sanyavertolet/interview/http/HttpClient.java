package io.github.sanyavertolet.interview.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sanyavertolet.interview.SpreadsheetDto;
import io.github.sanyavertolet.interview.SpreadsheetMetadata;
import io.github.sanyavertolet.interview.modals.LoadingDialog;
import io.github.sanyavertolet.interview.serialization.JsonConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * A simple HTTP client for interacting with a remote spreadsheet service.
 *
 * <p>This class uses Java's built-in {@link java.net.http.HttpClient} to send requests
 * and receive responses from a server. It leverages Jackson's {@link ObjectMapper} to
 * serialize and deserialize JSON data into Java objects and vice versa.</p>
 *
 * <p>Each public method is responsible for one type of operation against the server:
 * loading a list of spreadsheet metadata, fetching a single spreadsheet by its ID,
 * and posting new spreadsheet data. A {@link LoadingDialog} is displayed while each
 * request is processed, providing a visual cue to the user that the operation is in
 * progress.</p>
 *
 * <p>Common error handling is done by throwing a {@code RuntimeException} when the HTTP
 * status code is not 200, indicating a failure in communicating with the server.</p>
 */
public class HttpClient {
    private final java.net.http.HttpClient client;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "http://localhost:8080/api/v1";

    /**
     * Constructs a new {@code HttpClient} instance.
     *
     * <p>This constructor creates a default HTTP client and obtains a pre-configured
     * {@code ObjectMapper} from {@link JsonConfig#createObjectMapper()}.</p>
     */
    public HttpClient() {
        client = java.net.http.HttpClient.newHttpClient();
        objectMapper = JsonConfig.createObjectMapper();
    }

    /**
     * Retrieves a list of {@link SpreadsheetMetadata} from the remote server.
     *
     * <p>This method sends a GET request to the server endpoint that lists all spreadsheets.
     * A {@link LoadingDialog} is shown during the request, and hidden once a response is received.
     * If the response is successful (status 200), the JSON payload is deserialized into a
     * {@code List<SpreadsheetMetadata>}. Otherwise, a {@link RuntimeException} is thrown.</p>
     *
     * @return a list of {@link SpreadsheetMetadata} instances.
     * @throws IOException if an I/O error occurs when sending or receiving data.
     * @throws InterruptedException if the operation is interrupted.
     */
    public List<SpreadsheetMetadata> getSpreadsheetMetadataList() throws IOException, InterruptedException {
        LoadingDialog loadingDialog = new LoadingDialog("Loading spreadsheet metadata entries...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/sheets"))
                .GET()
                .build();

        loadingDialog.showDialog();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } finally {
            loadingDialog.hideDialog();
        }

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } else {
            throw new RuntimeException("Failed to fetch list: " + response.statusCode());
        }
    }

    /**
     * Fetches a specific spreadsheet by its ID.
     *
     * <p>This method sends a GET request to the server endpoint that returns a single spreadsheet's data.
     * The {@link LoadingDialog} is shown while waiting for the response. On a successful (200) response,
     * the JSON payload is deserialized into a {@link SpreadsheetDto}. On failure, a
     * {@link RuntimeException} is thrown.</p>
     *
     * @param id the unique identifier of the spreadsheet to fetch.
     * @return a {@link SpreadsheetDto} representing the requested spreadsheet.
     * @throws IOException if an I/O error occurs during the communication.
     * @throws InterruptedException if the operation is interrupted.
     */
    public SpreadsheetDto getSpreadsheetDtoById(String id) throws IOException, InterruptedException {
        LoadingDialog loadingDialog = new LoadingDialog("Loading spreadsheet data for " + id + "...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/sheets/" + id))
                .GET()
                .build();

        loadingDialog.showDialog();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } finally {
            loadingDialog.hideDialog();
        }

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } else {
            throw new RuntimeException("Failed to fetch spreadsheet: " + response.statusCode());
        }
    }

    /**
     * Posts a {@link SpreadsheetDto} to the server to either create a new spreadsheet or update an existing one.
     *
     * <p>This method sends a POST request with a JSON payload representing the spreadsheet data. If the
     * {@link SpreadsheetMetadata#getId()} is empty, the method assumes a new spreadsheet is being created,
     * and the request is sent to the base endpoint. If the ID is present, the method assumes an update
     * operation and updates <b>only spreadsheet data with no metadata</b>.</p>
     *
     * <p>A {@link LoadingDialog} is displayed while the operation is in progress to provide feedback to the user.
     * On a successful response (status code 200), the server's JSON response is deserialized into a
     * {@link SpreadsheetDto} object and returned. If the operation fails (non-200 status), a
     * {@link RuntimeException} is thrown with the status code.</p>
     *
     * @param spreadsheetDto the {@link SpreadsheetDto} containing the spreadsheet data to be saved or updated.
     * @return the {@link SpreadsheetDto} returned by the server after the operation is completed.
     * @throws IOException if an I/O error occurs while sending or receiving data.
     * @throws InterruptedException if the operation is interrupted.
     */
    @SuppressWarnings("UnusedReturnValue")
    public SpreadsheetDto postSpreadsheetDto(SpreadsheetDto spreadsheetDto) throws IOException, InterruptedException {
        LoadingDialog loadingDialog = new LoadingDialog("Saving spreadsheet " + spreadsheetDto.getMetadata().getName() + "...");
        boolean isNewSpreadsheet = spreadsheetDto.getMetadata().getId().isEmpty();
        HttpRequest request;
        if (isNewSpreadsheet) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/sheets"))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(spreadsheetDto)))
                    .header("Content-Type", "application/json")
                    .build();
        } else {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/sheets/" + spreadsheetDto.getMetadata().getId()))
                    .POST(HttpRequest.BodyPublishers.ofString(spreadsheetDto.getData()))
                    .header("Content-Type", "application/json")
                    .build();
        }

        loadingDialog.showDialog();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } finally {
            loadingDialog.hideDialog();
        }

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), SpreadsheetDto.class);
        } else {
            throw new RuntimeException("Failed to post data: " + response.statusCode());
        }
    }
}
