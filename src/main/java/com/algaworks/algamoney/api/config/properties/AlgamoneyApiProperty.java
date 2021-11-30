package com.algaworks.algamoney.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

    String originPermitida = "https://localhost:8080";
    final Seguranca seguranca = new Seguranca();

    public Seguranca getSeguranca() {
        return seguranca;
    }

    public String getOriginPermitida() {
        return originPermitida;
    }

    public void setOriginPermitida(String originPermitida) {
        this.originPermitida = originPermitida;
    }

    public static class Seguranca{
        boolean enableHttps;

        public boolean isEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
    }
}
