package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Management Service - Passing Test Cases (50 tests)")
public class UserManagementServiceTest {

    private UserManagementService service;

    @BeforeEach
    void setUp() {
        service = new UserManagementService();
    }

    // ==================== User Creation Tests (10 tests) ====================
    @Nested
    @DisplayName("User Creation Tests")
    class UserCreationTests {
        @Test
        @DisplayName("TC001 - Create user with valid data")
        void testCreateUserWithValidData() throws Exception {
            UserManagementService.User user = service.createUser("U001", "John Doe", "john@example.com", 30);
            assertNotNull(user);
            assertEquals("U001", user.getId());
            assertEquals("John Doe", user.getName());
            assertEquals("john@example.com", user.getEmail());
            assertEquals(30, user.getAge());
        }

        @Test
        @DisplayName("TC002 - Create user should be active by default")
        void testCreateUserShouldBeActiveByDefault() throws Exception {
            UserManagementService.User user = service.createUser("U002", "Jane Smith", "jane@example.com", 25);
            assertTrue(user.isActive());
        }

        @Test
        @DisplayName("TC003 - Create multiple users with different IDs")
        void testCreateMultipleUsers() throws Exception {
            service.createUser("U001", "User One", "user1@example.com", 20);
            service.createUser("U002", "User Two", "user2@example.com", 25);
            service.createUser("U003", "User Three", "user3@example.com", 30);
            assertEquals(3, service.getTotalUserCount());
        }

        @Test
        @DisplayName("TC004 - Create user with minimum valid age")
        void testCreateUserWithMinimumAge() throws Exception {
            UserManagementService.User user = service.createUser("U004", "Baby User", "baby@example.com", 0);
            assertEquals(0, user.getAge());
        }

        @Test
        @DisplayName("TC005 - Create user with maximum valid age")
        void testCreateUserWithMaximumAge() throws Exception {
            UserManagementService.User user = service.createUser("U005", "Elder User", "elder@example.com", 150);
            assertEquals(150, user.getAge());
        }

        @Test
        @DisplayName("TC006 - Create user with valid email format")
        void testCreateUserWithValidEmailFormats() throws Exception {
            service.createUser("U006", "User Six", "test.user+tag@subdomain.example.com", 28);
            service.createUser("U007", "User Seven", "simple@domain.co.uk", 32);
            assertEquals(2, service.getTotalUserCount());
        }

        @Test
        @DisplayName("TC007 - User should have registration date set")
        void testUserShouldHaveRegistrationDate() throws Exception {
            UserManagementService.User user = service.createUser("U008", "User Eight", "user8@example.com", 25);
            assertNotNull(user.getRegistrationDate());
            assertEquals(LocalDate.now(), user.getRegistrationDate());
        }

        @Test
        @DisplayName("TC008 - User should have zero balance by default")
        void testUserShouldHaveZeroBalanceByDefault() throws Exception {
            UserManagementService.User user = service.createUser("U009", "User Nine", "user9@example.com", 30);
            assertEquals(0.0, user.getAccountBalance());
        }

        @Test
        @DisplayName("TC009 - User should have empty roles list by default")
        void testUserShouldHaveEmptyRolesByDefault() throws Exception {
            UserManagementService.User user = service.createUser("U010", "User Ten", "user10@example.com", 22);
            assertTrue(user.getRoles().isEmpty());
        }

        @Test
        @DisplayName("TC010 - Create user with special characters in name")
        void testCreateUserWithSpecialCharactersInName() throws Exception {
            UserManagementService.User user = service.createUser("U011", "José María O'Brien", "jose@example.com", 35);
            assertEquals("José María O'Brien", user.getName());
        }
    }

    // ==================== User Retrieval Tests (8 tests) ====================
    @Nested
    @DisplayName("User Retrieval Tests")
    class UserRetrievalTests {
        @BeforeEach
        void setupUsers() throws Exception {
            service.createUser("U001", "User One", "user1@example.com", 25);
            service.createUser("U002", "User Two", "user2@example.com", 30);
            service.createUser("U003", "User Three", "user3@example.com", 35);
        }

        @Test
        @DisplayName("TC011 - Get user by valid ID")
        void testGetUserByValidId() {
            UserManagementService.User user = service.getUserById("U001");
            assertNotNull(user);
            assertEquals("User One", user.getName());
        }

        @Test
        @DisplayName("TC012 - Get user by non-existent ID returns null")
        void testGetUserByNonExistentIdReturnsNull() {
            UserManagementService.User user = service.getUserById("U999");
            assertNull(user);
        }

