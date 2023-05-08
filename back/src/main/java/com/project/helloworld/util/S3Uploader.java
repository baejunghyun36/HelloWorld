//package com.project.helloworld.util;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Optional;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class S3Uploader {
//    private final AmazonS3Client amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public String uploadFiles(MultipartFile multipartFile, String dirName) throws IOException {
//        File uploadFile = convert(multipartFile).orElseThrow(()-> new IllegalArgumentException("MultipartFile -> File convert fail"));
//        return upload(uploadFile,dirName);
//    }
//
//    public String upload(File uploadFile, String filePath){
//        String fileName = filePath + "/" + UUID.randomUUID() + uploadFile.getName();
//        String uploadImageUrl = putS3(uploadFile,fileName);
//        removeNewFile(uploadFile);
//        return uploadImageUrl;
//    }
//
//    private String putS3(File uploadFile, String fileName){
//        amazonS3Client.putObject(new PutObjectRequest(bucket,fileName,uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket,fileName).toString();
//    }
//
//    private boolean removeNewFile(File targetFile){
//        if(targetFile.delete()){
//            return true;
//        }
//        return false;
//    }
//
//    private Optional<File> convert(MultipartFile file) throws IOException{
//        File convertFile = new File(System.getProperty("main/resources/img") + "/" + file.getOriginalFilename());
//        if(convertFile.createNewFile()){
//            try(FileOutputStream fos = new FileOutputStream(convertFile)){
//                fos.write(file.getBytes());
//            }
//            return Optional.of(convertFile);
//        }
//        return Optional.empty();
//    }
//}
