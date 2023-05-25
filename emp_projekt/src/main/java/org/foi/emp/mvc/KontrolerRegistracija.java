package org.foi.emp.mvc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.emp.pomocnici.QRCodeGenerator;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Controller
@Path("registracija")
@RequestScoped
public class KontrolerRegistracija {

  private final String issuer = "EMP";

  @Resource(lookup = "java:app/jdbc/mysql")
  javax.sql.DataSource ds;

  @Inject
  private Models model;

  @GET
  @View("registracija.jsp")
  public void registracija() {
    String secretKey = generirajGAuthKey();

    model.put("secretKey", secretKey);

    String qrCodeData = generirajGAuthKeyURI(secretKey, issuer);

    String imageAsBase64 = null;
    try {
      imageAsBase64 = QRCodeGenerator.generateQRCodeImage(qrCodeData);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    model.put("imageAsBase64", imageAsBase64);
  }

  @POST
  @View("registracija.jsp")
  public void postRegistracija(@FormParam("korisnicko_ime") String korisnicko_ime,
      @FormParam("lozinka") String lozinka, @FormParam("secret_key") String secret_key) {

    String poruka = null;

    System.out.println("Secret key: " + secret_key);

    if ((korisnicko_ime != null && korisnicko_ime.trim().length() != 0)
        && (lozinka != null && lozinka.trim().length() != 0)
        && (secret_key != null && secret_key.trim().length() != 0)) {
      poruka = spremiKorisnika(korisnicko_ime, lozinka, secret_key);
    } else
      poruka = "Neispravni podaci!";

    String secretKey = generirajGAuthKey();

    model.put("secretKey", secretKey);

    String qrCodeData = generirajGAuthKeyURI(secretKey, issuer);

    String imageAsBase64 = null;
    try {
      imageAsBase64 = QRCodeGenerator.generateQRCodeImage(qrCodeData);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    model.put("imageAsBase64", imageAsBase64);

    model.put("poruka", poruka);
  }

  public String spremiKorisnika(String korisnicko_ime, String lozinka, String secret_key) {

    String rezultat = null;

    String query = "INSERT INTO korisnik (korisnicko_ime, lozinka, secret_key) VALUES (?,?,?)";

    PreparedStatement stmt = null;

    try (var con = ds.getConnection()) {

      stmt = con.prepareStatement(query);
      stmt.setString(1, korisnicko_ime);
      stmt.setString(2, lozinka);
      stmt.setString(3, secret_key);

      stmt.executeUpdate();

      rezultat = "Uspješno ste registrirani";

    } catch (SQLException e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
      rezultat = e.getMessage();
    } finally {
      try {
        if (stmt != null && !stmt.isClosed())
          stmt.close();
      } catch (SQLException e) {
        Logger.getGlobal().log(Level.SEVERE, e.getMessage());
      }
    }

    return rezultat;
  }

  private String generirajGAuthKey() {
    GoogleAuthenticator gAuth = new GoogleAuthenticator();
    GoogleAuthenticatorKey gAuthKey = gAuth.createCredentials();
    String secretKey = gAuthKey.getKey();
    return secretKey;
  }

  private String generirajGAuthKeyURI(String secret_key, String issuer) {
    StringBuilder sb = new StringBuilder();
    sb.append("otpauth://totp/");
    try {
      sb.append(URLEncoder.encode(issuer, "UTF-8"));
      sb.append("?secret=" + URLEncoder.encode(secret_key, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return sb.toString();
  }
}
