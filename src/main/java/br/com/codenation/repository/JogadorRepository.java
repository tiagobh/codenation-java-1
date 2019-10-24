package br.com.codenation.repository;

import br.com.codenation.domain.Jogador;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class JogadorRepository {
    private Set<Jogador> jogadores;

    public Set<Jogador> getJogadores{
        return jogadores;
    }

    public  void inserir(Jogador jogador){
        jogadores.add(jogador);
    }

    @PostConstruct
    private void initDataBase(){
        if(Objects.isNull(jogadores)){
            jogadores = new HashSet<>();
        }
    }


}
