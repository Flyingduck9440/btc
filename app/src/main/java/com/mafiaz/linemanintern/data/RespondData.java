package com.mafiaz.linemanintern.data;

import java.util.List;

public class RespondData {
    private String status;
    private final Data data;

    public RespondData(Data response, String status) {
        this.data = response;
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public String getStatus(){
        return status;
    }

    public static class Data {
        private List<Coins> coins;

        public List<Coins> getCoins() {
            return coins;
        }
    }

    public static class Coins {
        private final String symbol;
        private final String name;
        private final String iconUrl;
        private final String coinrankingUrl;

        public Coins(String symbol, String name, String iconUrl, String coinrankingUrl) {
            this.symbol = symbol;
            this.name = name;
            this.iconUrl = iconUrl;
            this.coinrankingUrl = coinrankingUrl;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getName() {
            return name;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public String getCoinrankingUrl() {
            return coinrankingUrl;
        }
    }

}
