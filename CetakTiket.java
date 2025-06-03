package Parkir1;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CetakTiket {
    public static void cetakTiket(String nomorTiket, String nomorPolisi, String jenisKendaraan, String tanggal, String jam) {
        Document document = new Document(PageSize.A6);
        try {
            String fileName = "Tiket_" + nomorTiket + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font fontHeader = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
            Font fontText = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL);

            Paragraph title = new Paragraph("TIKET PARKIR", fontHeader);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subTitle = new Paragraph("UNIVERSITAS SINGAPERBANGSA KARAWANG", fontHeader);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);

            document.add(new Paragraph("=====================================", fontText));
            document.add(new Paragraph("No       : " + nomorTiket, fontText));
            document.add(new Paragraph("Plat     : " + nomorPolisi, fontText));
            document.add(new Paragraph("Jenis    : " + jenisKendaraan, fontText));
            document.add(new Paragraph("Tanggal  : " + tanggal, fontText));
            document.add(new Paragraph("Jam      : " + jam, fontText));
            document.add(new Paragraph("=====================================", fontText));

            Paragraph thankYou = new Paragraph("Terima Kasih", fontText);
            thankYou.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYou);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}