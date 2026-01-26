package ru.yp.sprint7pw.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yp.sprint7pw.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesService {

    public static final String UPLOAD_DIR = "uploads/";

    public Mono<Product> upload(Product product, FilePart file) {
        try {
            String id = product.getId() + "_" + file.filename();
            Path uploadDir = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Path filePath = uploadDir.resolve(id);

            return file.transferTo(filePath)
                    .thenReturn(product);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Mono<Resource> download(String id) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(id).normalize();
            byte[] content = Files.readAllBytes(filePath);

            return Mono.just(new ByteArrayResource(content));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
