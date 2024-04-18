package com.homecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecode.exception.CustomException;
import com.homecode.model.AccessInfo;
import com.homecode.model.RoleInfo;
import com.homecode.service.AccessService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AccessService accessService;

	@PostMapping("/addUser")
	public ResponseEntity<String> addUserAccess(@RequestBody AccessInfo accessInfo,
			@RequestHeader("Authorization") String authHeader) {
		
		// Decode and validate role from authHeader,Check if the role is admin
		try {
			RoleInfo role = accessService.validateRoleFromAuthHeader(authHeader);

			if (role != null && "Admin".equalsIgnoreCase(role.getRole())) {
				accessService.addAccess(accessInfo);
			}

		} catch (CustomException e) {
			return ResponseEntity.status(400).body(e.getErrorMessage());

		} catch (Exception e2) {

			return ResponseEntity.status(400).body("Server Error");
		}

		return ResponseEntity.ok("Access added successfully");
	}
}