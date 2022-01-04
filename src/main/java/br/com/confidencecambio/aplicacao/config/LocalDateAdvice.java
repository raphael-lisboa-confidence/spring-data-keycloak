package br.com.confidencecambio.aplicacao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class LocalDateAdvice {

    private final DateTimeFormatter dateFormatter;

    public LocalDateAdvice(@Value("${localdate.format}") final String localDateFormat) {
        dateFormatter = DateTimeFormatter.ofPattern(localDateFormat);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(final String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text, dateFormatter));
            }
        });
    }
}