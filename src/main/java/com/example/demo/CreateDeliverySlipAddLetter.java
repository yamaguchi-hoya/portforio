package com.example.demo;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import com.example.demo.dto.DeliveryItemDto;
import com.example.demo.form.DeliveryForm;
import com.example.demo.form.DeliveryItemFormWrapper;

public class CreateDeliverySlipAddLetter {
	public static void addLetter(PDPageContentStream contentStream, float frameX, float frameY, float tableHeight, PDFont notoSansBold, PDFont notoSansNormal,
								 DeliveryForm deliveryForm, DeliveryItemFormWrapper deliveryItemFormWrapper, String latestId) throws IOException {
		String date = new SimpleDateFormat("yyyy年MM月dd日").format(deliveryForm.getDeliveryDate());
		int subtotal = 0;
		for (DeliveryItemDto list : deliveryItemFormWrapper.getDeliveryItemList()) {
			subtotal += list.getPrice();
		}
		String subtotalStr = String.format("%,d", subtotal);
		String taxStr = String.format("%,d", subtotal/10);
		String totalStr = String.format("%,d", subtotal/10 + subtotal);
		
		contentStream.beginText();
        contentStream.setFont(notoSansNormal, 14);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 20, frameY + 624);
        contentStream.showText(deliveryForm.getDestination());
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansBold, 12);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 232, frameY + 624);
        contentStream.showText(deliveryForm.getTitle());
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 405, frameY + 682);
        contentStream.showText(latestId);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 388, frameY + 665);
        contentStream.showText(date);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 385, frameY + 551);
        contentStream.showText(deliveryForm.getPerson());
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 12);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 279 - notoSansNormal.getStringWidth(subtotalStr)*12/2000f, frameY + 512);
        contentStream.showText(totalStr + "円");
        contentStream.endText();
        if (deliveryForm.getNote() != null) {
        	String[] notes = deliveryForm.getNote().split("\\r?\\n");
        	int row = 0;
        	for(String note : notes) {
                contentStream.beginText();
                contentStream.setFont(notoSansNormal, 10);
                contentStream.setNonStrokingColor(Color.black);
                contentStream.newLineAtOffset(frameX + 6, frameY + 90 - row);
                contentStream.showText(note);
                contentStream.endText();
                row += 14;
        	}
        }
        int listNumber = 1;
        int rowNumber = 0;
        for (DeliveryItemDto list : deliveryItemFormWrapper.getDeliveryItemList()) {
        	System.out.println(list);
        	String unitPrice = String.format("%,d", list.getUnitPrice());
        	String amount = String.format("%,d", list.getAmount());
        	String price = String.format("%,d", list.getPrice());
        	String listNumberStr = Integer.toString(listNumber);
            String size = list.getSizeName();
            if (size.equals("-")) {    
            	size = "";
            	}
            contentStream.beginText();
            contentStream.setFont(notoSansNormal, 9);
            contentStream.setNonStrokingColor(Color.black);
            contentStream.newLineAtOffset(frameX + 35, frameY + 459 - rowNumber);
            contentStream.showText(listNumberStr);
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(notoSansNormal, 9);
            contentStream.setNonStrokingColor(Color.black);
            contentStream.newLineAtOffset(frameX + 86, frameY + 459 - rowNumber);
            contentStream.showText(list.getItemName() + " " + size);
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(notoSansNormal, 9);
            contentStream.setNonStrokingColor(Color.black);
            contentStream.newLineAtOffset(frameX + 326 - notoSansNormal.getStringWidth(unitPrice)*9/1000f, frameY + 459 - rowNumber);
            contentStream.showText(unitPrice + "円");
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(notoSansNormal, 9);
            contentStream.setNonStrokingColor(Color.black);
            contentStream.newLineAtOffset(frameX + 373 - notoSansNormal.getStringWidth(amount)*9/1000f, frameY + 459 - rowNumber);
            contentStream.showText(amount);
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(notoSansNormal, 9);
            contentStream.setNonStrokingColor(Color.black);
            contentStream.newLineAtOffset(frameX + 449 - notoSansNormal.getStringWidth(price)*9/1000f, frameY + 459 - rowNumber);
            contentStream.showText(price + "円");
            contentStream.endText();
            listNumber += 1;
            rowNumber += 17;
        }
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 449 - notoSansNormal.getStringWidth(subtotalStr)*9/1000f, frameY + 170);
        contentStream.showText(subtotalStr + "円");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 449 - notoSansNormal.getStringWidth(taxStr)*9/1000f, frameY + 153);
        contentStream.showText(taxStr + "円");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 449 - notoSansNormal.getStringWidth(totalStr)*9/1000f, frameY + 136);
        contentStream.showText(totalStr + "円");
        contentStream.endText();
	};
}