        @Test
        @DisplayName("TC013 - Get all users")
        void testGetAllUsers() {
            List<UserManagementService.User> users = service.getAllUsers();
            assertEquals(3, users.size());
        }

        @Test
        @DisplayName("TC014 - Get all users returns copy of list")
        void testGetAllUsersReturnsCopyOfList() {
            List<UserManagementService.User> users1 = service.getAllUsers();
            List<UserManagementService.User> users2 = service.getAllUsers();
            assertEquals(users1.size(), users2.size());
            assertNotSame(users1, users2);
        }

        @Test
        @DisplayName("TC015 - Get all users from empty service returns empty list")
        void testGetAllUsersFromEmptyService() {
            UserManagementService emptyService = new UserManagementService();
            List<UserManagementService.User> users = emptyService.getAllUsers();
            assertTrue(users.isEmpty());
        }

        @Test
        @DisplayName("TC016 - User exists check returns true for existing user")
        void testUserExistsReturnsTrueForExistingUser() {
            assertTrue(service.userExists("U001"));
            assertTrue(service.userExists("U002"));
        }

        @Test
        @DisplayName("TC017 - User exists check returns false for non-existent user")
        void testUserExistsReturnsFalseForNonExistent() {
            assertFalse(service.userExists("U999"));
        }

        @Test
        @DisplayName("TC018 - Get total user count")
        void testGetTotalUserCount() {
            assertEquals(3, service.getTotalUserCount());
        }
    }

    // ==================== User Update Tests (7 tests) ====================
    @Nested
    @DisplayName("User Update Tests")
    class UserUpdateTests {
        @BeforeEach
        void setupUsers() throws Exception {
            service.createUser("U001", "Original Name", "original@example.com", 25);
        }

        @Test
        @DisplayName("TC019 - Update user with valid data")
        void testUpdateUserWithValidData() throws Exception {
            UserManagementService.User user = service.updateUser("U001", "Updated Name", "updated@example.com", 30);
            assertEquals("Updated Name", user.getName());
            assertEquals("updated@example.com", user.getEmail());
            assertEquals(30, user.getAge());
        }

        @Test
        @DisplayName("TC020 - Update only user name")
        void testUpdateOnlyUserName() throws Exception {
            UserManagementService.User user = service.updateUser("U001", "New Name", "original@example.com", 25);
            assertEquals("New Name", user.getName());
            assertEquals("original@example.com", user.getEmail());
        }

        @Test
        @DisplayName("TC021 - Update only user email")
        void testUpdateOnlyUserEmail() throws Exception {
            UserManagementService.User user = service.updateUser("U001", "Original Name", "newemail@example.com", 25);
            assertEquals("Original Name", user.getName());
            assertEquals("newemail@example.com", user.getEmail());
        }

        @Test
        @DisplayName("TC022 - Update only user age")
        void testUpdateOnlyUserAge() throws Exception {
            UserManagementService.User user = service.updateUser("U001", "Original Name", "original@example.com", 40);
            assertEquals("Original Name", user.getName());
            assertEquals(40, user.getAge());
        }

        @Test
        @DisplayName("TC023 - Update preserves user ID")
        void testUpdatePreservesUserId() throws Exception {
            UserManagementService.User user = service.updateUser("U001", "New Name", "new@example.com", 50);
            assertEquals("U001", user.getId());
        }

        @Test
        @DisplayName("TC024 - Update preserves active status")
        void testUpdatePreservesActiveStatus() throws Exception {
            service.deactivateUser("U001");
            UserManagementService.User user = service.updateUser("U001", "New Name", "new@example.com", 30);
            assertFalse(user.isActive());
        }

        @Test
        @DisplayName("TC025 - Update user to minimum valid age")
        void testUpdateUserToMinimumAge() throws Exception {
            UserManagementService.User user = service.updateUser("U001", "Name", "email@example.com", 0);
            assertEquals(0, user.getAge());
        }
    }

    // ==================== User Deletion Tests (5 tests) ====================
    @Nested
    @DisplayName("User Deletion Tests")
    class UserDeletionTests {
        @BeforeEach
        void setupUsers() throws Exception {
            service.createUser("U001", "User One", "user1@example.com", 25);
            service.createUser("U002", "User Two", "user2@example.com", 30);
        }

        @Test
        @DisplayName("TC026 - Delete existing user returns true")
        void testDeleteExistingUserReturnsTrue() {
            boolean deleted = service.deleteUser("U001");
            assertTrue(deleted);
        }

