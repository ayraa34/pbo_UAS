package Parkir1;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class CetakStruk {
    public static void cetakStruk(String nomorTiket, String nomorPolisi, String jenisKendaraan,
                                  String tanggalMasuk, String jamMasuk, String tanggalKeluar,
                                  String durasi, String total, String pembayaran, String kembalian) {
        Document document = new Document(PageSize.A6);
        try {
            String fileName = "Struk_" + nomorTiket + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font fontHeader = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
            Font fontText = new Font(Font.FontFamily.COURIER, 10, Font.NORMAL);

            Paragraph title = new Paragraph("STRUK PARKIR", fontHeader);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subTitle = new Paragraph("UNIVERSITAS SINGAPERBANGSA KARAWANG", fontHeader);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);

            document.add(new Paragraph("=====================================", fontText));
            document.add(new Paragraph("No         : " + nomorTiket, fontText));
            document.add(new Paragraph("Plat       : " + nomorPolisi, fontText));
            document.add(new Paragraph("Jenis      : " + jenisKendaraan, fontText));
            document.add(new Paragraph("Masuk      : " + tanggalMasuk + " " + jamMasuk, fontText));
            document.add(new Paragraph("Keluar     : " + tanggalKeluar, fontText));
            document.add(new Paragraph("Durasi     : " + durasi, fontText));
            document.add(new Paragraph("Total      : " + total, fontText));
            document.add(new Paragraph("Pembayaran : " + pembayaran, fontText));
            document.add(new Paragraph("Kembalian  : " + kembalian, fontText));
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