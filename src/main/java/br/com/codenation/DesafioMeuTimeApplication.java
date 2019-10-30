package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.domain.Jogador;
import br.com.codenation.domain.Time;
import br.com.codenation.repository.DataLoader;
import br.com.codenation.repository.JogadorRepository;
import br.com.codenation.repository.TimeRepository;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
	JogadorRepository jogadorRepository = DataLoader.getJogadoresRepo();
	TimeRepository timeRepository = DataLoader.getTimesRepo();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		timeRepository.validarDadosTime(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
		Time novotime = new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
		System.out.println("Time inserido com sucesso");
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		jogadorRepository.validarDadosJogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario);
		Jogador novoJogador = new Jogador(id, timeRepository.getTimeById(idTime), nome, dataNascimento, nivelHabilidade, salario);
		jogadorRepository.inserir(novoJogador);
		System.out.println("Novo jogador inserido com sucesso.");
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		timeRepository.setCapitao(idJogador);
		System.out.println("Captão definido com sucesso.");
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		Long id = timeRepository.getCapítao(idTime);
		System.out.println("ID capitão do time: " + id);
		return id;
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		return jogadorRepository.getNomeJogadorById(idJogador);
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		return timeRepository.getNomeById(idTime);
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		List<Long> ids = timeRepository.getJogadoresByIdTime(idTime);
		ids.forEach(System.out::println);
		return ids;
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		return timeRepository.getMelhorJogadorTime(idTime);
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		return timeRepository.getMelhorJogadorTime(idTime);
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		List<Long> ids = timeRepository.getAllTimes();
		ids.forEach(System.out::println);
		return ids;
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		return timeRepository.getJogadorMaiorSalario(idTime);
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return jogadorRepository.getSalarioJogador(idJogador);
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		return jogadorRepository.getTopJogadores(top);
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		return timeRepository.getCorCamisaTimeFora(timeDaCasa, timeDeFora);
	}
	//git

}
