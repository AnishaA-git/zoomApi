package com.example.demo;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


public class CreateMeeting {

public ZoomMeetingObjectDTO createMeeting (ZoomMeetingObjectDTO zoomMeetingObjectDTO) {
	
        System.out.print("Request to create a Zoom meeting");
       // replace zoomUserId with your user ID
        String apiUrl = "https://api.zoom.us/v2/users/" + "anisha.agarwal1993@gmail.com" + "/meetings";

      // replace with your password or method
       // zoomMeetingObjectDTO.setPassword("");
      // replace email with your email
        zoomMeetingObjectDTO.setHost_email("anisha.agarwal1993@gmail.com");

      // Optional Settings for host and participant related options
        ZoomMeetingSettingsDTO settingsDTO = new ZoomMeetingSettingsDTO();
        settingsDTO.setJoin_before_host(false);
        settingsDTO.setParticipant_video(true);
        settingsDTO.setHost_video(false);
        settingsDTO.setAuto_recording("cloud");
        settingsDTO.setMute_upon_entry(true);
        zoomMeetingObjectDTO.setSettings(settingsDTO);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String JWT = generateZoomJWTTOken();
        headers.add("Authorization", "Bearer " + JWT);
        headers.add("content-type", "application/json");
        HttpEntity<ZoomMeetingObjectDTO> httpEntity = new HttpEntity<ZoomMeetingObjectDTO>(zoomMeetingObjectDTO, headers);
        ResponseEntity<ZoomMeetingObjectDTO> zEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, ZoomMeetingObjectDTO.class);
        if(zEntity.getStatusCodeValue() == 201) {
        	System.out.println("Zooom meeeting response {}");
            return zEntity.getBody();
        } else {
            System.out.println("Error while creating zoom meeting {}");
        }
        return zoomMeetingObjectDTO;
    }


private String generateZoomJWTTOken() {
    String id = UUID.randomUUID().toString().replace("-", "");
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    Date creation = new Date(System.currentTimeMillis());
    Date tokenExpiry = new Date(System.currentTimeMillis() + (1000 * 60));
    String secretKey = "wcl84aAz6DuGc2Pf83uTbb4B45XaqJCSLN0Q";
    String accessKey = "rGLWr08jR-OOTRTIUsfR9Q";
    Key key = Keys
        .hmacShaKeyFor(secretKey.getBytes());
    return Jwts.builder()
        .setId(id)
        .setIssuer(accessKey)
        .setIssuedAt(creation)
        .setSubject("")
        .setExpiration(tokenExpiry)
        .signWith(key, signatureAlgorithm)
        .compact();
}
}

   