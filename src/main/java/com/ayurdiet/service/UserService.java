package com.ayurdiet.service;

import com.ayurdiet.model.User;
import com.ayurdiet.repository.UserRepository;
import com.ayurdiet.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;
    
    public User registerUser(User user) {
        return userRepository.save(user);
    }
    
    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public String saveImage(MultipartFile imageFile) throws IOException {
        // Generate unique filename
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        
        // Save image
        FileUploadUtil.saveFile(uploadDir, fileName, imageFile);
        
        return fileName; // Return only the filename, not the full path
    }
    
    public void deleteImage(String imagePath) throws IOException {
        if (imagePath != null && !imagePath.isEmpty()) {
            FileUploadUtil.deleteFile(uploadDir + "/" + imagePath);
        }
    }
}