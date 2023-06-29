//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of ImageStorageService class.
//**********************************************************************************************************************

package com.zilinli.staybooking.service;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.exception.AwsS3UploadException;

// Framework includes
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

// System includes
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class ImageStorageService {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************

    ImageStorageService(S3Client s3) {
        this.s3 = s3;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    public String save(MultipartFile file) throws AwsS3UploadException {
        String filename = UUID.randomUUID().toString();
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .acl("public-read")
                .contentType("image/jpeg")
                .build();

        try (InputStream is = file.getInputStream()) {
            PutObjectResponse response = s3.putObject(objectRequest,
                    RequestBody.fromInputStream(is, file.getSize()));

            if (response.sdkHttpResponse().statusCode() != 200) {
                throw new IOException("Failed to upload file to S3, status code: " + response.sdkHttpResponse().statusCode());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "https://" + bucketName + ".s3.amazonaws.com/" + filename;
    }

//**********************************************************************************************************************
// * Protected methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    @Value("${s3.bucket}")
    private String bucketName;

    private final S3Client s3;
}
