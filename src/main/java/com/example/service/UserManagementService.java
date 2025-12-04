package com.example.service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class UserManagementService {
    private Map<String, User> users = new HashMap<>();
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static class User {
        private String id;
        private String name;
        private String email;
        private int age;
        private boolean active;
        private LocalDate registrationDate;
        private double accountBalance;
        private List<String> roles;

        public User(String id, String name, String email, int age) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
            this.active = true;
            this.registrationDate = LocalDate.now();
            this.accountBalance = 0.0;
            this.roles = new ArrayList<>();
        }

        // Getters and setters
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public int getAge() { return age; }
        public boolean isActive() { return active; }
        public LocalDate getRegistrationDate() { return registrationDate; }
        public double getAccountBalance() { return accountBalance; }
        public List<String> getRoles() { return new ArrayList<>(roles); }

        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
        public void setAge(int age) { this.age = age; }
        public void setActive(boolean active) { this.active = active; }
        public void setAccountBalance(double balance) { this.accountBalance = balance; }
        public void addRole(String role) { if (!roles.contains(role)) this.roles.add(role); }
        public void removeRole(String role) { this.roles.remove(role); }
    }

    // Create user
    public User createUser(String id, String name, String email, int age) throws Exception {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("User ID cannot be empty");
        if (users.containsKey(id)) throw new IllegalArgumentException("User ID already exists");
        if (!email.matches(EMAIL_REGEX)) throw new IllegalArgumentException("Invalid email format");
        if (age < 0 || age > 150) throw new IllegalArgumentException("Age must be between 0 and 150");

        User user = new User(id, name, email, age);
        users.put(id, user);
        return user;
    }

    // Get user by ID
    public User getUserById(String id) {
        return users.get(id);
    }

    // Update user
    public User updateUser(String id, String name, String email, int age) throws Exception {
        User user = users.get(id);
        if (user == null) throw new IllegalArgumentException("User not found");
        if (!email.matches(EMAIL_REGEX)) throw new IllegalArgumentException("Invalid email format");
        if (age < 0 || age > 150) throw new IllegalArgumentException("Age must be between 0 and 150");

        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        return user;
    }

    // Delete user
    public boolean deleteUser(String id) {
        return users.remove(id) != null;
    }

    // List all users
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    // Get active users
    public List<User> getActiveUsers() {
        return users.values().stream().filter(User::isActive).collect(Collectors.toList());
    }

    // Get inactive users
    public List<User> getInactiveUsers() {
        return users.values().stream().filter(u -> !u.isActive()).collect(Collectors.toList());
    }

    // Deactivate user
    public boolean deactivateUser(String id) {
        User user = users.get(id);
        if (user == null) return false;
        user.setActive(false);
        return true;
    }

    // Activate user
    public boolean activateUser(String id) {
        User user = users.get(id);
        if (user == null) return false;
        user.setActive(true);
        return true;
    }

    // Get users by age range
    public List<User> getUsersByAgeRange(int minAge, int maxAge) {
        return users.values().stream()
                .filter(u -> u.getAge() >= minAge && u.getAge() <= maxAge)
                .collect(Collectors.toList());
    }

    // Search users by name (contains)
    public List<User> searchUsersByName(String nameQuery) {
        String query = nameQuery.toLowerCase();
        return users.values().stream()
                .filter(u -> u.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    // Search users by email domain
    public List<User> searchByEmailDomain(String domain) {
        return users.values().stream()
                .filter(u -> u.getEmail().endsWith("@" + domain))
                .collect(Collectors.toList());
    }

    // Add balance to user account
    public double addBalance(String id, double amount) throws Exception {
        User user = users.get(id);
        if (user == null) throw new IllegalArgumentException("User not found");
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        user.setAccountBalance(user.getAccountBalance() + amount);
        return user.getAccountBalance();
    }

    // Withdraw balance from user account
    public double withdrawBalance(String id, double amount) throws Exception {
        User user = users.get(id);
        if (user == null) throw new IllegalArgumentException("User not found");
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        if (user.getAccountBalance() < amount) throw new IllegalArgumentException("Insufficient balance");
        user.setAccountBalance(user.getAccountBalance() - amount);
        return user.getAccountBalance();
    }

    // Add role to user
    public void addRoleToUser(String id, String role) throws Exception {
        User user = users.get(id);
        if (user == null) throw new IllegalArgumentException("User not found");
        user.addRole(role);
    }

    // Remove role from user
    public void removeRoleFromUser(String id, String role) throws Exception {
        User user = users.get(id);
        if (user == null) throw new IllegalArgumentException("User not found");
        user.removeRole(role);
    }

    // Check if user has role
    public boolean hasRole(String id, String role) {
        User user = users.get(id);
        if (user == null) return false;
        return user.getRoles().contains(role);
    }

    // Get total users count
    public int getTotalUserCount() {
        return users.size();
    }

    // Get active users count
    public int getActiveUserCount() {
        return (int) users.values().stream().filter(User::isActive).count();
    }

    // Clear all users
    public void clearAllUsers() {
        users.clear();
    }

    // Check if user exists
    public boolean userExists(String id) {
        return users.containsKey(id);
    }

    // Get average age of all users
    public double getAverageAge() {
        if (users.isEmpty()) return 0;
        return users.values().stream().mapToInt(User::getAge).average().orElse(0);
    }

    // Get users with balance above threshold
    public List<User> getUsersWithBalanceAbove(double threshold) {
        return users.values().stream()
                .filter(u -> u.getAccountBalance() > threshold)
                .collect(Collectors.toList());
    }

    // Get total balance across all users
    public double getTotalBalance() {
        return users.values().stream().mapToDouble(User::getAccountBalance).sum();
    }
}
