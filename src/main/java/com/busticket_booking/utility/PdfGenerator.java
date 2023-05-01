package com.busticket_booking.utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.Date;


public class PdfGenerator {

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);


    public  void generatePDF(String filePath, String name, String lastname, String phone, String address ,
                             String busName, String departureDate, String departureCity  , String arrivalCity , long totalCost) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            addMetaData(document);
            addTitleAndTable(document,name,lastname, phone,address,busName,departureDate,departureCity,arrivalCity,totalCost);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private static void addTitleAndTable(Document document, String name, String lastname, String phone,String address ,
                                         String busName, String departureDate, String departureCity  , String arrivalCity , long totalCost)
            throws DocumentException {
        Paragraph preface = new Paragraph();


        preface.add(new Paragraph("BUS Booking Details", catFont));


        preface.add(new Paragraph(
                "Report generated by: " + "ABC Reservation Company " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));

        document.add(preface);


        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );


        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Passenger Detail"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setColspan(2);
        table.addCell(c1);

        table.addCell("Passenger Firstname");
        table.addCell(name);
        table.addCell("Passenger Lastname");
        table.addCell(lastname);
        table.addCell("Phone Number");
        table.addCell(phone);
        table.addCell("address");
        table.addCell(address);

        document.add(table);

        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );

        PdfPTable table1 = new PdfPTable(2);
        table1.setWidthPercentage(100);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c2 = new PdfPCell(new Phrase("bus Detail"));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setColspan(2);
        table1.addCell(c2);

        table1.addCell("Operating busName");
        table1.addCell(busName);
        table1.addCell("Departure Date");
        table1.addCell(departureDate);
        table1.addCell("Departure City");
        table1.addCell(departureCity);

        table1.addCell("Arrival City");
        table1.addCell(arrivalCity);
        table1.addCell("Booking total cost");
        table1.addCell("Rs - " +String.valueOf(totalCost));

        document.add(table1);

        document.close();
    }


}
