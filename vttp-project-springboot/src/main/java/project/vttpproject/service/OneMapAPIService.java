package project.vttpproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OneMapAPIService {

    public ResponseEntity<String> getPropertyList(String searchVal, Integer pageNum) throws JsonProcessingException {
        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder.fromUriString("https://www.onemap.gov.sg/api/common/elastic/search")
                .queryParam("searchVal", searchVal)
                .queryParam("returnGeom", "Y")
                .queryParam("getAddrDetails", "Y")
                .queryParam("pageNum", pageNum)
                .toUriString();

        return template.getForEntity(url, String.class);

    }

}
