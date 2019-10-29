package br.com.codenation.repository;

import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.domain.Jogador;
import br.com.codenation.domain.Time;

import java.util.*;
import java.util.stream.Collectors;

public class TimeRepository {
    private Set<Time> times;

    public TimeRepository(){
        init();
    }

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

    public void setCapitao(Long idJogador){
       Time time = times.stream().filter(t -> t.getJogadores().stream().anyMatch(j -> j.getId().equals(idJogador))).findFirst().orElseThrow(JogadorNaoEncontradoException::new);
       time.setCapitao(time.getJogadores().stream().filter(j -> j.getId().equals(idJogador)).findFirst().orElse(null));
    }

    public  String getNomeById(Long idTime){
        Time time =  getTimeById(idTime);
        return time.getNome();
    }

    public boolean existe(Long idTime){
        return times.stream().anyMatch(t -> t.getId().equals(idTime));
    }

    public Jogador getCapítao(Long idTime){
        Time time = getTimeById(idTime);
        if(Objects.isNull(time.getCapitao())){
            throw new CapitaoNaoInformadoException();
        }else{
            return time.getCapitao();
        }
    }

    public List<Long> getJogadoresByIdTime(Long idTime){
        Time time = getTimeById(idTime);
        return time.getJogadores().stream().map(Jogador::getId).collect(Collectors.toList());
    }

    public Long getMelhorJogadorTime(Long idTime){
        Time time = getTimeById(idTime);
        return time.getJogadores().stream()
                .sorted(Comparator.comparing(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId))
                .findFirst()
                .orElse(null)
                .getId();
    }

    public Long getJogadorMaisVelhoTime(Long idTime){
        Time time = getTimeById(idTime);
        return time.getJogadores().stream()
                .sorted(Comparator.comparing(Jogador::getDataNascimento).thenComparing(Jogador::getId))
                .findFirst()
                .orElse(null)
                .getId();
    }

    public Long getJogadorMaiorSalario(Long idTime){
        Time time = getTimeById(idTime);
        return time.getJogadores().stream()
                .sorted(Comparator.comparing(Jogador::getSalario).reversed().thenComparing(Jogador::getId))
                .findFirst()
                .orElse(null)
                .getId();
    }

    public Time getTimeById(Long idTime){
        if(Objects.isNull(idTime)) throw new NullPointerException("ID do Time nulo");
        Time time = times.stream().filter(t -> t.getId().equals(idTime)).findFirst().orElseThrow(TimeNaoEncontradoException::new);
        return time;
    }

    public List<Time> getAllTimes(){
        return times.stream().sorted(Comparator.comparing(Time::getId)).collect(Collectors.toList());
    }

}
