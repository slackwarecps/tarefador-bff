/**
 * 
 */
package br.com.fabioalvaro.tarefaFacil.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fabioalvaro.tarefaFacil.config.security.TokenService;
import br.com.fabioalvaro.tarefaFacil.modelo.agendamento.Agendamento;
import br.com.fabioalvaro.tarefaFacil.modelo.forum.Perfil;
import br.com.fabioalvaro.tarefaFacil.modelo.forum.Usuario;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Lista;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Loja;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Operador;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Pergunta;
import br.com.fabioalvaro.tarefaFacil.modelo.tarefas.Rede;
import br.com.fabioalvaro.tarefaFacil.repository.AgendamentoRepository;
import br.com.fabioalvaro.tarefaFacil.repository.ListaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.LojaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.OperadorRepository;
import br.com.fabioalvaro.tarefaFacil.repository.PerguntaRepository;
import br.com.fabioalvaro.tarefaFacil.repository.RedeRepository;
import br.com.fabioalvaro.tarefaFacil.repository.UsuarioRepository;

/**
 * @author faapereira
 *
 */
@Service
public class FiltroDoUsuarioService {
	private String authorization;
	private String token;
	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;
	private Usuario usuario;
	private List<Integer> filtroDeRede = new ArrayList<>();
	private List<Integer> filtroDeLoja = new ArrayList<>();
	private List<Integer> filtroDePerfis = new ArrayList<>();

	@Autowired
	private ListaRepository listaRepository;

	@Autowired
	private RedeRepository redeRepository;

	@Autowired
	private LojaRepository lojaRepository;

	public void FiltroDoUsuario(String authorization, TokenService tokenService, UsuarioRepository usuarioRepository) {

		this.authorization = authorization.substring(7, authorization.length());
		this.token = this.authorization;
		this.usuarioRepository = usuarioRepository;

		// descriptografa o token

		pegaInformacoesDoToken(tokenService);
	}

	public String getAuthorization() {
		return authorization;
	}

	
	private void pegaInformacoesDoToken(TokenService tokenService) {
		// recuperar id do usuario

		Long idUsuario = tokenService.getIdUsuario(token);

		// carregar do banco de dados
		this.usuario = usuarioRepository.findById(idUsuario).get();
		List<Rede> listaRedes = this.usuario.getRedes();
		List<Loja> listaLojas = this.usuario.getLojas();

		preencheRedes(listaRedes);

		preencheLojas(listaLojas);

	}

	/**
	 * @param listaLojas
	 */
	private void preencheLojas(List<Loja> listaLojas) {
		filtroDeLoja.clear();
		if (listaLojas.size() == 0) {
			this.filtroDeLoja.add(-3000);
		}
		listaLojas.forEach(item2 -> {
			// verifica se tem SUDO
			if ((int) item2.getId() == -1) {
				Perfil perfil = new Perfil("ADMIN");
				
				Boolean isSuperAdmin;
				
				isSuperAdmin = userIsSuperAdmin();
				
				if (isSuperAdmin) {					
					// coloca todas as lojas sem Restricao
					filtroDeLoja.addAll(GetTodasAsLojas());
				}else {						
					List<Integer> lojasAutorizadas = new ArrayList<>();
					usuario.getRedes().forEach(rede->{
						lojasAutorizadas.addAll( getTodasAsLojasDaRede(rede) );						
						// coloca todas as lojas das Redes que ele possui
						filtroDeLoja.addAll(lojasAutorizadas);
					});
				}
				return;
			} else {
				filtroDeLoja.add((int) item2.getId());
			}

		});
		
	}

	/**
	 * @return
	 */
	private Boolean userIsSuperAdmin() {
		Boolean isSuperAdmin;
		isSuperAdmin =  usuario.getPerfis().stream().map(Perfil::getNome).filter("SUPER_ADMIN"::equals).findFirst().isPresent();
		return isSuperAdmin;
	}

