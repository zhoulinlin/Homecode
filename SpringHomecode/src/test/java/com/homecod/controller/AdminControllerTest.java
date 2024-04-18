package com.homecod.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.homecode.controller.AdminController;
import com.homecode.model.AccessInfo;
import com.homecode.service.AccessService;

class AdminControllerTest {

    @Mock
    private AccessService accessService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUserAccess() {
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setUserId(123456);
        accessInfo.setEndpoints(Arrays.asList("resourceA", "resourceB", "resourceC"));

      //  when(accessService.addAccess(123456, accessInfo.getEndpoints())).thenReturn(true);

        ResponseEntity<String> response = adminController.addUserAccess(accessInfo, "Basic YWRtaW46YWRtaW4=");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Access added successfully", response.getBody());
    }
}
