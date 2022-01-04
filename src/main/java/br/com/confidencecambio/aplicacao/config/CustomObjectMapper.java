package br.com.confidencecambio.aplicacao.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class CustomObjectMapper {

    private final DateTimeFormatter dateFormatter;

    public CustomObjectMapper(@Value("${localdate.format}") final String localDateFormat) {
        dateFormatter = DateTimeFormatter.ofPattern(localDateFormat);
    }

    @Bean
    public ObjectMapper mapper() {

        final ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(obterTimeModuleCustomizado());
        mapper.registerModule(new Jdk8Module());

        return mapper;
    }

    private JavaTimeModule obterTimeModuleCustomizado() {

        final JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(final LocalDate localDate, final JsonGenerator jsonGenerator,
                                  final SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(localDate.format(dateFormatter));
            }
        });

        javaTimeModule.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(final JsonParser jsonParser,
                                         final DeserializationContext deserializationContext)
                throws IOException {
                return LocalDate.parse(jsonParser.getValueAsString(), dateFormatter);
            }
        });

        return javaTimeModule;
    }
}