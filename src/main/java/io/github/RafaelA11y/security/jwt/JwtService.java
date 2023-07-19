package io.github.RafaelA11y.security.jwt;

import io.github.RafaelA11y.VendasApplication;
import io.github.RafaelA11y.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/*  Classe que usa a biblioteca jjwt para codificar/decodificar o usuário*/
@Service //Anotação usada para incluir a classe no contêiner de dependências do Spring
public class JwtService
{
    //Chave do application.properties, retorna o tempo de expiração em minutos, a anotação @Value serve para definir um valor que está presente num
    //arquivo .properties em um campo chave/valor, para inserir o nome do campo do arquivo .properties, usa-se dentro das chaves da anotação ("${}")
    @Value("${security.jwt.expiracao}")
    private String expiracao;

    //Chave do application.properties, retorna a chave de assinatura codificada em base hexadecimal
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario)
    {
        //Long.valueOf(String number) transforma o número em String num valor Long
        long expString = Long.valueOf(expiracao);
        /*Define o tempo de expiração, com a data, hora e minutos de sua geração mais o valor de minutos de expString, ou seja: do momento em que for
        gerado mais o valor em minutos do long expString.*/
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        //Transforma o valor de dataHoraExpiracao em um Instant.
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        //Retorna um Date que recebe um Instant de parâmetro, pois a data de expiração do JWT é definida por um objeto Date de parâmetro.
        Date data = Date.from(instant);
        /* Um jwt tem 3 partes: Header, Payload, Verify Signature
           Header: token type, algorithm
           Payload: subject, name, issued at
           Verify Signature: HMACSHA256
           */
        return Jwts
                .builder()
                .setSubject(usuario.getLoguin()) //Define assunto
                .setExpiration(data) //Define tempo de expiração a partir de um objeto Date
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura) //Define o algorítmo de codificação e a chave de assinatura.
                .compact(); //Retorna o token jwt como String
    }
    /*Método de decodificação de token, setSigningKey() recebe a chave de assinatura, parseClaimsJws recebe o token, getBody() retorna o Claims com
    * os dados do jwt como subject, data de expiração. O objeto Claims representa os dados presentes no Payload do token jwt*/
    private Claims obterClaims(String token) throws ExpiredJwtException
    {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    /*Com o objeto Claim do token, obtem-se os dados do token, o Claim do token é necessário para obter por exemplo a data de expiração do token,
    * por isso a necessidade de se obter o objeto Claim com as informações do token.
    * O método tokenValido(String token) verifica se o token recebido é válido*/
    public boolean tokenValido(String token)
    {
        try
        {
            //Obtem o objeto Claims do token
            Claims claims = obterClaims(token);
            //Obtem a data de expiração do token
            Date dataExpiracao = claims.getExpiration();
            //Transforma o objeto Date dataExpiracao em um LocalDateTime data.
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            //Verifica se a data e hora atuais são depois da data de expiração de LocalDateTime data, o token é válido quando a data e hora
            // atuais não são depois da data e hora de expiração do token.
            return !LocalDateTime.now().isAfter(data);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /*Este método serve para saber quem é o usuário que mandou o token, o getSubject() retorna o subject do token jwt, pois a propriedade subject
    * contêm o loguin do usuário que gerou o token.*/
    public String obterLoguinUsuario(String token) throws ExpiredJwtException
    {
        // E se eu não fizer um cast para String? Este método obtem o loguin do usuário para saber quem ele é
        return (String) this.obterClaims(token).getSubject();
    }

//    public static void main(String[] args) {
//        //Captura o contexto da aplicação Spring
//        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
//        /*Inicia o objeto JwtService a partir da extração dos beans da classe, ou seja: o objeto será instanciado com os seus atributos String chave de
//        assinatura e expiracaqo prenchidos pelas chaves do applications.properties (setados pela anotação @Value("${chave do application.properties}"))*/
//        JwtService service = contexto.getBean(JwtService.class);
//        Usuario usuario = Usuario.builder().loguin("Fulano").build();
//        String token = service.gerarToken(usuario);
//        System.out.println("TOKEN JWT GERADO: " + token);
//
//        boolean isTokenValido = service.tokenValido(token);
//        System.out.println("O token está válido? " + isTokenValido);
//        System.out.println(service.obterLoguinUsuario(token));
//    }

}
