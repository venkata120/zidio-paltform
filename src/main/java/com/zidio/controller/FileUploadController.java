package com.zidio.controller;

import com.zidio.Entities.StudentProfile;
import com.zidio.Entities.User;
import com.zidio.repository.StudentProfileRepository;
import com.zidio.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Value("${upload.resume.dir}")
    private String uploadDir;

    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;

    public FileUploadController(UserRepository userRepository, StudentProfileRepository studentProfileRepository) {
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, Authentication auth) {
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudentProfile student = studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "/api/file/files/" + fileName;
            student.setResumeUrl(fileUrl);
            studentProfileRepository.save(student);

            return ResponseEntity.ok(Map.of("message", "Uploaded successfully", "url", fileUrl));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/resume/mine")
    public ResponseEntity<?> getMyResume(Authentication auth) {
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudentProfile student = studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        if (student.getResumeUrl() == null || student.getResumeUrl().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No resume uploaded.");
        }

        return ResponseEntity.ok(Map.of("url", student.getResumeUrl()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteResume(Authentication auth) {
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudentProfile student = studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        if (student.getResumeUrl() == null || student.getResumeUrl().isEmpty()) {
            return ResponseEntity.badRequest().body("No resume to delete.");
        }

        String fileUrl = student.getResumeUrl(); // e.g., /api/file/files/filename.pdf
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);

        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete file.");
        }

        student.setResumeUrl(null);
        studentProfileRepository.save(student);

        return ResponseEntity.ok("Resume deleted.");
    }
}
