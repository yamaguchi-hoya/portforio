package com.example.demo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import com.example.demo.dto.CompanyDto;
import com.example.demo.form.DeliveryForm;
import com.example.demo.form.DeliveryItemFormWrapper;


public class CreateDeliverySlip {

    public static byte[] create(DeliveryForm deliveryForm, DeliveryItemFormWrapper deliveryItemFormWrapper, String latestId, CompanyDto company) {
    	if ( company.getCompanyName() == null) {
    		company.setCompanyName("会社名");
    		company.setAddressNumber("000-0000");
    		company.setAddress("住所");
    		company.setMail("メールアドレス");
    		company.setPhoneNumber("000-0000-0000");
    	}
        try (PDDocument document = new PDDocument();
			 ByteArrayOutputStream baos = new ByteArrayOutputStream()
        		) {
        	PDRectangle pageSize = PDRectangle.A4;
            PDPage page = new PDPage(pageSize);
            
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            InputStream boldStream = CreateDeliverySlip.class.getResourceAsStream("/fonts/NotoSansJP-SemiBold.ttf");
            InputStream normalStream = CreateDeliverySlip.class.getResourceAsStream("/fonts/NotoSansJP-Medium.ttf");

            PDFont notoSansBold = PDType0Font.load(document, boldStream);
            PDFont notoSansNormal = PDType0Font.load(document, normalStream);
            
            float frameX = 65;
            float frameY = 54;
            float tableHeight = 17;
            
            CreateDeliverySlipDrawFormat.drawFormat(contentStream, frameX, frameY, tableHeight, notoSansBold, notoSansNormal, company);
            CreateDeliverySlipAddLetter.addLetter(contentStream, frameX, frameY, tableHeight, notoSansBold,  notoSansNormal, deliveryForm, deliveryItemFormWrapper, latestId);
     
            contentStream.close();
            
            document.save(baos);
            byte[] pdfDocument = baos.toByteArray();
            return pdfDocument;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}