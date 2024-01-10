package study.donshin.s3client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.ByteBuffer;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${env.aws.s3.bucket}")
    private String bucket;

    public String upload(
            ByteBuffer fileBody,
            String fileContentType,
            long fileContentLength) {
        String uploadedFileName = getUploadedFileName();
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(uploadedFileName)
                .contentType(fileContentType)
                .contentLength(fileContentLength)
                .build();
        s3Client.putObject(request, RequestBody.fromByteBuffer(fileBody));

        return uploadedFileName;
    }

    private String getUploadedFileName() {
        return UUID.randomUUID().toString();
    }
}
