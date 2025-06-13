package Beans;

import Service.ResultService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.time.LocalDateTime;

@Named("formBean")
@RequestScoped
public class FormBean {

    @Inject
    ResultService resultService;

    private float[] radiusValues = {1f, 1.5f, 2f, 2.5f, 3f};

    private float x;

    private float y;

    private float r;

    private LocalDateTime requestTime = LocalDateTime.now();

    public void submit() {
        resultService.resultProcess(x, y, r, requestTime);
    }

    public void clearTable() {
        resultService.resultDelete();
    }

    public String getResults() {
        return resultService.resultGet();
    }

    public ResultService getResultService() {
        return resultService;
    }

    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }

    public float[] getRadiusValues() {
        return radiusValues;
    }

    public void setRadiusValues(float[] radiusValues) {
        this.radiusValues = radiusValues;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }
}
