package com.busticket_booking.controller;



import com.busticket_booking.entities.User;
import com.busticket_booking.entities.UserProfile;
import com.busticket_booking.repositories.UserProfileRepository;
import com.busticket_booking.repositories.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@RestController
public class UserProfileController {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;

    public UserProfileController(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @PostMapping("/users/{userId}/profilePicture")
    public ResponseEntity<String> addProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {



        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user.getEmail().equals(email)) {
                // Check if user has a profile
                UserProfile userProfile = userProfileRepository.findByUser(user);
                if (userProfile == null) {
                    userProfile = new UserProfile();
                    userProfile.setUser(user);
                }

                // Save profile image with user ID and file format as filename
                byte[] bytes = file.getBytes();
                String fileFormat = FilenameUtils.getExtension(file.getOriginalFilename());
                String fileName = user.getId() + "." + fileFormat;
                File fileToSave = new File(uploadDir + File.separator + fileName);
                Files.write(fileToSave.toPath(), bytes);

                // Update user profile
                userProfile.setProfileImage(bytes);
                userProfile.setFileFormat(fileFormat);
                userProfileRepository.save(userProfile);

                return ResponseEntity.ok().body("Profile picture uploaded successfully");
            }else {
                return ResponseEntity.badRequest().body("Failed to upload file check your user id");
            }

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload file");
        }
    }


    @GetMapping("/users/{userId}/profilePicture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user has a profile picture
        UserProfile userProfile = userProfileRepository.findByUser(user);
        if (userProfile == null || userProfile.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(userProfile.getProfileImage());
    }


    @DeleteMapping("/users/{userId}/profilePicture")
    public ResponseEntity<String> deleteProfilePicture(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user has a profile picture
        UserProfile userProfile = userProfileRepository.findByUser(user);
        if (userProfile == null || userProfile.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = user.getId() + "." + userProfile.getFileFormat();

        // Check if file exists
        File fileToDelete = new File(uploadDir + File.separator + fileName);
        if (!fileToDelete.exists()) {
            return ResponseEntity.notFound().build();
        }


        // Delete profile image file
        if (!fileToDelete.delete()) {
            return ResponseEntity.badRequest().body("Failed to delete file");
        }
        fileToDelete.getAbsolutePath();
        // Remove profile image from user profile
        userProfile.setProfileImage(null);
        userProfileRepository.save(userProfile);

        return ResponseEntity.ok().body("Profile picture deleted successfully");
    }

    @PutMapping("/users/{userId}/profilePicture")
    public ResponseEntity<String> updateProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            // Check if user has a profile
            UserProfile userProfile = userProfileRepository.findByUser(user);
            if (userProfile == null) {
                userProfile = new UserProfile();
                userProfile.setUser(user);
            }

            // Delete current profile image
            byte[] currentImageBytes = userProfile.getProfileImage();
            if (currentImageBytes != null) {
                String currentFileName = user.getId() + "." + userProfile.getFileFormat();
                File currentFile = new File(uploadDir + File.separator + currentFileName);
                if (currentFile.exists() && !currentFile.delete()) {
                    return ResponseEntity.badRequest().body("Failed to delete current profile picture");
                }
            }

            // Save new profile image
            byte[] newImageBytes = file.getBytes();
            String fileFormat = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = user.getId() + "." + fileFormat;
            File fileToSave = new File(uploadDir + File.separator + fileName);
            Files.write(fileToSave.toPath(), newImageBytes);

            // Update user profile
            userProfile.setProfileImage(newImageBytes);
            userProfile.setFileFormat(fileFormat);
            userProfileRepository.save(userProfile);

            return ResponseEntity.ok().body("Profile picture updated successfully");

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload file");
        }
    }


}


