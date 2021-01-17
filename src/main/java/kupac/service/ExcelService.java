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
    List<Korpa> tutorials = repository.findAll();

    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(tutorials);
    return in;
  }

}
