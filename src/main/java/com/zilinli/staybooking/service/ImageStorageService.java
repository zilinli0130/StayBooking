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
import com.zilinli.staybooking.exception.GCSUploadException;

// Framework includes
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// System includes
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class ImageStorageService {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************

    ImageStorageService(Storage storage) {
        this.storage = storage;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    public String save(MultipartFile file) throws GCSUploadException {
        String filename = UUID.randomUUID().toString();
        BlobInfo blobInfo = null;
        try {
            blobInfo = storage.createFrom(
                    BlobInfo
                            .newBuilder(bucketName, filename)
                            .setContentType("image/jpeg")
                            .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                            .build(),
                    file.getInputStream());
        } catch (IOException exception) {
            throw new GCSUploadException("Failed to upload file to GCS");
        }
        return blobInfo.getMediaLink();
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
    @Value("${gcs.bucket}")
    private String bucketName;

    private final Storage storage;
}
