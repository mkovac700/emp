package org.foi.emp.podaci;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor()
public class Korisnik {

  @Getter
  @Setter
  private String korisnicko_ime;

  @Getter
  @Setter
  private String lozinka;

  @Getter
  @Setter
  private String secret_key;

  public Korisnik() {}
}
