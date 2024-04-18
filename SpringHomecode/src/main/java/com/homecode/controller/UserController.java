package com.homecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homecode.exception.CustomException;
import com.homecode.model.AccessInfo;
import com.homecode.model.RoleInfo;
import com.homecode.service.AccessService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private AccessService accessService;

	@GetMapping("/{resource}")
	public ResponseEntity<String> getResourceAccess(@PathVariable String resource,
			@RequestHeader("Authorization") String authHeader) {
		
		ResponseEntity<String> response;

		try {
			RoleInfo role = accessService.validateRoleFromAuthHeader(authHeader);

			AccessInfo accessInfo = accessService.getAccessInfo(role.getUserId());
			if (accessInfo.getEndpoints().contains(resource)) {
				response = ResponseEntity.ok("Success: User has access to " + resource);
			} else {
				response = ResponseEntity.ok("Failure: User does not have access to " + resource);
			}

		} catch (CustomException e) {
			response =  ResponseEntity.status(400).body(e.getErrorMessage());

		} catch (Exception e2) {
			e2.printStackTrace();

			response =   ResponseEntity.status(400).body("Server Error");
		}
		
		return response;

		
	}

}
