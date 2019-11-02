package br.com.codenation;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.domain.Jogador;
import br.com.codenation.domain.Time;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
    public static final String TIME_INEXISTENTE = "Time inexistente";
    public static final String JOGADOR_INEXISTENTE = "Jogador inexistente";
    public static final String IDENTIFICADOR_UTILIZADO = "Identificador utilizado";
    private Set<Jogador> jogadores = new HashSet<>();
    private Set<Time> times = new HashSet<>();

    @Desafio("incluirTime")
    public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        validarTime(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
        times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
    }

    @Desafio("incluirJogador")
    public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        validarJogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario);
        jogadores.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
    }

    @Desafio("definirCapitao")
    public void definirCapitao(Long idJogador) {
        Jogador jogador = jogadores.stream().filter(j -> j.getId().equals(idJogador)).findFirst().orElseThrow(JogadorNaoEncontradoException::new);
        times.stream().filter(t -> t.getId().equals(jogador.getIdTime())).findFirst().orElseThrow(TimeNaoEncontradoException::new).setCapitao(jogador);
    }

    @Desafio("buscarCapitaoDoTime")
    public Long buscarCapitaoDoTime(Long idTime) {
        Time time = buscarTime(idTime);
        if(Objects.isNull(time.getCapitao()))
            throw new CapitaoNaoInformadoException("capitao nao informado");
        return time.getCapitao().getId();
    }

    @Desafio("buscarNomeJogador")
    public String buscarNomeJogador(Long idJogador) {
        return buscarJogador(idJogador).getNome();
    }

    @Desafio("buscarNomeTime")
    public String buscarNomeTime(Long idTime) {
        return buscarTime(idTime).getNome();
    }

    @Desafio("buscarJogadoresDoTime")
    public List<Long> buscarJogadoresDoTime(Long idTime) {
        if (!timeExiste(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        List<Long> js = jogadores.stream().filter(j -> j.getIdTime().equals(idTime))
                .sorted(Comparator.comparing(Jogador::getId))
                .map(Jogador::getId)
                .collect(Collectors.toList());

        if (Objects.isNull(js)) {
            return Collections.emptyList();
        }
        return js;
    }

    @Desafio("buscarMelhorJogadorDoTime")
    public Long buscarMelhorJogadorDoTime(Long idTime) {
        if (!timeExiste(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        Long tm = jogadores.stream().filter(j -> j.getIdTime().equals(idTime))
                .sorted(Comparator.comparing(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId))
                .limit(1)
                .findFirst()
                .orElseThrow(TimeNaoEncontradoException::new)
                .getId();
        return tm;
    }

    @Desafio("buscarJogadorMaisVelho")
    public Long buscarJogadorMaisVelho(Long idTime) {
        if (!timeExiste(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        Long ja = jogadores.stream().filter(j -> j.getIdTime().equals(idTime))
                .sorted(Comparator.comparing(Jogador::getDataNascimento).thenComparing(Jogador::getId))
                .limit(1)
                .findFirst()
                .orElseThrow(TimeNaoEncontradoException::new)
                .getId();
        return ja;
    }

    @Desafio("buscarTimes")
    public List<Long> buscarTimes() {
        if (times.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> tms = times.stream()
                .sorted(Comparator.comparing(Time::getId))
                .map(Time::getId)
                .collect(Collectors.toList());
        return tms;
    }

    @Desafio("buscarJogadorMaiorSalario")
    public Long buscarJogadorMaiorSalario(Long idTime) {
        if (!timeExiste(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }

        Long ja = jogadores.stream().filter(j -> j.getIdTime().equals(idTime))
                .sorted(Comparator.comparing(Jogador::getSalario).reversed().thenComparing(Jogador::getId))
                .limit(1)
                .findFirst()
                .orElseThrow(TimeNaoEncontradoException::new)
                .getId();
        return ja;
    }

    @Desafio("buscarSalarioDoJogador")
    public BigDecimal buscarSalarioDoJogador(Long idJogador) {
        return buscarJogador(idJogador).getSalario();
    }

    @Desafio("buscarTopJogadores")
    public List<Long> buscarTopJogadores(Integer top) {
        if (jogadores.isEmpty())
            return Collections.emptyList();
        return jogadores.stream()
                .sorted(Comparator.comparing(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId))
                .limit(top)
                .map(Jogador::getId)
                .collect(Collectors.toList());
    }

    @Desafio("buscarCorCamisaTimeDeFora")
    public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
        Time tc = buscarTime(timeDaCasa);
        Time tf = buscarTime(timeDeFora);
        if (tc.getCorUniformePrincipal().toUpperCase().equals(tf.getCorUniformePrincipal().toUpperCase())) {
            return tf.getCorUniformeSegundario();
        } else {
            return tf.getCorUniformePrincipal();
        }
    }

    private void validarJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        if (jogadorExiste(id)) {
            throw new IdentificadorUtilizadoException(IDENTIFICADOR_UTILIZADO);
        }

        if (!timeExiste(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID Jogador", id);
        parametros.put("ID Time", idTime);
        parametros.put("Nome Jogador", nome);
        parametros.put("Data Nascimento", dataNascimento);
        parametros.put("Nível habilidade", nivelHabilidade);
        parametros.put("Salário", salario);

        for(Map.Entry<String, Object> obj : parametros.entrySet()){
            if(Objects.isNull(obj.getValue())) throw new NullPointerException(obj.getKey() + " is null");
            if(obj.getValue() instanceof String){
                if(((String) obj.getValue()).isEmpty()) throw new IllegalArgumentException(obj.getKey() + " vazio.");
            }
        }

        //valida habilidade
        if (nivelHabilidade < 1 || nivelHabilidade > 100) {
            throw new IllegalArgumentException("Nivel de habilidade inválido.");
        }

    }

    private void validarTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        if(timeExiste(id)){
            throw new IdentificadorUtilizadoException("Time já existe");
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID do time", id);
        parametros.put("Nome do time", nome);
        parametros.put("Data Criação", dataCriacao);
        parametros.put("Cor Uniforme Principal", corUniformePrincipal);
        parametros.put("Cor Uniforme Seguncário", corUniformeSecundario);

        for(Map.Entry<String, Object> obj : parametros.entrySet()){
            if(Objects.isNull(obj.getValue())) throw new NullPointerException(obj.getKey() + " is null");
            if(obj.getValue() instanceof String){
                if(((String) obj.getValue()).isEmpty()) throw new IllegalArgumentException(obj.getKey() + " vazio.");
            }
        }
    }

    private boolean timeExiste(Long id) {
        return times.stream().anyMatch(t -> t.getId().equals(id));
    }

    private boolean jogadorExiste(Long id) {
        return jogadores.stream().anyMatch(j -> j.getId().equals(id));
    }

    private Time buscarTime(Long id) {
        return times.stream().filter(t -> t.getId().equals(id)).findFirst().orElseThrow(TimeNaoEncontradoException::new);
    }

    private Jogador buscarJogador(Long id) {
        return jogadores.stream().filter(j -> j.getId().equals(id)).findFirst().orElseThrow(JogadorNaoEncontradoException::new);
    }

}
