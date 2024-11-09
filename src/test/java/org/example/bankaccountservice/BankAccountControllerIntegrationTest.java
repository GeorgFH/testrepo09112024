package org.example.bankaccountservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountControllerIntegrationTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateBankAccountEndpoint() {
        // Prepare JSON payload
        BankAccount request = new BankAccount();
        request.setOwnerName("Alice");
        request.setBalance(100.0);

        HttpEntity<BankAccount> entity = new HttpEntity<>(request);

        // Call the endpoint
        ResponseEntity<BankAccount> response = restTemplate.postForEntity("/account", entity, BankAccount.class);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Alice", response.getBody().getOwnerName());
        assertEquals(100.0, response.getBody().getBalance());
        assertTrue(response.getBody().getAccountId().startsWith("K-"));
    }

    @Test
    public void testGetAllBankAccountsEndpoint() {
        // Call the endpoint
        ResponseEntity<BankAccount[]> response = restTemplate.getForEntity("/account", BankAccount[].class);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<BankAccount> accounts = List.of(response.getBody());
        assertTrue(accounts.size() >= 0); // Ensure the response is a valid list
    }

    @Test
    public void testGetBankAccountById_Found() {
        // Assume an account with ID K-0001 exists in the database
        ResponseEntity<BankAccount> response = restTemplate.getForEntity("/account/K-0001", BankAccount.class);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("K-0001", response.getBody().getAccountId());
    }

    @Test
    public void testGetBankAccountById_NotFound() {
        // Call the endpoint with a non-existent account ID
        ResponseEntity<BankAccount> response = restTemplate.getForEntity("/account/K-9999", BankAccount.class);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteBankAccountById_Success() {
        // Assume an account with ID K-0001 exists
        ResponseEntity<Void> response = restTemplate.exchange("/account/K-0001", HttpMethod.DELETE, null, Void.class);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteBankAccountById_NotFound() {
        // Call the endpoint with a non-existent account ID
        ResponseEntity<Void> response = restTemplate.exchange("/account/K-9999", HttpMethod.DELETE, null, Void.class);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @BeforeEach
    public void setUp() {
        // Clear and initialize the database
        bankAccountRepository.deleteAll();
        BankAccount account = new BankAccount("K-0001", "Alice", 100.0);
        bankAccountRepository.save(account);
    }

    @Test
    public void testUpdateBankAccountBalance_Success() {
        // Prepare request body
        BankAccount requestBody = new BankAccount();
        requestBody.setBalance(200.0);

        // Prepare HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HTTP request
        HttpEntity<BankAccount> request = new HttpEntity<>(requestBody, headers);

        // Send PUT request
        ResponseEntity<BankAccount> response = restTemplate.exchange(
                "/account/K-0001", HttpMethod.PUT, request, BankAccount.class);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200.0, response.getBody().getBalance());
    }

    @Test
    public void testUpdateBankAccountBalance_NotFound() {
        // Prepare request body
        BankAccount requestBody = new BankAccount();
        requestBody.setBalance(200.0);

        // Prepare HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HTTP request
        HttpEntity<BankAccount> request = new HttpEntity<>(requestBody, headers);

        // Send PUT request to non-existent account
        ResponseEntity<BankAccount> response = restTemplate.exchange(
                "/account/K-9999", HttpMethod.PUT, request, BankAccount.class);

        // Verify response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
