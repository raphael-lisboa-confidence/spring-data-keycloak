package br.com.confidencecambio.aplicacao.service;

public class HelloService {

    private final String prefix;

    public HelloService(final String prefix) {
        this.prefix = prefix;
    }

    public String helloWorld(final String nome) {
        return prefix +" "+ nome;
    }
}
