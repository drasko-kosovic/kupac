package kupac.web.rest;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import kupac.domain.Korpa;
import kupac.repository.KorpaRepository;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterJob;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = ("*"))
@RestController
@RequestMapping("/report")
public class Pdf {

    @Autowired
    ApplicationContext context;

    @Autowired
    KorpaRepository KorpaRepository;

    @GetMapping(path = "/korpa/{artikal}")
    @ResponseBody

    public void getPdfKorpaArtikal(HttpServletResponse response, @PathVariable String artikal) throws Exception {

        Resource resource = context.getResource("classpath:reports/Artikli.jrxml");

        InputStream inputStream = resource.getInputStream();
        JasperReport report = JasperCompileManager.compileReport(inputStream);

        Map<String, Object> params = new HashMap<>();

        List<Korpa> korpa = KorpaRepository.findByArtikal(artikal);

        // Data source Set
        JRDataSource dataSource = new JRBeanCollectionDataSource(korpa);
        params.put("datasource", dataSource);

        // Make jasperPrint
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
        // Media Type
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        // Export PDF Stream
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @GetMapping(path = "/korpa")
    @ResponseBody

    public void getPdfKorpaAll(HttpServletResponse response) throws Exception {

        Resource resource = context.getResource("classpath:reports/Artikli.jrxml");

        InputStream inputStream = resource.getInputStream();
        JasperReport report = JasperCompileManager.compileReport(inputStream);

        Map<String, Object> params = new HashMap<>();

        List<Korpa> korpa = (List<Korpa>) KorpaRepository.findAll();

        // Data source Set
        JRDataSource dataSource = new JRBeanCollectionDataSource(korpa);
        params.put("datasource", dataSource);

        // Make jasperPrint
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
        // Media Type
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        // Export PDF Stream
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

}
