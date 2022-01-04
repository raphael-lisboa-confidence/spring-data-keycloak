package br.com.confidencecambio.aplicacao.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class HelloServiceTest {

    @Test
    @DisplayName("Quero checar o HelloWorld")
    void queroChecarOHelloWorld() {
        String prefix = "World";
        String name = "Testevaldo";
        HelloService helloService = new HelloService(prefix);
        String helloWorld = helloService.helloWorld(name);
        Assertions.assertEquals("World Testevaldo",helloWorld);
    }

}