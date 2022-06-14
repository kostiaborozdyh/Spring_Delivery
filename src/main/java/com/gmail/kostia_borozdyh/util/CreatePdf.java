package com.gmail.kostia_borozdyh.util;

import com.gmail.kostia_borozdyh.dto.OrderDTO;
import com.gmail.kostia_borozdyh.entity.Order;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class CreatePdf {
    public static final String FONT = "src/main/resources/static/font/FreeSans.ttf";

    public static ByteArrayOutputStream userList(List<Order> orders) throws IOException {
        List<OrderDTO> orderList = ConvertToDTO.fromListOrder(orders);
        String text = "Заявки на відправлення вантажу";
        PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);
        doc.getPdfDocument().setDefaultPageSize(PageSize.A4.rotate());
        Paragraph p = new Paragraph(text).setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold();
        doc.add(p);
        com.itextpdf.layout.element.Table table = new Table(new float[]{2, 1, 1, 1, 2, 2, 2, 2, 3});
        table.setWidth(UnitValue.createPercentValue(100));
        Cell cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Опис").setFont(font).setBold();
        cell.setWidthPercent(20);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Об'єм").setFont(font).setBold();
        cell.setWidthPercent(5);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Ціна").setFont(font).setBold();
        cell.setWidthPercent(5);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Статус").setFont(font).setBold();
        cell.setWidthPercent(10);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Адреса").setFont(font).setBold();
        cell.setWidthPercent(10);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Місто відправлення").setFont(font).setBold();
        cell.setWidthPercent(10);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Місто прибуття").setFont(font).setBold();
        cell.setWidthPercent(10);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Дата Створення").setFont(font).setBold();
        cell.setWidthPercent(10);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Дата прибуття").setFont(font).setBold();
        cell.setWidthPercent(15);
        table.addCell(cell);
        for (OrderDTO order :
                orderList) {
            cell = new Cell();
            cell.setWidthPercent(25);
            cell.add(order.getDescription()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.setWidthPercent(5);
            cell.add(String.valueOf(order.getVolume())).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.setWidthPercent(5);
            cell.add(String.valueOf(order.getPrice())).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.setWidthPercent(10);
            cell.add(order.getPaymentStatus().getStatus()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.setWidthPercent(15);
            cell.add(order.getAddress()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.setWidthPercent(10);
            cell.add(order.getCityFrom()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.setWidthPercent(10);
            cell.add(order.getCityTo()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.setWidthPercent(10);
            cell.add(String.valueOf(order.getDateCreate())).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.setWidthPercent(10);
            cell.add(String.valueOf(order.getDateOfArrival())).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
        }
        // Step-6 Adding Table to document
        doc.add(table);
        doc.close();
        return baos;
    }

    public static ByteArrayOutputStream managerList(List<Order> orders) throws IOException {
        List<OrderDTO> orderList = ConvertToDTO.fromListOrder(orders);
        PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);
        doc.getPdfDocument().setDefaultPageSize(PageSize.A4.rotate());
        Paragraph p = new Paragraph("Заявки на відправлення вантажу").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold();
        doc.add(p);
        Table table = new Table(new float[]{1, 1, 1, 1, 2, 2, 2, 2, 3});
        table.setWidth(UnitValue.createPercentValue(100));
        Cell cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Номер").setFont(font).setBold();
        cell.setWidthPercent(5);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Опис").setFont(font).setBold();
        cell.setWidthPercent(20);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Ціна").setFont(font).setBold();
        cell.setWidthPercent(5);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Замовник").setFont(font).setBold();
        cell.setWidthPercent(10);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Статус").setFont(font).setBold();
        cell.setWidthPercent(10);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Місто відправлення").setFont(font).setBold();
        cell.setWidthPercent(15);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Місто прибуття").setFont(font).setBold();
        cell.setWidthPercent(15);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Дата Створення").setFont(font).setBold();
        cell.setWidthPercent(10);
        table.addCell(cell);
        cell = new Cell();
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add("Дата прибуття").setFont(font).setBold();
        cell.setWidthPercent(10);
        table.addCell(cell);
        for (OrderDTO order :
                orderList) {
            cell = new Cell();
            cell.add(order.getId().toString()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.add(order.getDescription()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.add(String.valueOf(order.getPrice())).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.add(String.valueOf(order.getUser().getLogin())).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.add(order.getPaymentStatus().getStatus()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.add(order.getCityFrom()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.add(order.getCityTo()).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.add(String.valueOf(order.getDateCreate())).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            cell = new Cell();
            cell.add(String.valueOf(order.getDateOfArrival())).setFont(font);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
        }
        // Step-6 Adding Table to document
        doc.add(table);
        doc.close();
        return baos;
    }

    public static ByteArrayOutputStream userOrder(OrderDTO order) throws IOException {
        String text = "Заявка№" + order.getId();
        String sb = "Опис: " + order.getDescription() + "\n" +
                "Вага: " + order.getWeight() + "\n" +
                "Об'єм: " + order.getVolume() + "\n" +
                "Ціна: " + order.getPrice() + "\n" +
                "Місто відправлення: " + order.getCityFrom() + "\n" +
                "Місто прибуття: " + order.getCityTo() + "\n" +
                "Адреса: " + order.getAddress() + "\n" +
                "Дата створення: " + order.getDateCreate() + "\n" +
                "Дата прибуття: " + order.getDateOfArrival() + "\n" +
                "Статус: " + order.getPaymentStatus().getStatus() + "\n";
        PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);
        Paragraph p = new Paragraph(text).setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(14).setBold();
        doc.add(p);
        p = new Paragraph(sb).setFont(font);
        doc.add(p);

        doc.close();
        return baos;
    }
}
