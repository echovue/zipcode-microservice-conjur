package com.echovue.service;

import com.echovue.model.Distance;
import net.conjur.api.Conjur;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ZipcodeDistanceService {
    private static final Logger LOGGER = Logger.getLogger("ZipcodeDistanceService");
    private URL zipcodeapi;
    private String apiKey;


    public Optional<Double> getDistance(final String zipCode1,
                                        final String zipCode2) {
        if (StringUtils.isEmpty(apiKey)) {
            System.setProperty("CONJUR_ACCOUNT", System.getenv("CONJUR_ACCOUNT"));
            System.setProperty("CONJUR_AUTHN_API_KEY", System.getenv("CONJUR_AUTHN_API_KEY"));
            System.setProperty("CONJUR_AUTHN_LOGIN", System.getenv("CONJUR_AUTHN_LOGIN"));
            System.setProperty("CONJUR_APPLIANCE_URL", System.getenv("CONJUR_APPLIANCE_URL"));

            Conjur conjur = new Conjur();
            apiKey = conjur.variables().retrieveSecret("ZipcodeMicroservice/apiKey");
            System.out.println(apiKey);
        }
        try {
            zipcodeapi = new URL("https://www.zipcodeapi.com/rest/");

        RestTemplate restTemplate = new RestTemplate();
        return Optional.of(restTemplate.getForObject(
                zipcodeapi.toString() + apiKey + "/distance.json/"
                + zipCode1 + "/" + zipCode2 + "/mile", Distance.class).getDistance());

        } catch (MalformedURLException urlException) {
            LOGGER.log(Level.SEVERE, "Invalid URL for ZipCodeApi");
        } catch (org.springframework.web.client.HttpClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "Bad request");
        }
        return Optional.empty();
    }
}
