package com.inn.cafemanagement.document;

import java.io.FileNotFoundException;
import java.util.Map;

import org.json.JSONException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;


public interface PdfGenerator {
	public  void setRectaangleInPdf(Document document) throws DocumentException;
	public Font getFont(String type);
	public  void addTableHeader(PdfPTable table);
	public void addRows(PdfPTable table, Map<String, Object> data);
	public byte[] getByteArray(String filepath) throws Exception;
	public void generatePdf(Map<String, Object> requestMap, String filename) throws FileNotFoundException, DocumentException, JSONException;
}
