package kupac.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kupac.service.ExcelService;

@CrossOrigin(origins = ("*"))
@Controller
@RequestMapping("/excel")
public class ExcelController {

  @Autowired
  ExcelService fileService;

  @GetMapping(path = "/download")
  public ResponseEntity<Resource> getFile() {
    String filename = "korpa.xlsx";
    InputStreamResource file = new InputStreamResource(fileService.load());

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
  }

  @GetMapping(path = "/download/artikal/{artikal}")
  public ResponseEntity<Resource> getFileByArtikal(@PathVariable String artikal) {
    String filename = "korpa.xlsx";
    InputStreamResource file = new InputStreamResource(fileService.loadByArtikal(artikal));

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
  }

  @GetMapping(path = "/download/cijena/{cijena}")
  public ResponseEntity<Resource> getFileByCijena(@PathVariable Integer cijena) {
    String filename = "korpa.xlsx";
    InputStreamResource file = new InputStreamResource(fileService.loadByCijena(cijena));

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
  }

  @GetMapping(path = "/download/artikalcijena")
  public ResponseEntity<Resource> getFileByCijena(@RequestParam String artikal, Integer cijena) {
    String filename = "korpa.xlsx";
    InputStreamResource file = new InputStreamResource(fileService.loadByArtikalCijena(artikal, cijena));

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
  }

}
