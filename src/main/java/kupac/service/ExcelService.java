package kupac.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kupac.domain.Korpa;
import kupac.helper.ExcelHelper;
import kupac.repository.KorpaRepository;



@Service
public class ExcelService {
  @Autowired
  KorpaRepository repository;

  public ByteArrayInputStream load() {
    List<Korpa> korpa = repository.findAll();

    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(korpa);
    return in;
  }

  public ByteArrayInputStream loadByArtikal(String artikal) {
    List<Korpa> korpa = repository.findByArtikal(artikal);

    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(korpa);
    return in;
  }

  public ByteArrayInputStream loadByCijena(Double cijena) {
    List<Korpa> korpa = repository.findByCijena(cijena);

    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(korpa);
    return in;
  }

}

