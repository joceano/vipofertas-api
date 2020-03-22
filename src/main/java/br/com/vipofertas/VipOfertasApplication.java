package br.com.vipofertas;

import br.com.vipofertas.model.*;
import br.com.vipofertas.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication()
public class VipOfertasApplication {

	public static void main(String[] args) {
		SpringApplication.run(VipOfertasApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(
			UsuarioService usuarioService,
			PermissaoService permissaoService,
			PaisService paisService,
			EstadoService estadoService,
			CidadeService cidadeService,
			ParceiroService parceiroService) {
		return args -> {
			//Cria um País
			Pais pais = new Pais();
			pais.setId(1L);
			pais.setNome("Brasil");
			pais.setSigla("BR");
			paisService.salvar(pais);

			//Cria um Estado
			Estado estado = new Estado();
			estado.setId(1L);
			estado.setNome("Paraná");
			estado.setSigla("PR");
			estado.setPais(pais);
			estadoService.salvar(estado);

			//Cria uma Cidade
			Cidade cidade = new Cidade();
			cidade.setId(1L);
			cidade.setNome("Pato Branco");
			cidade.setEstado(estado);
			cidadeService.salvar(cidade);

			//Cria um parceiro
			Parceiro parceiro = new Parceiro();
			parceiro.setId(1L);
			parceiro.setNome("Super Teste");
			parceiro.setAtivo(true);
			parceiro.setOrdem(1L);
			parceiro.setRamo("Supermercado");
			parceiro.setImagem("patao.jpg");
			//Endereço do Parceiro
			EnderecoParceiro end = new EnderecoParceiro();
			end.setId(1L);
			end.setBairro("centro");
			end.setEmail("email@email.com");
			end.setNumero("SN");
			end.setRua("Tamoio");
			end.setTelefone("4632201244");
			end.setCidade(cidade);
			end.setParceiro(parceiro);
			Set<EnderecoParceiro> endParceiroSet = new HashSet();
			endParceiroSet.add(end);
			parceiro.setEnderecosParceiro(endParceiroSet);
			parceiroService.salvar(parceiro);

			//Cria uma permissão
			Permissao permissao = new Permissao();
			permissao.setId(1L);
			permissao.setPermissao("ROLE_USER");
			permissaoService.salvar(permissao);

			//Cria um usuário
			Usuario usuario = new Usuario();
			usuario.setId(1L);
			usuario.setNome("Joceano");
			usuario.setSobrenome("Alves de Borba");
			usuario.setUsername("99999999999");
			usuario.setEmail("alves.joceano@gmail.com");
			usuario.setPassword(new BCryptPasswordEncoder().encode("123"));
			usuario.setAtivo(true);
			usuario.setNascimento(new Date("09/18/1990"));
			usuario.addPermissao(permissao);
			usuario.setParceiro(parceiro);
			usuarioService.salvar(usuario);
		};
	}
}