	/**
	 * @param listaRedes
	 */
	private void preencheRedes(List<Rede> listaRedes) {
		filtroDeRede.clear();
		if (listaRedes.size() == 0) {
			this.filtroDeRede.add(-3000);
		}
		listaRedes.forEach(item -> {
			if (item.getId() == -1) {
				// coloca todos
				filtroDeRede.addAll(GetTodasAsRedes());
				return;
			} else {
				filtroDeRede.add(item.getId().intValue());
			}
		});
	}

	/**
	 * @return
	 */
	private Collection<? extends Integer> GetTodasAsRedes() {
		List<Integer> listaCompleta = new ArrayList<>();

		List<Rede> listaRedes = redeRepository.findAll();
		listaRedes.forEach(item -> {
			listaCompleta.add(item.getId().intValue());
		});

		return listaCompleta;
	}

	/**
	 * Busca as lojas e cria uma lista de inteiros
	 * 
	 * @return lista de integers
	 */
	private Collection<? extends Integer> GetTodasAsLojas() {
		List<Integer> listaCompleta = new ArrayList<>();
		List<Loja> listaLojas = lojaRepository.findAll();
		listaLojas.forEach(itemLoja -> {
			listaCompleta.add((int) itemLoja.getId());
		});
		return listaCompleta;
	}
	
	private Collection<? extends Integer> getTodasAsLojasDaRede(Rede rede) {
		List<Integer> listaCompleta = new ArrayList<>();
		List<Loja> listaLojas = lojaRepository.findByRede_Id(rede.getId());
		listaLojas.forEach(itemLoja -> {
			
			listaCompleta.add((int) itemLoja.getId());
		});
		return listaCompleta;
	}

	/**
	 * 
	 */
	public List<Integer> invalidaFiltro() {
		List<Integer> filtro = new ArrayList<>();
		filtro.clear();
		filtro.add(-3000);
		return filtro;

	}

	/**
	 * Localiza as redes desse operador
	 * 
	 * @param idOperador
	 * @return
	 */
	public Integer getIdDaRedeDoOperador(Long idOperador) {

		return null;
	}

	/**
	 * @param filtroDoUsuario
	 * @return
	 */
	public Boolean isOperadorPermitido(OperadorRepository operadorRepository, Long idOperador) {
		Boolean resposta = false;
		// Essa lista é da sua rede?
		// Busca o Operador FK
		Integer idRedeDoOperador = -3000;
		Optional<Operador> operador = operadorRepository.findById(idOperador);
		if (operador.isPresent()) {
			idRedeDoOperador = operador.get().getLoja().getRede().getId().intValue();
		} else {
			idRedeDoOperador = -3000;

		}

		List<Integer> listaGato = this.filtroDeRede;
		if (listaGato.contains(idRedeDoOperador)) {
			resposta = true;
		} else {
			resposta = false;
		}

		return resposta;
	}

	/**
	 * @param operadorRepository
	 * @param idLista
	 * @return
	 */
	public Boolean isOperadorPermitidoApagarLista(ListaRepository listaRepository, Long idLista) {
		Boolean permitido = false;
		Long idRedeDaLista = null;
		// qual a rede dessa lista?
		Optional<Lista> lista = listaRepository.findById(idLista);
		if (lista.isPresent()) {
			idRedeDaLista = lista.get().getOperador().getLoja().getRede().getId();
		} else {
			idRedeDaLista = -3000L;
		}
		List<Integer> listaGato = this.filtroDeRede;
		System.out.print(listaGato);
		if (listaGato.contains(idRedeDaLista.intValue())) {
			permitido = true;
		} else {
			permitido = false;
		}
		return permitido;
	}
	
	
	/**
	 * @param operadorRepository
	 * @param idLista
	 * @return
	 */
	public Boolean isUsuarioPermitidoApagarAgendamento(AgendamentoRepository agendamentoRepository, Long idAgendamento) {
		Boolean permitido = false;
		Long idRedeDaListaModelo = null;
		// qual a rede desse agendamente?
		Optional<Agendamento> agendamento = agendamentoRepository.findById(idAgendamento);
		if (agendamento.isPresent()) {
			idRedeDaListaModelo = agendamento.get().getListaModelo().getId();
		} else {
			idRedeDaListaModelo = -3000L;
		}
		List<Integer> listaGato = this.filtroDeRede;
	
		if (listaGato.contains(idRedeDaListaModelo.intValue())) {
			permitido = true;
		} else {
			permitido = false;
		}
		return permitido;
	}	
	
	
	

