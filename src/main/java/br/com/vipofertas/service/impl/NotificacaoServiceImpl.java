package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Notificacao;
import br.com.vipofertas.model.Usuario;
import br.com.vipofertas.repository.NotificacaoRepository;
import br.com.vipofertas.security.service.SecurityService;
import br.com.vipofertas.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Service
public class NotificacaoServiceImpl implements NotificacaoService{

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public ResponseEntity<?> notificar(Notificacao notificacao) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic OWI0ZGNlMjMtNmQzMy00YjBjLTg5ZGYtNzVkM2I1ZjZmZmU5");
            con.setRequestMethod("POST");

            String oferta_id = "0";
            if ( (notificacao.getOferta() != null) &&
                 (notificacao.getOferta().getId() != null) ) {
                oferta_id = notificacao.getOferta().getId().toString();
            }
            String titulo = "\"" + notificacao.getTitulo() + "\"";
            String descricao = "\"" + notificacao.getDescricao() + "\"";

            String strJsonBody = "{"
                    +   "\"app_id\": \"3b3c07ef-a794-4729-b32d-4e10e3f22895\","
                    +   "\"included_segments\": [\"All\"],"
                    +   "\"android_group\": \"vip\","
                    +   "\"data\": {\"oferta_id\": " + oferta_id + "},"
                    +   "\"headings\": {\"en\": " + titulo + "},"
                    +   "\"contents\": {\"en\": " + descricao + "}"
                    + "}";

            //System.out.println("strJsonBody:\n" + strJsonBody);
            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
                return ResponseEntity.status(HttpStatus.OK).body(this.salvar(notificacao));
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            //System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Retorna as notificações do parceiro pelo ID.
     **/
    @Override
    public Notificacao findByParceiroAndId(Long id) {
        Usuario usuarioLogado = securityService.userLogged();
        return notificacaoRepository.findByParceiroAndId(usuarioLogado.getParceiro(), id);
    }

    @Override
    public List<Notificacao> findByParceiro() {
        Usuario usuarioLogado = securityService.userLogged();
        return notificacaoRepository.findByParceiroOrderByIdDesc(usuarioLogado.getParceiro());
    }

    @Override
    public List<Notificacao> findAll() {
        return notificacaoRepository.findAllByOrderByIdDesc(new Date());
    }

    @Override
    public Notificacao findOne(Long id) {
        return notificacaoRepository.findOne(id);
    }

    private Notificacao salvar(Notificacao notificacao) {
        Usuario usuarioLogado = securityService.userLogged();
        notificacao.setUsuario(usuarioLogado);
        notificacao.setParceiro(usuarioLogado.getParceiro());
        notificacao.setData(new Date());
        return notificacaoRepository.save(notificacao);
    }
}