        @Test
        @DisplayName("TC027 - Delete user removes from service")
        void testDeleteUserRemovesFromService() {
            service.deleteUser("U001");
            assertNull(service.getUserById("U001"));
            assertEquals(1, service.getTotalUserCount());
        }

        @Test
        @DisplayName("TC028 - Delete non-existent user returns false")
        void testDeleteNonExistentUserReturnsFalse() {
            boolean deleted = service.deleteUser("U999");
            assertFalse(deleted);
        }

        @Test
        @DisplayName("TC029 - Delete all users one by one")
        void testDeleteAllUsersOneByOne() {
            service.deleteUser("U001");
            service.deleteUser("U002");
            assertEquals(0, service.getTotalUserCount());
        }

        @Test
        @DisplayName("TC030 - Can create user with same ID after deletion")
        void testCanCreateUserWithSameIdAfterDeletion() throws Exception {
            service.deleteUser("U001");
            UserManagementService.User newUser = service.createUser("U001", "New User One", "newuser1@example.com", 28);
            assertNotNull(newUser);
            assertEquals("New User One", newUser.getName());
        }
    }

    // ==================== User Status Tests (6 tests) ====================
    @Nested
    @DisplayName("User Status Tests")
    class UserStatusTests {
        @BeforeEach
        void setupUsers() throws Exception {
            service.createUser("U001", "User One", "user1@example.com", 25);
            service.createUser("U002", "User Two", "user2@example.com", 30);
            service.createUser("U003", "User Three", "user3@example.com", 35);
        }

        @Test
        @DisplayName("TC031 - All users active by default")
        void testAllUsersActiveByDefault() {
            List<UserManagementService.User> activeUsers = service.getActiveUsers();
            assertEquals(3, activeUsers.size());
        }

        @Test
        @DisplayName("TC032 - Deactivate user")
        void testDeactivateUser() {
            boolean deactivated = service.deactivateUser("U001");
            assertTrue(deactivated);
            assertFalse(service.getUserById("U001").isActive());
        }

        @Test
        @DisplayName("TC033 - Activate user")
        void testActivateUser() {
            service.deactivateUser("U001");
            boolean activated = service.activateUser("U001");
            assertTrue(activated);
            assertTrue(service.getUserById("U001").isActive());
        }

        @Test
        @DisplayName("TC034 - Get inactive users")
        void testGetInactiveUsers() {
            service.deactivateUser("U001");
            service.deactivateUser("U002");
            List<UserManagementService.User> inactiveUsers = service.getInactiveUsers();
            assertEquals(2, inactiveUsers.size());
        }

        @Test
        @DisplayName("TC035 - Get active user count")
        void testGetActiveUserCount() {
            service.deactivateUser("U001");
            assertEquals(2, service.getActiveUserCount());
        }

        @Test
        @DisplayName("TC036 - Deactivate non-existent user returns false")
        void testDeactivateNonExistentUserReturnsFalse() {
            boolean deactivated = service.deactivateUser("U999");
            assertFalse(deactivated);
        }
    }

    // ==================== User Search Tests (7 tests) ====================
    @Nested
    @DisplayName("User Search Tests")
    class UserSearchTests {
        @BeforeEach
        void setupUsers() throws Exception {
            service.createUser("U001", "John Smith", "john@example.com", 25);
            service.createUser("U002", "John Doe", "john.doe@example.com", 30);
            service.createUser("U003", "Jane Smith", "jane@example.com", 28);
            service.createUser("U004", "Bob Johnson", "bob@gmail.com", 35);
        }

        @Test
        @DisplayName("TC037 - Search users by name substring")
        void testSearchUsersByNameSubstring() {
            List<UserManagementService.User> results = service.searchUsersByName("John");
            assertEquals(3, results.size());
        }

        @Test
        @DisplayName("TC038 - Search by name is case-insensitive")
        void testSearchByNameIsCaseInsensitive() {
            List<UserManagementService.User> results1 = service.searchUsersByName("john");
            List<UserManagementService.User> results2 = service.searchUsersByName("JOHN");
            assertEquals(results1.size(), results2.size());
        }

        @Test
        @DisplayName("TC039 - Search users by full name")
        void testSearchUsersByFullName() {
            List<UserManagementService.User> results = service.searchUsersByName("Jane Smith");
            assertEquals(1, results.size());
            assertEquals("Jane Smith", results.get(0).getName());
        }

        @Test
        @DisplayName("TC040 - Search by non-existent name returns empty list")
        void testSearchByNonExistentNameReturnsEmpty() {
            List<UserManagementService.User> results = service.searchUsersByName("NonExistent");
            assertTrue(results.isEmpty());
        }

