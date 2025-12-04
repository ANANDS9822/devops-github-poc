package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Management Service - Failure Test Cases (20 tests: 10 passing, 10 failing)")
public class UserManagementServiceFailureTest {

    private UserManagementService service;

    @BeforeEach
    void setUp() {
        service = new UserManagementService();
    }

    // ==================== Invalid User Creation Tests (Failing Cases) ====================
    @Nested
    @DisplayName("Invalid User Creation Tests - FAILING")
    class InvalidUserCreationTests {
        @Test
        @DisplayName("FC001 - FAIL: Create user with null ID should throw exception")
        void testCreateUserWithNullIdThrowsException() throws Exception {
            // This test is INTENTIONALLY DESIGNED TO FAIL
            UserManagementService.User user = service.createUser(null, "John Doe", "john@example.com", 30);
            // The service correctly throws an exception, but test expects non-null user
            assertNotNull(user);
        }

        @Test
        @DisplayName("FC002 - FAIL: Create user with empty ID should throw exception")
        void testCreateUserWithEmptyIdThrowsException() throws Exception {
            // INTENTIONALLY DESIGNED TO FAIL - expects success but should fail
            UserManagementService.User user = service.createUser("", "Jane Smith", "jane@example.com", 25);
            assertNotNull(user);
        }

        @Test
        @DisplayName("FC003 - FAIL: Create user with invalid email format")
        void testCreateUserWithInvalidEmailFormat() throws Exception {
            // INTENTIONALLY DESIGNED TO FAIL - invalid email should throw exception
            UserManagementService.User user = service.createUser("U001", "User", "invalid-email", 30);
            assertNotNull(user);
        }

        @Test
        @DisplayName("FC004 - FAIL: Create user with age exceeding maximum limit")
        void testCreateUserWithExcessiveAge() throws Exception {
            // INTENTIONALLY DESIGNED TO FAIL - age > 150 should throw exception
            UserManagementService.User user = service.createUser("U004", "Ancient User", "ancient@example.com", 200);
            assertNotNull(user);
        }

        @Test
        @DisplayName("FC005 - FAIL: Create user with negative age")
        void testCreateUserWithNegativeAge() throws Exception {
            // INTENTIONALLY DESIGNED TO FAIL - negative age should throw exception
            UserManagementService.User user = service.createUser("U005", "Negative Age User", "neg@example.com", -5);
            assertNotNull(user);
        }

        @Test
        @DisplayName("FC006 - FAIL: Create duplicate user with same ID")
        void testCreateDuplicateUserWithSameId() throws Exception {
            service.createUser("U006", "First User", "first@example.com", 30);
            // INTENTIONALLY DESIGNED TO FAIL - duplicate ID should throw exception
            UserManagementService.User user = service.createUser("U006", "Duplicate User", "dup@example.com", 25);
            assertNotNull(user);
        }

        @Test
        @DisplayName("FC007 - FAIL: Update user with invalid email")
        void testUpdateUserWithInvalidEmail() throws Exception {
            service.createUser("U007", "Original", "original@example.com", 30);
            // INTENTIONALLY DESIGNED TO FAIL - invalid email should throw exception
            UserManagementService.User user = service.updateUser("U007", "Name", "not-a-valid-email", 30);
            assertNotNull(user);
        }

        @Test
        @DisplayName("FC008 - FAIL: Add negative balance")
        void testAddNegativeBalance() throws Exception {
            service.createUser("U008", "User", "user@example.com", 30);
            // INTENTIONALLY DESIGNED TO FAIL - negative amount should throw exception
            double balance = service.addBalance("U008", -100.0);
            assertTrue(balance >= 0);
        }

        @Test
        @DisplayName("FC009 - FAIL: Withdraw from non-existent user")
        void testWithdrawFromNonExistentUser() throws Exception {
            // INTENTIONALLY DESIGNED TO FAIL - non-existent user should throw exception
            double balance = service.withdrawBalance("U999", 50.0);
            assertEquals(0, balance);
        }

        @Test
        @DisplayName("FC010 - FAIL: Withdraw more than available balance")
        void testWithdrawMoreThanAvailableBalance() throws Exception {
            service.createUser("U010", "User", "user@example.com", 30);
            service.addBalance("U010", 50.0);
            // INTENTIONALLY DESIGNED TO FAIL - insufficient balance should throw exception
            double balance = service.withdrawBalance("U010", 100.0);
            assertEquals(-50.0, balance);
        }
    }

