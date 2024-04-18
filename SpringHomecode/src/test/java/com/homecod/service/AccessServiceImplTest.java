package com.homecod.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.homecode.model.AccessInfo;
import com.homecode.service.AccessServiceImpl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccessServiceImplTest {

    private AccessServiceImpl accessService;

    @BeforeEach
    void setUp() {
        accessService = new AccessServiceImpl();
    }

    @Test
    void testAddAccess() throws Exception {
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setUserId(123456);
        accessInfo.setEndpoints(Arrays.asList("resourceA", "resourceB", "resourceC"));

        accessService.addAccess(accessInfo);

        String content = new String(Files.readAllBytes(Paths.get("src/main/resources/access.json")));
        System.out.print(content);
        assertTrue(content.contains("123456"));
        assertTrue(content.contains("resourceC"));
    }

    @Test
    void testGetAccessInfo() {
        AccessInfo accessInfo = accessService.getAccessInfo(123456);

        assertEquals(123456, accessInfo.getUserId());
        assertEquals(Arrays.asList("resourceA", "resourceB", "resourceC"), accessInfo.getEndpoints());
    }
}
