package kupac.web.rest;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

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
public class Exel {

    @Autowired
    ApplicationContext context;

    @Autowired
    KorpaRepository KorpaRepository;

    @GetMapping(path = "/exel/{artikal}")
    @ResponseBody

    public void getPdfZahtjev(HttpServletResponse response, @PathVariable String artikal) throws Exception {

        Resource resource = context.getResource("classpath:reports/ArtikliExel.jrxml");

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

        JRXlsxExporter exporter = new JRXlsxExporter();
        SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
        reportConfigXLS.setSheetNames(new String[] { "sheet1" });
        exporter.setConfiguration(reportConfigXLS);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        response.setHeader("Content-Disposition", "attachment;filename=jasperReport.xlsx");
        response.setContentType("application/octet-stream");
        exporter.exportReport();
    }
}
