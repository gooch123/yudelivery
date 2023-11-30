package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Getter
@AllArgsConstructor
public class MessageDTO {

    private String message;
    private String redirectURI;
    private RequestMethod method;
    private Map<String, Object> data;

}
