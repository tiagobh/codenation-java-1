package br.com.codenation.repository;

import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.domain.Jogador;
import br.com.codenation.domain.Time;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

public class TimeRepository {
    private Set<Time> times;

    @PostConstruct
    private void init(){
        if(Objects.isNull(times)){
            times = new HashSet<>();
        }
    }

    public void inserir(Time time){
        if(times.stream().anyMatch(t -> t.getId().equals(time.getId()))){
            throw new IdentificadorUtilizadoException("Este identificador já existe.");
        }

        times.add(time);
    }

    public void definirCapitao(Long idJogador){
        Jogador jogador = times.stream().filter(t -> t.getJogadores().stream().filter(j -> j.getId().equals(idJogador))).findFirst();
        Time timeDoJogador = times.stream().filter(t -> t.getJogadores().stream().filter(j -> j.getId().equals(idJogador)));

    }

    public  String getNomeDoTime(Long idTime){
        if(times.stream().noneMatch(t -> t.getId().equals(idTime))){
            throw new TimeNaoEncontradoException();
        }

        List<Jogador> jogadores = new ArrayList<>();
        IntSummaryStatistics sumario = jogadores.stream().collect(Collectors.summarizingInt(Jogador::getNivelHabilidade));
        Integer maiorHabilidade = sumario.getMax();

        List<Long> jog = sumario.get

    }
}
