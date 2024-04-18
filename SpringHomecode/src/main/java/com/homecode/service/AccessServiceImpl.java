package com.homecode.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.homecode.exception.CustomException;
import com.homecode.model.AccessInfo;
import com.homecode.model.RoleInfo;

@Service
public class AccessServiceImpl implements AccessService {

	private Map<Long, AccessInfo> userAccessMap = new HashMap<>();

	private static final String FILE_PATH = "src/main/resources/access.json";

	@Override
	public AccessInfo getAccessInfo(long userId) {

		if (userAccessMap.isEmpty()) {
			userAccessMap = loadFromFile();
		}
		return userAccessMap.get(userId);
	}

	@Override
	public void addAccess(AccessInfo access) {
		userAccessMap.put(access.getUserId(), access);
		try {
			saveToFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("Something wrong wit access file");
			
		}
	}

	/**
	 * validate Role From AuthHeader
	 * Format: Authorization: Basic MTIzNDU2OlhYWFhYWFg6YWRtaW4=
	 * Example : 123456:XXXXXXX:admin
	 * @param authHeader
	 * @return
	 */
	@Override
	public RoleInfo validateRoleFromAuthHeader(String authHeader) {
		
		System.out.print(authHeader);

		String[] authParts = authHeader.split(" ");
		if (authParts.length != 2 || !"Basic".equalsIgnoreCase(authParts[0])) {
			throw new CustomException("Invalid Authorization header");
		}

		String decodedCredentials = new String(Base64.getDecoder().decode(authParts[1]), StandardCharsets.UTF_8);
		String[] credentials = decodedCredentials.split(":");
		if (credentials.length != 3) {
			throw new CustomException("Invalid credentials format");
		}

		long userId;
		try {
			userId = Long.parseLong(credentials[0]);
		} catch (NumberFormatException e) {

			throw new CustomException("Invalid userId format");
		}

		return new RoleInfo(userId,credentials[1],credentials[2]);

	}

	private void saveToFile() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try (FileWriter fileWriter = new FileWriter(new File(FILE_PATH))) {
			objectMapper.writeValue(fileWriter, userAccessMap);
		}
	}

	private Map<Long, AccessInfo> loadFromFile() {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(FILE_PATH);

		if (!file.exists()) {
			return new HashMap<>();
		}

		try {
			return objectMapper.readValue(file, new TypeReference<Map<Long, AccessInfo>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("Exception occur: Load From File");
		}
	}

}
