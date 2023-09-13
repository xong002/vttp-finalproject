package project.vttpproject.service;

import java.io.IOException;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.vttpproject.model.property.Property;
import project.vttpproject.model.property.PropertyResults;

@Service
public class OneMapAPIService {

    public PropertyResults getPropertyList(String searchVal) throws IOException {
        String searchValFormatted = searchVal.replaceAll(" ", "+");
        Integer currentPageNum = 1;
        Integer totalPageNum = 0;
        PropertyResults finalResults = new PropertyResults();

        RestTemplate template = new RestTemplate();

        String url = UriComponentsBuilder.fromUriString("https://www.onemap.gov.sg/api/common/elastic/search")
                .queryParam("searchVal", searchValFormatted)
                .queryParam("returnGeom", "Y")
                .queryParam("getAddrDetails", "Y")
                .queryParam("pageNum", currentPageNum)
                .toUriString();

        String jsonString = template.getForEntity(url, String.class).getBody();
        PropertyResults results = PropertyResults.create(jsonString);
        finalResults.setResults(new LinkedList<>());
        for(Property p1 : results.getResults()) {
            finalResults.getResults().add(p1);
        }
        finalResults.setFound(results.getFound());
        currentPageNum = results.getPageNum();
        totalPageNum = results.getTotalNumPages();
        finalResults.setTotalNumPages(results.getTotalNumPages());

        if (currentPageNum < totalPageNum) {
            for (Integer i = 2; i <= totalPageNum; i++) {
                String url2 = UriComponentsBuilder.fromUriString("https://www.onemap.gov.sg/api/common/elastic/search")
                        .queryParam("searchVal", searchValFormatted)
                        .queryParam("returnGeom", "Y")
                        .queryParam("getAddrDetails", "Y")
                        .queryParam("pageNum", i)
                        .toUriString();
                String newJsonString = template.getForEntity(url2, String.class).getBody();
                PropertyResults newResults = PropertyResults.create(newJsonString);
                for (Property p : newResults.getResults()) {
                    finalResults.getResults().add(p); // null here
                }
                
            }
        }
        return finalResults;
    }

}
