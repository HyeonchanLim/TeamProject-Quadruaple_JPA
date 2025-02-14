package com.green.project_quadruaple.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Component //빈등록
public class MyFileUtils {
     private final String uploadPath;

    public String getUploadPath() {
        return uploadPath;
    }

    /*
            @Value("${file.directory}")은
            yaml 파일에 있는 file.directory 속성에 저장된 값을 생성자 호출할 때 값을 넣어준다.
          */
     public MyFileUtils(@Value("${file.directory}") String uploadPath) {
         log.info("MyfileUtils - 생성자: {}", uploadPath);
         this.uploadPath = uploadPath;
     }

     //디렉토리 생성
     public String makeFolders(String path) {
         File file = new File(uploadPath, path);
//            if(!file.exists()) {
//             file.mkdirs();
//         }
         file.mkdirs();
         return file.getAbsolutePath();
     }

     //파일명에서 확장자 추출
    public String getExt(String filaName) {
         int lastIdx = filaName.lastIndexOf(".");
         return filaName.substring(lastIdx);
    }

    //랜덤 파일명 생성
    public String makeRandomFileName() {
         return UUID.randomUUID().toString();
    }

    //랜덤 파일명 + 확장자 생성하여 리턴
    //오버로딩
    public String makeRandomFileName(String originalFileName) {
         return makeRandomFileName() + getExt(originalFileName);
    }

    public String makeRandomFileName(MultipartFile pic) {
        return makeRandomFileName(pic.getOriginalFilename());
    }

    //파일을 원하는 경로에 저장(이동)
    public void transferTo(MultipartFile mf, String path) throws IOException {
         File file = new File(uploadPath, path);
         mf.transferTo(file);
    }

    //파일을 원하는 경로에 저장(복사)
    public void copyFolder(Path source, Path destination) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetPath = destination.resolve(source.relativize(dir));
                if (!Files.exists(targetPath)) {
                    Files.createDirectories(targetPath);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path targetPath = destination.resolve(source.relativize(file));
                Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }
    //폴더 내 파일 갯수 체크
    public static long countFiles(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
         if (!Files.exists(path) || !Files.isDirectory(path)) {
            return 0;
        }
        try (Stream<Path> files = Files.list(path)) {
            return files.count();
        }
    }

    //폴더 지우기, ex) "user/1"
    public void deleteFolder(String path, boolean deleteRootFolder) {
         File folder = new File(path);
         if (folder.exists() && folder.isDirectory()) { // 폴더가 존재하면서 디렉토리인가?
             File[] includedFiles = folder.listFiles();

             for (File f : includedFiles) {
                 if(f.isDirectory()) {
                     deleteFolder(f.getAbsolutePath(), true);
                 } else {
                     f.delete();
                 }
             }
             if (deleteRootFolder) {
                 folder.delete();
             }
         }
    }
}