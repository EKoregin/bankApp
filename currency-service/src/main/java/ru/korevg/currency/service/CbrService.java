package ru.korevg.currency.service;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.korevg.currency.config.AppConfig;
import ru.korevg.currency.schema.ValCurs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CbrService {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final AppConfig appConfig;

    private final WebClient webClient;
    private final Cache cache;

    public CbrService(AppConfig appConfig, WebClient webClient, CacheManager cacheManager) {
        this.appConfig = appConfig;
        this.webClient = webClient;
        this.cache = cacheManager.getCache("dataCache");
    }

    public BigDecimal requestByCurrencyCodeAndDate(@Nullable LocalDate date, String currencyCode) {
        log.info("Request service by currency code {}", currencyCode);
        if (date == null) {
            date = LocalDate.now();
        }
        Map<String, BigDecimal> currencies = cache.get(date, Map.class);
        if (currencies == null) {
            currencies = fetchAndCacheCurrencies(date);
            cache.put(date, currencies);
        }

        BigDecimal value = currencies.get(currencyCode);
        if (value == null) {
            log.warn("Currency code {} not found", currencyCode);
        }

        return value;
    }

    /**
     * Получение курса валют с сайта cbr.ru
     *
     */
    private Map<String, BigDecimal> fetchAndCacheCurrencies(LocalDate date) {
        try {
            String baseUrl = appConfig.getUrl();
            String cbrUrl =  String.format(baseUrl + "date_req=%s", date.format(DATE_FORMATTER));

            var response = Objects.requireNonNull(webClient.get()
                            .uri(cbrUrl)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block());

            ValCurs valCurs = convertXmlToValCurs(response);

            return valCurs.getValute().stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(ValCurs.Valute::getCharCode, currency->
                            new BigDecimal(currency.getValue().replace(",", "."))));
        } catch (Exception e) {
            log.error("Ошибка получения курса валют для  даты {}", date);
            return Collections.emptyMap();
        }
    }

    private ValCurs convertXmlToValCurs(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        return (ValCurs) unmarshaller.unmarshal(reader);
    }
}
