package Service;


import Configuration.JacksonConfig;
import Entity.Result;
import Repository.ResultRepository;
import Validator.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

@ApplicationScoped
public class ResultService {

    private final ObjectMapper objectMapper = JacksonConfig.getMapper();
    @Inject
    private ResultRepository resultRepository;

    public void resultProcess(float x, float y, float r, LocalDateTime requestTime) {
        long startTime = System.nanoTime();

        System.out.printf("Result processing: (%f, %f, %f)%n", x, y, r);

        if (!Validator.validateParams(x, y, r)) {
            System.out.printf("Invalid parameters: (%f, %f, %f)%n", x, y, r);
            return;
        }

        Result result = new Result();

        result.setX(x);
        result.setY(y);
        result.setR(r);
        result.setRequestTime(requestTime.toString());
        result.setInArea(Validator.checkPointInArea(x, y, r));

        long finishTime = System.nanoTime();

        result.setProcessingTime(finishTime - startTime);

        Result savedResult = resultRepository.saveResult(result);

        System.out.printf("Result saved: %s%n", savedResult.toString());
    }

    public String resultGet() {
        try {
            System.out.println("Getting results from Database");
            return objectMapper.writeValueAsString(resultRepository.getAllResults());
        } catch (JsonProcessingException e) {
            System.out.println("Error while processing JSON");
            return "[]";
        }
    }

    public void resultDelete() {
        System.out.println("Deleting results from Database");
        resultRepository.removeAllResults();
    }

}
