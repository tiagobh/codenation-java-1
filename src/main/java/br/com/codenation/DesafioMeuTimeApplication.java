package br.com.codenation;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
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
        if (!timeInexistente(id)) {
            throw new IdentificadorUtilizadoException(IDENTIFICADOR_UTILIZADO);
        }
        if (!isTimeValido(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario)) {
            throw new IllegalArgumentException("parametros inválidos para time");
        }
        Time novotime = new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
        times.add(novotime);
    }

    @Desafio("incluirJogador")
    public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        if (!isJogadorValido(id, idTime, nome, dataNascimento, nivelHabilidade, salario)) {
            throw new IllegalArgumentException("Dados do jogador invalidos");
        }

        if (times.stream().noneMatch(t -> t.getId().equals(idTime))) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        Jogador novoJogador = new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario);
        jogadores.add(novoJogador);
    }

    @Desafio("definirCapitao")
    public void definirCapitao(Long idJogador) {
        if (jogadorInexistente(idJogador)) {
            throw new JogadorNaoEncontradoException(JOGADOR_INEXISTENTE);
        }
        Jogador jogador = buscarJogador(idJogador);
        jogadores.stream()
				.filter(j -> j.getIdTime().equals(jogador.getIdTime()))
				.forEach(j ->{
				    if(j.getId().equals(idJogador)){
				        j.setCapitao(true);
                    }else {
                        j.setCapitao(false);
                    }
				});
    }

    @Desafio("buscarCapitaoDoTime")
    public Long buscarCapitaoDoTime(Long idTime) {
        if (timeInexistente(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        //return buscarTime(idTime).getCapitao().getId();
		return jogadores.stream()
				.filter(j -> j.getIdTime().equals(idTime) && j.isCapitao())
				.findFirst()
				.get().getId();
    }

    @Desafio("buscarNomeJogador")
    public String buscarNomeJogador(Long idJogador) {
        if (jogadorInexistente(idJogador)) {
            throw new JogadorNaoEncontradoException(JOGADOR_INEXISTENTE);
        }
        return buscarJogador(idJogador).getNome();
    }

    @Desafio("buscarNomeTime")
    public String buscarNomeTime(Long idTime) {
        if (timeInexistente(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        return buscarTime(idTime).getNome();
    }

    @Desafio("buscarJogadoresDoTime")
    public List<Long> buscarJogadoresDoTime(Long idTime) {
        if (timeInexistente(idTime)) {
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
        if (timeInexistente(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        return jogadores.stream().filter(j -> j.getIdTime().equals(idTime))
                .sorted(Comparator.comparing(Jogador::getNivelHabilidade).reversed().thenComparing(Jogador::getId))
                .limit(1)
                .findFirst()
                .orElse(null)
                .getId();
    }

    @Desafio("buscarJogadorMaisVelho")
    public Long buscarJogadorMaisVelho(Long idTime) {
        if (timeInexistente(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        return jogadores.stream().filter(j -> j.getIdTime().equals(idTime))
                .sorted(Comparator.comparing(Jogador::getDataNascimento).thenComparing(Jogador::getId))
                .limit(1)
                .findFirst()
                .orElse(null)
                .getId();
    }

    @Desafio("buscarTimes")
    public List<Long> buscarTimes() {
        if (times.isEmpty()) {
            return Collections.emptyList();
        }
        return times.stream()
                .sorted(Comparator.comparing(Time::getId))
                .map(Time::getId)
                .collect(Collectors.toList());
    }

    @Desafio("buscarJogadorMaiorSalario")
    public Long buscarJogadorMaiorSalario(Long idTime) {
        if (timeInexistente(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        return jogadores.stream().filter(j -> j.getIdTime().equals(idTime))
                .sorted(Comparator.comparing(Jogador::getSalario).reversed().thenComparing(Jogador::getId))
                .limit(1)
                .findFirst()
                .orElse(null)
                .getId();
    }

    @Desafio("buscarSalarioDoJogador")
    public BigDecimal buscarSalarioDoJogador(Long idJogador) {
        if (jogadorInexistente(idJogador)) {
            throw new JogadorNaoEncontradoException(JOGADOR_INEXISTENTE);
        }
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
        if (timeInexistente(timeDaCasa)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }
        if (timeInexistente(timeDeFora)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }

        Time tc = buscarTime(timeDaCasa);
        Time tf = buscarTime(timeDeFora);

        if (tc.getCorUniformePrincipal().toUpperCase().equals(tf.getCorUniformePrincipal().toUpperCase())) {
            return tf.getCorUniformeSegundario();
        } else {
            return tf.getCorUniformeSegundario();
        }
    }

    private boolean isJogadorValido(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        if (!jogadorInexistente(id)) {
            throw new IdentificadorUtilizadoException(IDENTIFICADOR_UTILIZADO);
        }

        if (timeInexistente(idTime)) {
            throw new TimeNaoEncontradoException(TIME_INEXISTENTE);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID Jogador", id);
        parametros.put("ID Time", idTime);
        parametros.put("Nome Jogador", nome);
        parametros.put("Data Nascimento", dataNascimento);
        parametros.put("Nível habilidade", nivelHabilidade);
        parametros.put("Salário", salario);

        parametros.forEach((key, valor) -> {
            if (Objects.isNull(valor)) new IllegalArgumentException(key + " está nulo.");
            if (valor instanceof String) {
                if (((String) valor).isEmpty()) throw new IllegalArgumentException(key + " está vazio.");
            }
            if (valor instanceof BigDecimal) {
                if (((BigDecimal) valor).doubleValue() <= 0)
                    throw new IllegalArgumentException(key + " deve ser maior que Zero.");
            }
        });

        //valida habilidade
        if (nivelHabilidade < 1 || nivelHabilidade > 100) {
            throw new IllegalArgumentException("Nivel de habilidade inválido.");
        }

        return true;
    }

    private boolean isTimeValido(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        if (!timeInexistente(id)) {
            throw new IdentificadorUtilizadoException(IDENTIFICADOR_UTILIZADO);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID do time", id);
        parametros.put("Nome do time", nome);
        parametros.put("Data Criação", dataCriacao);
        parametros.put("Cor Uniforme Principal", corUniformePrincipal);
        parametros.put("Cor Uniforme Seguncário", corUniformeSecundario);

        parametros.forEach((key, valor) -> {
            if (Objects.isNull(valor)) throw new IllegalArgumentException(key + " é nulo.");
            if (valor instanceof String) {
                if (((String) valor).isEmpty())
                    throw new IllegalArgumentException(key + " está vazio.");
            }
        });

        return true;
    }

    private boolean timeInexistente(Long id) {
        return times.stream().noneMatch(t -> t.getId().equals(id));
    }

    private boolean jogadorInexistente(Long id) {
        return jogadores.stream().noneMatch(j -> j.getId().equals(id));
    }

    private Time buscarTime(Long id) {
        return times.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }

    private Jogador buscarJogador(Long id) {
        return jogadores.stream().filter(j -> j.getId().equals(id)).findFirst().get();
    }

}
