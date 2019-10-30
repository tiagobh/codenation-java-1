package br.com.codenation.repository;

import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.domain.Jogador;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JogadorRepository {
    private List<Jogador> jogadores;

    public JogadorRepository(){
        initDataBase();
    }

    public List<Jogador> getJogadores(){
        return jogadores;
    }

    public  void inserir(Jogador jogador){
        jogadores.add(jogador);
    }

    private void initDataBase(){
        if(Objects.isNull(jogadores)){
            jogadores = new ArrayList<>();
        }
    }

    public void validarDadosJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID Jogador", id);
        parametros.put("ID Time", idTime);
        parametros.put("Nome Jogador", nome);
        parametros.put("Data Nascimento", dataNascimento);
        parametros.put("Nível habilidade", nivelHabilidade);
        parametros.put("Salário", salario);

        parametros.forEach((key, valor) ->{
            if(Objects.isNull(valor)) new NullPointerException(key + " está nulo.");
            if(valor instanceof String){
                if(((String) valor).isEmpty()) throw new IllegalArgumentException(key + " está vazio.");
            }
            if(valor instanceof BigDecimal){
                if(((BigDecimal) valor).doubleValue() <= 0) throw new IllegalArgumentException(key + " deve ser maior que Zero.");
            }
        });

        //valida jogador existente
        if(jogadores.stream().anyMatch(j -> j.getId().equals(id)))
            throw new JogadorNaoEncontradoException();

        //valida habilidade
        if(nivelHabilidade <1 || nivelHabilidade > 100){
            throw new IllegalArgumentException("Nivel de habilidade inválido.");
        }

        //valida existencia do time
        if(!DataLoader.getTimesRepo().existe(idTime)) throw new TimeNaoEncontradoException();
    }


    public String getNomeJogadorById(Long idJogador){
        Jogador jogador = getJogadorById(idJogador);
        return jogador.getNome();
    }

    public BigDecimal getSalarioJogador(Long idJogador){
        Jogador jogador = getJogadorById(idJogador);
        return jogador.getSalario();
    }

    public Jogador getJogadorById(Long idJogador){
        if(Objects.isNull(idJogador)) throw new NullPointerException();
        return jogadores.stream().filter(j -> j.getId().equals(idJogador)).findFirst().orElseThrow(JogadorNaoEncontradoException::new);
    }

    public List<Long> getTopJogadores(Integer limit){
        return jogadores.stream()
                .sorted(Comparator.comparing(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId))
                .map(Jogador::getId)
                .limit(limit)
                .collect(Collectors.toList());
    }
    //git
}
