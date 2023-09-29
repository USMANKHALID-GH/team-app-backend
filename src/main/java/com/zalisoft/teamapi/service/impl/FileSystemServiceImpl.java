package com.zalisoft.teamapi.service.impl;



import com.zalisoft.teamapi.enums.ResponseMessageEnum;
import com.zalisoft.teamapi.exception.BusinessException;
import com.zalisoft.teamapi.service.FileSystemService;
import org.springframework.stereotype.Service;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;


import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.zalisoft.teamapi.util.General.checkIfIsImage;

@Service
public class FileSystemServiceImpl implements FileSystemService {

    private Storage storage;

    @Value("${gcs.credential}")
    private String credentials;
    @Value("${gcs.bucket_name}")
    private String imageStorage;

    @PostConstruct
    private void init() throws IOException {
        Credentials gcsCredentials = GoogleCredentials.fromStream(IOUtils.toInputStream(credentials));
        storage = StorageOptions.newBuilder().setCredentials(gcsCredentials).build().getService();
    }

    @Override
    public String saveImage(MultipartFile file) throws IOException {
//        String file1=imageName(file);
        BlobId id= BlobId.of(imageStorage, imageName(file));

        BlobInfo blobInfo = BlobInfo.newBuilder(id).build();
//        String extion=file1.substring(file1.lastIndexOf("."),file1.length());

        Blob blob = storage.create(blobInfo, file.getBytes(),Storage.BlobTargetOption.doesNotExist());

        if (blob ==null){
            throw new BusinessException(ResponseMessageEnum.BACK_FILE_MSG_001);
        }
        return  blob.getMediaLink();
    }


    @Override
    public void deleteImage(String file) {
        String fileName=file.substring(file.lastIndexOf("/o/"), file.lastIndexOf("?")).substring(3);

        BlobId id=BlobId.of(imageStorage,fileName);
        Blob blod=storage.get(id);
        if(blod==null){
            throw new BusinessException(ResponseMessageEnum.BACK_FILE_MSG_002);
        }
        blod.delete();

    }


    private String imageName(MultipartFile file){
        String fileName= file.getOriginalFilename();
        checkIfIsImage(fileName);
        String newFileName=fileName.substring(fileName.lastIndexOf("."),fileName.length());
        return UUID.randomUUID()+fileName+newFileName;
    }

}
