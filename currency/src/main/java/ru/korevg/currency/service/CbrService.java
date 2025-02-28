package ru.korevg.currency.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CbrService {

    public static final String BASE_URL = "https://cbr.ru/scripts/XML_daily.asp?";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ConcurrentHashMap<LocalDate, Map<String, BigDecimal>> cache = new ConcurrentHashMap<>();
    private final WebClient webClient;

    public BigDecimal requestByCurrencyCode(String currencyCode) {
        var currentDate = LocalDate.now();
        return cache.computeIfAbsent(currentDate, this::fetchAndCacheCurrencies).get(currencyCode);
    }

    /**
     * Получение курса валют с сайта cbr.ru
     *
     */
    private Map<String, BigDecimal> fetchAndCacheCurrencies(LocalDate date) {
        try {
            String cbrUrl =  String.format(BASE_URL + "date_req=%s", date.format(DATE_FORMATTER));

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
            log.debug("Ошибка получения курса валют для  даты {}", date);
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
