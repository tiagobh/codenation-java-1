package br.com.codenation.repository;

import java.util.Objects;

public final class DataLoader {
    private static TimeRepository timesDb;
    private static JogadorRepository jogadoresDb;

    public static TimeRepository getTimesRepo(){
        if(Objects.isNull(timesDb)){
            timesDb = new TimeRepository();
        }
        return timesDb;
    }

    public static JogadorRepository getJogadoresRepo(){
        if(Objects.isNull(jogadoresDb)){
            jogadoresDb = new JogadorRepository();
        }
        return jogadoresDb;
    }

    private DataLoader(){}
}
