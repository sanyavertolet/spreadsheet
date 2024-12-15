package io.github.sanyavertolet.interview.exim;

import io.github.sanyavertolet.interview.SpreadsheetDto;
import io.github.sanyavertolet.interview.data.manager.DataManager;
import io.github.sanyavertolet.interview.exceptions.io.ExportException;
import io.github.sanyavertolet.interview.exceptions.io.ImportException;
import io.github.sanyavertolet.interview.exim.adapters.ExImAdapter;
import io.github.sanyavertolet.interview.exim.adapters.JacksonSerializationExImAdapter;
import io.github.sanyavertolet.interview.http.HttpClient;
import io.github.sanyavertolet.interview.modals.SaveOptionDialog;
import io.github.sanyavertolet.interview.modals.SpreadsheetMetadataPicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class MongoCloudExImManager implements ExImManager {
    private final static Logger logger = LoggerFactory.getLogger(MongoCloudExImManager.class);
    private final HttpClient httpClient = new HttpClient();
    private final ExImAdapter adapter = new JacksonSerializationExImAdapter();

    @Override
    public void exportData(DataManager manager) {
        CompletableFuture.supplyAsync(() -> {
            try {
                return httpClient.getSpreadsheetMetadataList();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Could not load data from cloud", e);
            }
        }).thenCompose(metadataList ->
                invokeOnEdt(() -> SaveOptionDialog.askUserForMetadata(metadataList))
        ).thenAccept(metadata -> {
            try {
                String data = adapter.exportData(manager);
                SpreadsheetDto dto = new SpreadsheetDto(metadata, data);
                httpClient.postSpreadsheetDto(dto);
                logger.info("Export completed for metadata: {}", metadata.getId());
            } catch (IOException | InterruptedException | ExportException e) {
                throw new RuntimeException("Could not save data to cloud", e);
            }
        });
    }

    @Override
    public void importData(DataManager manager) {
        CompletableFuture.supplyAsync(() -> {
            try {
                return httpClient.getSpreadsheetMetadataList();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Could not load spreadsheet metadata list from cloud", e);
            }
        }).thenCompose(metadataList ->
                invokeOnEdt(() -> SpreadsheetMetadataPicker.pick(metadataList))
        ).thenCompose(metadata ->
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return httpClient.getSpreadsheetDtoById(metadata.getId());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException("Could not load spreadsheet " + metadata.getId() + " from cloud", e);
                    }
                })
        ).thenAccept(dto -> {
            try {
                adapter.importData(manager, dto.getData());
                logger.info("Import completed for spreadsheet: {}", dto);
            } catch (ImportException e) {
                throw new RuntimeException("Could not import spreadsheet " + dto.getMetadata().getId() + " from cloud", e);
            }
        });
    }

    /**
     * Utility method to schedule the execution of a Supplier of CompletableFuture on the EDT.
     * <p>
     * The Supplier should return a CompletableFuture that completes when the UI task finishes.
     * This method does not block the EDT; it returns a CompletableFuture immediately and completes
     * it when the inner future completes.
     *
     * @param supplier a supplier that returns a CompletableFuture when invoked on the EDT.
     * @param <T>      the type of the result.
     * @return a CompletableFuture that completes when the supplier's returned future completes.
     */
    private static <T> CompletableFuture<T> invokeOnEdt(Supplier<CompletableFuture<T>> supplier) {
        CompletableFuture<T> result = new CompletableFuture<>();
        SwingUtilities.invokeLater(() -> {
            try {
                CompletableFuture<T> innerFuture = supplier.get();
                innerFuture.whenComplete((value, ex) -> {
                    if (ex == null) {
                        result.complete(value);
                    } else {
                        result.completeExceptionally(ex);
                    }
                });
            } catch (Throwable t) {
                result.completeExceptionally(t);
            }
        });
        return result;
    }
}
