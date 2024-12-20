package com.lab6.crud_lab6.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.lab6.crud_lab6.model.Categoria;
import com.lab6.crud_lab6.service.CategoriaService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping()
    public String main(Model model) {
        List<Categoria> categorias = categoriaService.obtenerCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoria", new Categoria());
        return "categorias";
    }

    @GetMapping("/listaCategorias")
    public String showCategorias(Model model) {
        List<Categoria> categorias = categoriaService.obtenerCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoria", new Categoria());
        return "categorias";
    }

    @PostMapping("/RegistrarCategoria")
    public String registrarCategoria(@ModelAttribute Categoria categoria, Model model) {
        categoriaService.registrarCategoria(categoria);
        model.addAttribute("categoria", categoria);
        return "redirect:/listaCategorias";
    }

    @GetMapping("/EditarCategoria/{id}")
    public String editarCategoria(@PathVariable("id") Long id, Model model) {
        Optional<Categoria> categoria = categoriaService.buscarCategoria(id);
        if (categoria.isPresent()) {
            model.addAttribute("categoria", categoria.get());
            return "categoria";
        } else {
            return "redirect:/listaCategorias";
        }
    }

    @PostMapping("/GuardarEdicion")
    public String guardarEdicion(@ModelAttribute Categoria categoria) {
        categoriaService.editarCategoria(categoria);
        return "redirect:/listaCategorias";
    }

    @GetMapping("/EliminarCategoria/{id}")
    public String eliminarCategoria(@PathVariable("id") Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/listaCategorias";

    }
    

    @GetMapping("/reporte/pdf")
    public void generarReportePdf(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=categorias_reporte.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

        document.add(new Paragraph("Reporte de Categorías").setBold().setFontSize(18));

        Table table = new Table(4);
        table.addCell("ID");
        table.addCell("Talla");
        table.addCell("Tipo de Tela");
        table.addCell("Color");

        List<Categoria> categorias = categoriaService.obtenerCategorias();
        categorias.forEach(categoria -> {
            table.addCell(categoria.getId().toString());
            table.addCell(categoria.getTalla());
            table.addCell(categoria.getTipoTela());
            table.addCell(categoria.getColor());
        });

        document.add(table);
        document.close();
    }

    @GetMapping("/reporte/excel")
    public void generarReporteExcel(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=categorias_reporte.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Categorías");

        Row header = sheet.createRow(0);
        String[] headers = { "ID", "Talla", "Tipo de Tela", "Color" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            cell.setCellStyle(headerStyle);
        }

        List<Categoria> categorias = categoriaService.obtenerCategorias();
        int rowNum = 1;
        for (Categoria categoria : categorias) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(categoria.getId());
            row.createCell(1).setCellValue(categoria.getTalla());
            row.createCell(2).setCellValue(categoria.getTipoTela());
            row.createCell(3).setCellValue(categoria.getColor());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();



    }

}
