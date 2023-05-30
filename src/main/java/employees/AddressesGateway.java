package employees;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class AddressesGateway {

    public AddressDto findAddressByName(String name) {
        log.debug("findAddressByName name: {}", name);
        return WebClient.create("http://localhost:8081")
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/addresses").queryParam("name", name).build())
                .retrieve()
                .bodyToMono(AddressDto.class)
                .block();
    }
}
