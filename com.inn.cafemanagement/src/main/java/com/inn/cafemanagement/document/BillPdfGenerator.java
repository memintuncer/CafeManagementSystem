package com.inn.cafemanagement.document;

import java.beans.JavaBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import com.inn.cafemanagement.constants.CafeManagementConstants;
import com.inn.cafemanagement.utils.CafeManagementUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
@JavaBean
public class BillPdfGenerator implements PdfGenerator {

	@Override
	public void setRectaangleInPdf(Document document) throws DocumentException {
		Rectangle rectangle = new Rectangle(577, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        document.add(rectangle);
		
	}

	@Override
	public Font getFont(String type) {
		switch (type) {
        case "Header":
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
            headerFont.setStyle(Font.BOLD);
            return headerFont;
        case "Data":
            Font dareFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
            dareFont.setStyle(Font.BOLD);
            return dareFont;
        default:
            return new Font();
    }
	}

	@Override
	public void addTableHeader(PdfPTable table) {
		Stream.of("Name", "Category", "Quantity", "Price", "Sub Total")
        .forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            header.setBackgroundColor(BaseColor.YELLOW);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });
		
	}

	@Override
	public void addRows(PdfPTable table, Map<String, Object> data) {
		table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
	}

	@Override
	public byte[] getByteArray(String filepath) throws Exception {
		File initalFile = new File(filepath);
        InputStream targetStream = new FileInputStream(initalFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
	}

	@Override
	public void generatePdf(Map<String, Object> requestMap, String filename) throws FileNotFoundException, DocumentException, JSONException {
		
		String data = "Name: " + requestMap.get("name") + "\n" + "Contact Number: " + requestMap.get("contactNumber") +
                "\n" + "Email: " + requestMap.get("email") + "\n" + "Payment Method: " + requestMap.get("paymentMethod");
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(CafeManagementConstants.DOWNLOAD_PATH+ "\\" + filename + ".pdf"));
        document.open();
        setRectaangleInPdf(document);

        // print pdf Header
        Paragraph chunk = new Paragraph("Cafe Management System", getFont("Header"));
        chunk.setAlignment(Element.ALIGN_CENTER);
        document.add(chunk);


        Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
        document.add(paragraph);

        // Create table in pdf to print data
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        addTableHeader(table);


        // Print table data
        JSONArray jsonArray = CafeManagementUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));
        for (int i = 0; i < jsonArray.length(); i++) {
            addRows(table, CafeManagementUtils.getMapFromJson(jsonArray.getString(i)));
        }

        document.add(table);

        // print pdf Footer
        Paragraph footer = new Paragraph("Total : " + requestMap.get("totalAmount") + "\n"
                + "Thank you for visiting our website.", getFont("Data"));
        document.add(footer);
        document.close();
	}
	
}
