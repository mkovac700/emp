package org.foi.emp.mvc;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Controller
@Path("pocetna")
@RequestScoped
public class KontrolerPocetna {

  @Inject
  private Models model;

  @GET
  @View("index.jsp")
  public void pocetna() {

  }
}
