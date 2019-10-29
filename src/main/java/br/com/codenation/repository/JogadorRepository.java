package br.com.codenation.repository;

import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
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
        validarJogador(jogador);
        jogadores.add(jogador);
    }

    @PostConstruct
    private void initDataBase(){
        if(Objects.isNull(jogadores)){
            jogadores = new HashSet<>();
        }
    }

    private void validarJogador(Jogador jogador){
        if(Objects.isNull(jogador)) throw new NullPointerException("Jogador está nulo");
        if(jogadores.stream().anyMatch(j -> j.getId().equals(jogador.getId()))) throw new IdentificadorUtilizadoException();
        if(Objects.isNull(jogador.getTime())) throw  new NullPointerException("Time do jogador está nulo");
        if(jogadores.stream().noneMatch(j -> j.getTime().getId().equals(jogador.getTime().getId()))) throw new TimeNaoEncontradoException();
    }


}
