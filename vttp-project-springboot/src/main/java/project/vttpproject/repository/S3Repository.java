package project.vttpproject.repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class S3Repository {
    
    @Autowired
    private AmazonS3 s3;

    public String saveImage(MultipartFile uploadFile, String userName) throws IOException{

        Map<String, String> userData = new HashMap<>();
        userData.put("uploadedBy", userName);
        userData.put("fileName", uploadFile.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(uploadFile.getContentType());
        metadata.setContentLength(uploadFile.getSize());
        metadata.setUserMetadata(userData); // will set above Map

        String id = UUID.randomUUID().toString().substring(0, 8) + "_" + LocalDate.now();
        // 2nd arg ('key') should be unique

            PutObjectRequest putReq = new PutObjectRequest("csf-xz", "images/%s".formatted(id),
                    uploadFile.getInputStream(), metadata);
            // url = https://csf-xz.sgp1.digitaloceanspaces.com/images/id
            putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putReq);


        return id;

    }
}
