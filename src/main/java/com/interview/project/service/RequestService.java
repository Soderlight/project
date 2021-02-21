package com.interview.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.project.dao.RequestDao;
import com.interview.project.exception.ApiRequestException;
import com.interview.project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RequestService {
    private final RequestDao requestDao;
    private final ObjectMapper mapper;

    @Autowired
    public RequestService(RequestDao requestDao, ObjectMapper mapper){
        this.requestDao = requestDao;
        this.mapper = mapper;
    }

    public void save(String desc){
        Request req = new Request(UUID.randomUUID(), LocalDateTime.now(), desc);

        requestDao.save(req);
    }

    public List<String> getExchangeRates() throws IOException {
        save("Getting exchange rates");

        save("Requesting exchange rate data");
        ExchangeRates[] er = mapper.readValue(new URL("http://api.nbp.pl/api/exchangerates/tables/a?format=json"), ExchangeRates[].class);

        if (er == null)
            throw new ApiRequestException("no json found for exchange rate");

        return Arrays.stream(er)
                .flatMap(exchangeRates -> exchangeRates.getRates().stream())
                .map(Rate::getFullName)
                .collect(Collectors.toList());
    }

    public Double getValue(CalculateRequest calcReq) throws IOException {
        save("Calculating amount between currencies");

        if (Double.compare(calcReq.getAmount(), 0.0) < 0)
            throw new ApiRequestException("negative amount");

        save("Calculating " + calcReq.getAmount() + " " + calcReq.getCurrencyFrom() + " to " + calcReq.getCurrencyTo());

        ExchangeRates[] er = mapper.readValue(new URL("http://api.nbp.pl/api/exchangerates/tables/a?format=json"), ExchangeRates[].class);

        if (er == null)
            throw new ApiRequestException("no json found for exchange rate");

        List<Rate> rates = Arrays.stream(er)
                .flatMap(exchangeRates -> exchangeRates.getRates().stream())
                .collect(Collectors.toList());

        double valueFrom = rates.stream()
                .filter(rate -> calcReq.getCurrencyFrom().equals(rate.getCode()))
                .mapToDouble(Rate::getMid)
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(String.format("exchange rate for %s not found", calcReq.getCurrencyFrom())));

        double valueTo = rates.stream()
                .filter(rate -> calcReq.getCurrencyTo().equals(rate.getCode()))
                .mapToDouble(Rate::getMid).findFirst()
                .orElseThrow(() -> new ApiRequestException(String.format("exchange rate for %s not found", calcReq.getCurrencyTo())));

        return calcReq.calculate(valueFrom, valueTo);
    }

    public List<String> getExchangeRates(ExchangeRateRequest exRateReq) throws IOException {
        save("Getting exchange rates for currency list");

        save("Requested exchange rate data for currencies " + exRateReq.getCurrencies().stream()
                .map(Currency::getName)
                .collect(Collectors.joining(", ")));

        ExchangeRates[] er = mapper.readValue(new URL("http://api.nbp.pl/api/exchangerates/tables/a?format=json"), ExchangeRates[].class);

        if (er == null) {
            throw new ApiRequestException("no json found for exchange rate");
        }

        return Arrays.stream(er)
                .flatMap(exchangeRates -> exchangeRates.getRates().stream())
                .filter(rate -> exRateReq.getCurrencyCodes().contains(rate.getCode()))
                .map(rate -> rate.getCurrency() + " exchange rate: " + rate.getMid())
                .collect(Collectors.toList());
    }
}
