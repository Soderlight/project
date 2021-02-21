package com.interview.project.api;

import com.interview.project.model.CalculateRequest;
import com.interview.project.model.ExchangeRateRequest;
import com.interview.project.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("api")
@RestController
public class RequestController {
    private final RequestService requestService;

    public RequestController(@Autowired RequestService requestService){
        this.requestService = requestService;
    }

    @GetMapping(path = "/currencyRates")
    public List<String> getCurrency() throws IOException {
        return requestService.getExchangeRates();
    }

    @GetMapping(path = "/calculate")
    public double getCurrency(@RequestBody CalculateRequest calcReq) throws IOException {
        return requestService.getValue(calcReq);
    }

    @GetMapping(path = "/getExchangeRates")
    public List<String> getCurrency(@RequestBody ExchangeRateRequest exRateReq) throws IOException {
        return requestService.getExchangeRates(exRateReq);
    }
}
