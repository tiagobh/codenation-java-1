package br.com.codenation.repository;

import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.domain.Jogador;
import br.com.codenation.domain.Time;

import java.time.LocalDate;
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

    public Long getCapítao(Long idTime){
        Time time = getTimeById(idTime);
        if(Objects.isNull(time.getCapitao())){
            throw new CapitaoNaoInformadoException();
        }else{
            return time.getCapitao().getId();
        }
    }

    public List<Long> getJogadoresByIdTime(Long idTime){
        Time time = getTimeById(idTime);
        return time.getJogadores().stream()
                .sorted(Comparator.comparing(Jogador::getId))
                .map(Jogador::getId)
                .collect(Collectors.toList());
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

    public List<Long> getAllTimes(){
        return times.stream()
                .sorted(Comparator.comparing(Time::getId))
                .map(Time::getId)
                .collect(Collectors.toList());
    }

    public Time getTimeById(Long idTime){
        if(Objects.isNull(idTime)) throw new NullPointerException("ID do Time nulo");
        Time time = times.stream().filter(t -> t.getId().equals(idTime)).findFirst().orElseThrow(TimeNaoEncontradoException::new);
        return time;
    }

    public String getCorCamisaTimeFora(Long idTimeCasa, Long idTimeFora){
        Time timeCasa = getTimeById(idTimeCasa);
        Time timeFora = getTimeById(idTimeFora);

        return timeCasa.getCorUniformePrincipal().equalsIgnoreCase(timeFora.getCorUniformePrincipal()) ?
                timeFora.getCorUniformeSegundario() : timeFora.getCorUniformePrincipal();
    }

    public void validarDadosTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID do time", id);
        parametros.put("Nome do time", nome);
        parametros.put("Data Criação", dataCriacao);
        parametros.put("Cor Uniforme Principal",corUniformePrincipal);
        parametros.put("Cor Uniforme Seguncário", corUniformeSecundario);

        parametros.forEach((key, valor) ->{
            if(Objects.isNull(valor)) throw new NullPointerException(key + " é nulo.");
            if(valor instanceof String){
                if(((String) valor).isEmpty())
                    throw new IllegalArgumentException(key + " está vazio.");
            }
        });

        if(times.stream().anyMatch(t -> t.getId().equals(id)))
            throw new IdentificadorUtilizadoException();
    }
    //git

}
