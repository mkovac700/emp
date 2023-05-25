package org.foi.emp.mvc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.emp.podaci.Korisnik;
import com.warrenstrange.googleauth.GoogleAuthenticator;
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
@Path("prijava")
@RequestScoped
public class KontrolerPrijava {

  @Resource(lookup = "java:app/jdbc/mysql")
  javax.sql.DataSource ds;

  @Inject
  private Models model;

  @GET
  @View("prijava.jsp")
  public void prijava() {

  }

  @POST
  @View("prijava.jsp")
  public void postPrijava(@FormParam("korisnicko_ime") String korisnicko_ime,
      @FormParam("lozinka") String lozinka,
      @FormParam("verification_code") String verification_code) {
    String poruka = null;

    Korisnik korisnik = dajKorisnika(korisnicko_ime, lozinka);
    if (korisnik == null) {
      poruka = "Neispravno korisničko ime ili lozinka";
    } else {
      int code = Integer.parseInt(verification_code);

      GoogleAuthenticator gAuth = new GoogleAuthenticator();

      System.out.println("SECRET KEY: " + korisnik.getSecret_key());
      System.out.println("CODE: " + code);

      if (gAuth.authorize(korisnik.getSecret_key(), code))
        poruka = "Uspješno se autentificirani!";
      else
        poruka = "Kod za verifikaciju nije ispravan!";
    }

    model.put("poruka", poruka);
  }

  public Korisnik dajKorisnika(String korisnicko_ime, String lozinka) {

    Korisnik korisnik = null;

    String query =
        "SELECT korisnicko_ime, lozinka, secret_key FROM korisnik WHERE korisnicko_ime = ? AND lozinka = ?";

    PreparedStatement stmt = null;
    try (var con = ds.getConnection()) {

      stmt = con.prepareStatement(query);
      stmt.setString(1, korisnicko_ime);
      stmt.setString(2, lozinka);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        korisnik = new Korisnik(rs.getString(1), rs.getString(2), rs.getString(3));
      }

      rs.close();

    } catch (SQLException e) {
      Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    } finally {
      try {
        if (stmt != null && !stmt.isClosed())
          stmt.close();
      } catch (SQLException e) {
        Logger.getGlobal().log(Level.SEVERE, e.getMessage());
      }
    }

    return korisnik;
  }
}
