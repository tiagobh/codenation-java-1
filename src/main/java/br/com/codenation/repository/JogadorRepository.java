package br.com.codenation.repository;

import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.domain.Jogador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class JogadorRepository {
    private List<Jogador> jogadores;

    public JogadorRepository(){
        initDataBase();
    }

    public List<Jogador> getJogadores(){
        return jogadores;
    }

    public  void inserir(Jogador jogador){
        validarInsercaoJogador(jogador);
        jogadores.add(jogador);
    }

    private void initDataBase(){
        if(Objects.isNull(jogadores)){
            jogadores = new ArrayList<>();
        }
    }

    private void validarInsercaoJogador(Jogador jogador){
        if(Objects.isNull(jogador)) throw new NullPointerException("Jogador está nulo");
        if(Objects.isNull(jogador.getTime())) throw new NullPointerException("Time do jogador está nulo.");
        if(jogadores.stream().anyMatch(j -> j.getId().equals(jogador.getId()))) throw new IdentificadorUtilizadoException();
        if(!DataLoader.getTimesRepo().existe(jogador.getTime().getId())) throw new TimeNaoEncontradoException();
    }

    public String getNomeJogadorBy(Long idJogador){
        Jogador jogador = jogadores.stream().filter(j -> j.getId().equals(idJogador)).findFirst().orElseThrow(JogadorNaoEncontradoException::new);
        return jogador.getNome();
    }
}
