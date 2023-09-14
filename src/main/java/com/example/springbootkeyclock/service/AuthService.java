package com.example.springbootkeyclock.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;



@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "http://localhost:8080/realms/myRealm/protocol/openid-connect/token";
    private static final String grant_type="password";
    private static final String client_id="keyCloak-test";
    private Object response = null;
    public Object loginAuth(String unameAndPass){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        try {

            String payload = "grant_type="+grant_type+"&client_id="+client_id+"&"+unameAndPass;

            HttpEntity<String> map1= new HttpEntity<String>(payload,headers);

           response = restTemplate.postForObject(URL,map1,Object.class);

            return response;

        }catch (Exception e){
            System.out.println( "Error : "+e.getMessage());
        }

        return null;
    }


}
