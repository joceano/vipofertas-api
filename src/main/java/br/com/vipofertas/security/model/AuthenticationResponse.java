package br.com.vipofertas.security.model;

public class AuthenticationResponse {
	private String token;

	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}
	

	public AuthenticationResponse() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	
}
