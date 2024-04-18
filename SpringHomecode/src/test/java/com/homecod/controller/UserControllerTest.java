package com.homecod.controller;

import com.homecode.controller.UserController;
import com.homecode.model.AccessInfo;
import com.homecode.model.RoleInfo;
import com.homecode.service.AccessService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

	@Mock
	private AccessService accessService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetResourceAccessSuccess() {
		AccessInfo accessInfo = new AccessInfo();
		accessInfo.setUserId(123456);
		accessInfo.setEndpoints(Arrays.asList("resourceA", "resourceB", "resourceC"));

		RoleInfo role = new RoleInfo(123456, "acc", "admin");

		when(accessService.validateRoleFromAuthHeader(any())).thenReturn(role);

		when(accessService.getAccessInfo(123456)).thenReturn(accessInfo);

		ResponseEntity<String> response = userController.getResourceAccess("resourceA",
				"Basic MTIzNDU2OlhYWFhYWFg6YWRtaW4=");

		System.out.print(response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Success: User has access to resourceA", response.getBody());
	}

	@Test
	void testGetResourceAccessFailure() {
		AccessInfo accessInfo = new AccessInfo();
		accessInfo.setUserId(123456);
		accessInfo.setEndpoints(Arrays.asList("resourceA", "resourceB", "resourceC"));

		when(accessService.getAccessInfo(123456)).thenReturn(accessInfo);

		RoleInfo role = new RoleInfo(123456, "acc", "admin");

		when(accessService.validateRoleFromAuthHeader(any())).thenReturn(role);
		ResponseEntity<String> response = userController.getResourceAccess("resourceD", "Basic dXNlcjoxMjM0NTY=");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Failure: User does not have access to resourceD", response.getBody());
	}
}
