//// UserController.java
//package com.jspm.SmartErp.controller;
//
//import com.jspm.SmartErp.model.User;
//import com.jspm.SmartErp.service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://localhost:3000")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // Get all users (Admin only)
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        try {
//            List<User> users = userService.findAll();
//            // Remove passwords from response
//            users.forEach(user -> user.setPassword(null));
//            return ResponseEntity.ok(users);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    // Get user by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
//        try {
//            Optional<User> user = userService.findById(id);
//            if (user.isPresent()) {
//                // Don't return password in response
//                User userResponse = user.get();
//                userResponse.setPassword(null);
//                return ResponseEntity.ok(userResponse);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to fetch user: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Get user by email
//    @GetMapping("/email/{email}")
//    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
//        try {
//            User user = userService.findByEmail(email);
//            // Don't return password in response
//            user.setPassword(null);
//            return ResponseEntity.ok(user);
//        } catch (RuntimeException e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to fetch user: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Get current user profile (from JWT token)
//    @GetMapping("/me")
//    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
//        try {
//            // Extract token from "Bearer {token}"
//            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//
//            String token = authorizationHeader.substring(7);
//
//            // In a real application, you would decode the JWT token here
//            // For now, we'll use a simple approach - get user from the first user in database
//            // You should replace this with proper JWT decoding logic
//
//            List<User> users = userService.findAll();
//            if (users.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            User currentUser = users.get(0); // Temporary - get first user
//            currentUser.setPassword(null);
//
//            return ResponseEntity.ok(currentUser);
//
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to fetch current user: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Create new user
//    @PostMapping
//    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
//        try {
//            // Check if email already exists
//            if (userService.existsByEmail(user.getEmail())) {
//                Map<String, String> error = new HashMap<>();
//                error.put("error", "User with email '" + user.getEmail() + "' already exists");
//                return ResponseEntity.badRequest().body(error);
//            }
//
//            User createdUser = userService.save(user);
//            createdUser.setPassword(null);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to create user: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Update user profile (without password)
//    @PutMapping("/{id}/profile")
//    public ResponseEntity<?> updateUserProfile(@PathVariable Integer id, @RequestBody User userDetails) {
//        try {
//            Optional<User> existingUser = userService.findById(id);
//            if (existingUser.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            User user = existingUser.get();
//
//            // Update allowed fields only
//            if (userDetails.getFullName() != null) {
//                user.setFullName(userDetails.getFullName());
//            }
//            if (userDetails.getPhone() != null) {
//                user.setPhone(userDetails.getPhone());
//            }
//            if (userDetails.getDepartment() != null) {
//                user.setDepartment(userDetails.getDepartment());
//            }
//            if (userDetails.getRole() != null) {
//                user.setRole(userDetails.getRole());
//            }
//
//            User updatedUser = userService.save(user);
//            updatedUser.setPassword(null);
//            return ResponseEntity.ok(updatedUser);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to update profile: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Update user (full update - admin only)
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable Integer id, @Valid @RequestBody User userDetails) {
//        try {
//            Optional<User> existingUser = userService.findById(id);
//            if (existingUser.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            User user = existingUser.get();
//
//            // Update all fields
//            user.setFullName(userDetails.getFullName());
//            user.setEmail(userDetails.getEmail());
//            user.setPhone(userDetails.getPhone());
//            user.setDepartment(userDetails.getDepartment());
//            user.setRole(userDetails.getRole());
//
//            // Only update password if provided
//            if (userDetails.getPassword() != null && !userDetails.getPassword().trim().isEmpty()) {
//                user.setPassword(userDetails.getPassword());
//            }
//
//            User updatedUser = userService.save(user);
//            updatedUser.setPassword(null);
//            return ResponseEntity.ok(updatedUser);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to update user: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Change password
//    @PutMapping("/{id}/password")
//    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestBody Map<String, String> passwordData) {
//        try {
//            String newPassword = passwordData.get("newPassword");
//            String currentPassword = passwordData.get("currentPassword"); // Optional: for verification
//
//            if (newPassword == null || newPassword.trim().isEmpty()) {
//                Map<String, String> error = new HashMap<>();
//                error.put("error", "New password is required");
//                return ResponseEntity.badRequest().body(error);
//            }
//
//            if (newPassword.length() < 6) {
//                Map<String, String> error = new HashMap<>();
//                error.put("error", "Password must be at least 6 characters long");
//                return ResponseEntity.badRequest().body(error);
//            }
//
//            Optional<User> existingUser = userService.findById(id);
//            if (existingUser.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            User user = existingUser.get();
//
//            // Optional: Verify current password if provided
//            if (currentPassword != null && !currentPassword.trim().isEmpty()) {
//                if (!userService.verifyPassword(currentPassword, user.getPassword())) {
//                    Map<String, String> error = new HashMap<>();
//                    error.put("error", "Current password is incorrect");
//                    return ResponseEntity.badRequest().body(error);
//                }
//            }
//
//            user.setPassword(newPassword); // Service will encode it
//            User updatedUser = userService.save(user);
//            updatedUser.setPassword(null);
//
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Password updated successfully");
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to update password: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Delete user
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
//        try {
//            if (!userService.findById(id).isPresent()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            userService.deleteById(id);
//
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "User deleted successfully");
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to delete user: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Check if email exists
//    @GetMapping("/check-email/{email}")
//    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String email) {
//        try {
//            boolean exists = userService.existsByEmail(email);
//            Map<String, Boolean> response = new HashMap<>();
//            response.put("exists", exists);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    // Get users by role
//    @GetMapping("/role/{role}")
//    public ResponseEntity<?> getUsersByRole(@PathVariable String role) {
//        try {
//            List<User> users = userService.findAll()
//                    .stream()
//                    .filter(user -> role.equalsIgnoreCase(user.getRole()))
//                    .toList();
//
//            // Remove passwords from response
//            users.forEach(user -> user.setPassword(null));
//            return ResponseEntity.ok(users);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to fetch users by role: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Update user role
//    @PutMapping("/{id}/role")
//    public ResponseEntity<?> updateUserRole(@PathVariable Integer id, @RequestBody Map<String, String> roleData) {
//        try {
//            String newRole = roleData.get("role");
//            if (newRole == null || newRole.trim().isEmpty()) {
//                Map<String, String> error = new HashMap<>();
//                error.put("error", "Role is required");
//                return ResponseEntity.badRequest().body(error);
//            }
//
//            Optional<User> existingUser = userService.findById(id);
//            if (existingUser.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            User user = existingUser.get();
//            user.setRole(newRole);
//            User updatedUser = userService.save(user);
//            updatedUser.setPassword(null);
//
//            return ResponseEntity.ok(updatedUser);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to update user role: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    // Search users by name or email
//    @GetMapping("/search")
//    public ResponseEntity<?> searchUsers(@RequestParam String query) {
//        try {
//            List<User> users = userService.findAll()
//                    .stream()
//                    .filter(user ->
//                            (user.getFullName() != null && user.getFullName().toLowerCase().contains(query.toLowerCase())) ||
//                                    (user.getEmail() != null && user.getEmail().toLowerCase().contains(query.toLowerCase()))
//                    )
//                    .toList();
//
//            // Remove passwords from response
//            users.forEach(user -> user.setPassword(null));
//            return ResponseEntity.ok(users);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to search users: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//}