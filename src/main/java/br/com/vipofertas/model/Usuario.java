package br.com.vipofertas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
public class Usuario implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotEmpty
	private String username;

	@Column(nullable = false)
	@NotEmpty
	private String password;

	@Column(nullable = false)
	@NotEmpty
	private String nome;

	@Column(nullable = false)
	@NotEmpty
	private String sobrenome;

	@Column(nullable = false)
	@NotEmpty
	private String email;

	@Column(nullable = false)
	private Boolean ativo;

	@Column(nullable = false)
	private Date nascimento;

	private Date lastPasswordReset;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parceiro_id", referencedColumnName = "id")
	Parceiro parceiro;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Permissao> permissoes;

	/* Métodos com implementação obrigatória devido a herança */
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void addPermissao(Permissao permissao){
		if (permissoes == null){
			permissoes = new HashSet<>();
		}
		permissoes.add(permissao);
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	public Date getLastPasswordReset() {
		return this.lastPasswordReset;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auto = new ArrayList<>();
		auto.addAll(getPermissoes());
		return auto;
	}
}