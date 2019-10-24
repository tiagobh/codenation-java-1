package br.com.codenation.repository;

import br.com.codenation.domain.Time;

import java.util.Objects;

public final class DataLoader {
    private TimeRepository timesDb;
    private JogadorRepository jogadoresDb;

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