    // ==================== Boundary Condition Tests (Failing Cases) ====================
    @Nested
    @DisplayName("Boundary Condition Tests - FAILING")
    class BoundaryConditionTests {
        @Test
        @DisplayName("FC011 - FAIL: User age exactly at minimum boundary should pass")
        void testUserAgeAtMinimumBoundary() throws Exception {
            // INTENTIONALLY DESIGNED TO FAIL - expects wrong age
            UserManagementService.User user = service.createUser("U011", "Age Zero", "age@example.com", 0);
            assertEquals(1, user.getAge()); // Should be 0, not 1
        }

        @Test
        @DisplayName("FC012 - FAIL: User age exactly at maximum boundary should pass")
        void testUserAgeAtMaximumBoundary() throws Exception {
            // INTENTIONALLY DESIGNED TO FAIL - expects wrong age
            UserManagementService.User user = service.createUser("U012", "Age Max", "max@example.com", 150);
            assertEquals(149, user.getAge()); // Should be 150, not 149
        }

        @Test
        @DisplayName("FC013 - FAIL: Get users in age range with inverted boundaries")
        void testGetUsersInAgeRangeInvertedBoundaries() throws Exception {
            service.createUser("U013", "User", "user@example.com", 25);
            service.createUser("U014", "User", "user@example.com", 35);
            // INTENTIONALLY DESIGNED TO FAIL - inverted range should return results
            List<UserManagementService.User> results = service.getUsersByAgeRange(35, 25);
            assertEquals(2, results.size());
        }

        @Test
        @DisplayName("FC014 - FAIL: Average age calculation with single user")
        void testAverageAgeWithSingleUser() throws Exception {
            service.createUser("U015", "Single User", "user@example.com", 42);
            // INTENTIONALLY DESIGNED TO FAIL - expects wrong average
            double average = service.getAverageAge();
            assertEquals(40.0, average); // Should be 42, not 40
        }

        @Test
        @DisplayName("FC015 - FAIL: Total balance with multiple users")
        void testTotalBalanceWithMultipleUsers() throws Exception {
            service.createUser("U016", "User One", "user1@example.com", 30);
            service.createUser("U017", "User Two", "user2@example.com", 35);
            service.addBalance("U016", 100.0);
            service.addBalance("U017", 200.0);
            // INTENTIONALLY DESIGNED TO FAIL - expects wrong total
            double total = service.getTotalBalance();
            assertEquals(250.0, total); // Should be 300.0, not 250
        }
    }

    // ==================== Logic Error Tests (Failing Cases) ====================
    @Nested
    @DisplayName("Logic Error Tests - FAILING")
    class LogicErrorTests {
        @Test
        @DisplayName("FC016 - FAIL: Deactivated user still counted as active")
        void testDeactivatedUserStillCountedAsActive() throws Exception {
            service.createUser("U018", "User", "user@example.com", 30);
            service.deactivateUser("U018");
            // INTENTIONALLY DESIGNED TO FAIL - deactivated user should not be in active count
            assertEquals(1, service.getActiveUserCount()); // Should be 0
        }

        @Test
        @DisplayName("FC017 - FAIL: Inactive users list contains active users")
        void testInactiveUsersListContainsActiveUsers() throws Exception {
            service.createUser("U019", "User", "user@example.com", 30);
            service.createUser("U020", "User", "user@example.com", 35);
            // INTENTIONALLY DESIGNED TO FAIL - both are active, none should be inactive
            List<UserManagementService.User> inactive = service.getInactiveUsers();
            assertEquals(2, inactive.size()); // Should be 0
        }

        @Test
        @DisplayName("FC018 - PASS: Verify user roles are case-sensitive")
        void testUserRolesCaseSensitive() throws Exception {
            service.createUser("U021", "User", "user@example.com", 30);
            service.addRoleToUser("U021", "ADMIN");
            // INTENTIONALLY DESIGNED TO PASS - this should work correctly
            assertTrue(service.hasRole("U021", "ADMIN"));
            assertFalse(service.hasRole("U021", "admin"));
        }

        @Test
        @DisplayName("FC019 - PASS: Can add same role multiple times")
        void testAddSameRoleMultipleTimes() throws Exception {
            service.createUser("U022", "User", "user@example.com", 30);
            service.addRoleToUser("U022", "ADMIN");
            service.addRoleToUser("U022", "ADMIN");
            // INTENTIONALLY DESIGNED TO PASS - duplicate role should be prevented
            UserManagementService.User user = service.getUserById("U022");
            assertEquals(1, user.getRoles().size()); // Role list prevents duplicates
        }

        @Test
        @DisplayName("FC020 - PASS: Delete user and verify total count")
        void testDeleteUserAndVerifyTotalCount() throws Exception {
            service.createUser("U023", "User One", "user@example.com", 25);
            service.createUser("U024", "User Two", "user@example.com", 30);
            service.deleteUser("U023");
            // INTENTIONALLY DESIGNED TO PASS
            assertEquals(1, service.getTotalUserCount());
        }
    }
}
