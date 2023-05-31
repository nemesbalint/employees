package employees;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureWireMock(port = 8081)
public class AddressesGatewayWireMockIT {

    @Autowired
    AddressesGateway addressesGateway;

    @Test
    public void testFindAddress() throws JsonProcessingException {

        var resource = "/api/addresses";
        var objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(new AddressDto("Budapest", "Fő út 1"));

        stubFor(get(urlPathEqualTo(resource))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)
                ));
        var addressDto = addressesGateway.findAddressByName("John Doe");

        verify(getRequestedFor(urlPathEqualTo(resource))
                .withQueryParam("name", equalTo("John Doe")));

        assertEquals("Budapest", addressDto.getCity());

    }
}
