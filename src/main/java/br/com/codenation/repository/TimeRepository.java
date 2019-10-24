package br.com.codenation.repository;

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
        times.add(time);
    }

    public void definirCapitao(Long idJogador){

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