	/**
	 * @param idLista
	 * @return
	 */
	public Boolean isPermitidoAcessarLista(ListaRepository listaRepository, Long idLista) {
		Boolean permitido = false;
		Long idRedeDessaLista = null;
		// qual a rede dessa lista?
		Optional<Lista> lista = listaRepository.findById(idLista);
		if (lista.isPresent()) {
			idRedeDessaLista = lista.get().getOperador().getLoja().getRede().getId();
		} else {
			idRedeDessaLista = -3000L;
		}
		List<Integer> listaDeRedesDoUsuario = this.filtroDeRede;
		System.out.print(listaDeRedesDoUsuario);
		if (listaDeRedesDoUsuario.contains(idRedeDessaLista.intValue())) {
			permitido = true;
		}
		return permitido;
	}
	
	
	/**
	 * @param idLista
	 * @return
	 */
	public Boolean isPermitidoAcessarAgendamento(AgendamentoRepository agendamentoRepository, Long idAgendamento) {
		Boolean permitido = false;
		Long idRedeDesseAgendamento = null;
		// qual a rede dessa lista?
		Optional<Agendamento> agendamento = agendamentoRepository.findById(idAgendamento);
		if (agendamento.isPresent()) {
			idRedeDesseAgendamento = agendamento.get().getListaModelo().getOperador().getLoja().getRede().getId();
		} else {
			idRedeDesseAgendamento = -3000L;
		}
		List<Integer> listaDeRedesDoUsuario = this.filtroDeRede;
		System.out.print(listaDeRedesDoUsuario);
		if (listaDeRedesDoUsuario.contains(idRedeDesseAgendamento.intValue())) {
			permitido = true;
		}
		return permitido;
	}
	

	/**
	 * @param idPergunta
	 * @param perguntaRepository
	 * @return
	 */
	public Long RecuperaIdListaPorPerguntaId(Long idPergunta, PerguntaRepository perguntaRepository) {
		// Qual a Lista dessa Pergunta
		Long idLista = null;
		Optional<Pergunta> perguntaLocalizada = perguntaRepository.findById(idPergunta);
		if (perguntaLocalizada.isPresent()) {
			idLista = perguntaLocalizada.get().getLista().getId();
		}
		return idLista;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Integer> getFiltroDeRede() {
		return filtroDeRede;
	}

	public void setFiltroDeRede(List<Integer> filtroDeRede) {
		this.filtroDeRede = filtroDeRede;
	}

	public List<Integer> getFiltroDeLoja() {
		return filtroDeLoja;
	}

	public void setFiltroDeLoja(List<Integer> filtroDeLoja) {
		this.filtroDeLoja = filtroDeLoja;
	}
	
	@Override
	public String toString() {
		return "FiltroDoUsuario [authorization=" + authorization + ", token=" + token + ", tokenService=" + tokenService
				+ ", usuarioRepository=" + usuarioRepository + ", usuario=" + usuario + ", filtroDeRede=" + filtroDeRede
				+ ", filtroDeLoja=" + filtroDeLoja + ", filtroDePerfis=" + filtroDePerfis + "]";
	}

	/**
	 * @param lojaRepository2
	 * @param idLoja
	 * @return
	 */
	public boolean isPermitidoAcessarLoja(LojaRepository lojaRepository, Long idLoja) {
		Boolean permitido = false;
		Optional<Loja> lojaLocalizada = lojaRepository.findById(idLoja);
		if (lojaLocalizada.isPresent()){
			List<Integer> listaDeRedesDoUsuario = this.filtroDeRede;
			//System.out.print(listaDeRedesDoUsuario);
			if (listaDeRedesDoUsuario.contains(lojaLocalizada.get().getRede().getId().intValue())) {
				permitido = true;
			}
		}
		return permitido;
	}


}
