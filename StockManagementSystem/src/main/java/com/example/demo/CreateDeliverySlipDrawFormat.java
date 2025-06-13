package com.example.demo;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import com.example.demo.dto.CompanyDto;

public class CreateDeliverySlipDrawFormat {
	public static void drawFormat(PDPageContentStream contentStream, float frameX, float frameY, float tableHeight, PDFont notoSansBold, PDFont notoSansNormal, CompanyDto company) throws IOException {

//    ----------ライン----------
      contentStream.moveTo(frameX + 7, frameY + 618);
      contentStream.lineTo(frameX + 262, frameY + 618);
      contentStream.setLineWidth(1);
      contentStream.stroke();
      contentStream.moveTo(frameX + 379, frameY + 662);
      contentStream.lineTo(frameX + 464, frameY + 662);
      contentStream.setLineWidth(1);
      contentStream.stroke();
      contentStream.moveTo(frameX + 379, frameY + 679);
      contentStream.lineTo(frameX + 464, frameY + 679);
      contentStream.setLineWidth(1);
      contentStream.stroke();
      
//    ----------テーブル----------
      int col1 = 0;
      int col2 = 0;
      
      contentStream.addRect(frameX + 275, frameY + 131, 104, 51);
      contentStream.addRect(frameX, frameY + 471, 464, 17);
      contentStream.addRect(frameX, frameY + 506, 225, 22);
      contentStream.setNonStrokingColor(0.9f, 0.9f, 0.9f);
      contentStream.fill();
      contentStream.stroke();
      
      contentStream.addRect(frameX, frameY + 506, 225, 22);
      contentStream.addRect(frameX + 225, frameY + 506, 117, 22);
     	contentStream.stroke();
  	
      for (int i=0; i<18; i++) {
      	contentStream.addRect(frameX, frameY + col1 + 182, 80, 17);
      	contentStream.addRect(frameX + 80, frameY + col1 + 182, 195, 17);
      	contentStream.addRect(frameX + 275, frameY + col1 + 182, 66, 17);
      	contentStream.addRect(frameX + 341, frameY + col1 + 182, 38, 17);
      	contentStream.addRect(frameX + 379, frameY + col1 + 182, 85, 17);
      	contentStream.stroke();
      	col1 += tableHeight;
      }
      for (int i=0; i<3; i++) {
      	contentStream.addRect(frameX + 275, frameY + col2 + 131, 104, 17);
      	contentStream.addRect(frameX + 379, frameY + col2 + 131, 85, 17);
      	contentStream.setStrokingColor(Color.black);
      	contentStream.stroke();
      	col2+= tableHeight;
      }
		
		//      ----------備考----------
        contentStream.addRect(frameX, frameY, 465, 105);
        contentStream.stroke();

//      ----------文字配置----------
        contentStream.beginText();
        contentStream.setFont(notoSansBold, 22);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 198, frameY + 700);
        contentStream.showText("納品書");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 328, frameY + 681);
        contentStream.showText("納品書番号");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 328, frameY + 664);
        contentStream.showText("　　納品日");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 343, frameY + 626);
        contentStream.showText(company.getCompanyName());
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 343, frameY + 611);
        contentStream.showText("〒" + company.getAddressNumber());
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 343, frameY + 596);
        contentStream.showText(company.getAddress());
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 343, frameY + 581);
        contentStream.showText("電話番号");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 385, frameY + 581);
        contentStream.showText(company.getPhoneNumber());
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 343, frameY + 566);
        contentStream.showText("E-mail");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 385, frameY + 566);
        contentStream.showText(company.getMail());
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 9);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 343, frameY + 551);
        contentStream.showText("担当");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansBold, 8);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 4, frameY + 532);
        contentStream.showText("下記のとおり納品致しました。");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 88, frameY + 513);
        contentStream.showText("合計(税込)");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 31, frameY + 475);
        contentStream.showText("No.");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 169, frameY + 475);
        contentStream.showText("内容");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 299, frameY + 475);
        contentStream.showText("単価");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 351, frameY + 475);
        contentStream.showText("数量");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 413, frameY + 475);
        contentStream.showText("金額");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 318, frameY + 170);
        contentStream.showText("小計");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 313, frameY + 153);
        contentStream.showText("消費税");
        contentStream.endText();        
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 318, frameY + 136);
        contentStream.showText("合計");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(notoSansNormal, 10);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.newLineAtOffset(frameX + 3, frameY + 108);
        contentStream.showText("備考");
        contentStream.endText();
	}
}