        @Test
        @DisplayName("TC041 - Search by email domain")
        void testSearchByEmailDomain() {
            List<UserManagementService.User> results = service.searchByEmailDomain("example.com");
            assertEquals(3, results.size());
        }

        @Test
        @DisplayName("TC042 - Search by different email domain")
        void testSearchByDifferentEmailDomain() {
            List<UserManagementService.User> results = service.searchByEmailDomain("gmail.com");
            assertEquals(1, results.size());
            assertEquals("Bob Johnson", results.get(0).getName());
        }

        @Test
        @DisplayName("TC043 - Get users by age range")
        void testGetUsersByAgeRange() {
            List<UserManagementService.User> results = service.getUsersByAgeRange(25, 30);
            assertEquals(3, results.size());
        }
    }

    // ==================== Account Balance Tests (6 tests) ====================
    @Nested
    @DisplayName("Account Balance Tests")
    class AccountBalanceTests {
        @BeforeEach
        void setupUsers() throws Exception {
            service.createUser("U001", "User One", "user1@example.com", 25);
            service.createUser("U002", "User Two", "user2@example.com", 30);
        }

        @Test
        @DisplayName("TC044 - Add balance to user account")
        void testAddBalanceToUserAccount() throws Exception {
            double newBalance = service.addBalance("U001", 100.0);
            assertEquals(100.0, newBalance);
        }

        @Test
        @DisplayName("TC045 - Add multiple amounts to user account")
        void testAddMultipleAmountsToAccount() throws Exception {
            service.addBalance("U001", 50.0);
            double newBalance = service.addBalance("U001", 75.0);
            assertEquals(125.0, newBalance);
        }

        @Test
        @DisplayName("TC046 - Withdraw balance from user account")
        void testWithdrawBalanceFromAccount() throws Exception {
            service.addBalance("U001", 100.0);
            double newBalance = service.withdrawBalance("U001", 30.0);
            assertEquals(70.0, newBalance);
        }

        @Test
        @DisplayName("TC047 - Get total balance across all users")
        void testGetTotalBalanceAcrossAllUsers() throws Exception {
            service.addBalance("U001", 100.0);
            service.addBalance("U002", 250.0);
            double totalBalance = service.getTotalBalance();
            assertEquals(350.0, totalBalance);
        }

        @Test
        @DisplayName("TC048 - Get users with balance above threshold")
        void testGetUsersWithBalanceAboveThreshold() throws Exception {
            service.addBalance("U001", 100.0);
            service.addBalance("U002", 250.0);
            List<UserManagementService.User> results = service.getUsersWithBalanceAbove(150.0);
            assertEquals(1, results.size());
        }

        @Test
        @DisplayName("TC049 - Get average age of all users")
        void testGetAverageAgeOfAllUsers() throws Exception {
            service.createUser("U003", "User Three", "user3@example.com", 35);
            double averageAge = service.getAverageAge();
            assertEquals(30.0, averageAge);
        }
    }

    // ==================== Role Management Tests (4 tests) ====================
    @Nested
    @DisplayName("Role Management Tests")
    class RoleManagementTests {
        @BeforeEach
        void setupUsers() throws Exception {
            service.createUser("U001", "User One", "user1@example.com", 25);
        }

        @Test
        @DisplayName("TC050 - Add role to user")
        void testAddRoleToUser() throws Exception {
            service.addRoleToUser("U001", "ADMIN");
            assertTrue(service.hasRole("U001", "ADMIN"));
        }

        @Test
        @DisplayName("TC051 - Add multiple roles to user")
        void testAddMultipleRolesToUser() throws Exception {
            service.addRoleToUser("U001", "ADMIN");
            service.addRoleToUser("U001", "USER");
            service.addRoleToUser("U001", "MODERATOR");
            UserManagementService.User user = service.getUserById("U001");
            assertEquals(3, user.getRoles().size());
            assertTrue(service.hasRole("U001", "ADMIN"));
            assertTrue(service.hasRole("U001", "USER"));
            assertTrue(service.hasRole("U001", "MODERATOR"));
        }

        @Test
        @DisplayName("TC052 - Remove role from user")
        void testRemoveRoleFromUser() throws Exception {
            service.addRoleToUser("U001", "ADMIN");
            service.removeRoleFromUser("U001", "ADMIN");
            assertFalse(service.hasRole("U001", "ADMIN"));
        }

        @Test
        @DisplayName("TC053 - Check role does not exist returns false")
        void testCheckRoleDoesNotExistReturnsFalse() {
            assertFalse(service.hasRole("U001", "ADMIN"));
        }
    }
}
