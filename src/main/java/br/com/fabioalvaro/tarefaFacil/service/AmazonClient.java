/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * @author faapereira
 *
 */
@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
    	Regions clientRegion = Regions.US_EAST_1;
        //AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       
        // this.s3client = new AmazonS3Client(credentials);
        
        
        
        this.s3client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();
    }

    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        String fileName ="";
        try {
            File file = convertMultiPartToFile(multipartFile);
            fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            System.out.println("vai fazer o upload no s3");
            System.out.println("FileName: "+ fileName);
            System.out.println(file);
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return fileName;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
    	System.out.println("vai converter");
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
    	UUID uuid = UUID.randomUUID();
    	//return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
        return uuid + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileNameFinal, File fileOriginal) {
    	String fileObjKeyName = fileNameFinal;

        // Upload a file as a new object with ContentType and title specified.
        PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, fileOriginal);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
        metadata.addUserMetadata("title", "someTitle");
        request.setMetadata(metadata);
        s3client.putObject(request);
        System.out.println("ok arquivo enviado com sucesso!");
        
        
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }

}
