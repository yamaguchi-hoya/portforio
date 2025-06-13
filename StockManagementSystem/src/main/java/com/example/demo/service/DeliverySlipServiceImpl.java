package com.example.demo.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.CreateDeliverySlip;
import com.example.demo.dto.CompanyDto;
import com.example.demo.dto.DeliveryItemDto;
import com.example.demo.dto.ItemDto;
import com.example.demo.dto.SizeDto;
import com.example.demo.entity.StockFormEntity;
import com.example.demo.form.DeliveryForm;
import com.example.demo.form.DeliveryItemForm;
import com.example.demo.form.DeliveryItemFormWrapper;
import com.example.demo.repository.DeliverySlipRepository;
import com.example.demo.repository.RegistItemRepository;
import com.example.demo.repository.RegistSizeRepository;
import com.example.demo.repository.StockRecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliverySlipServiceImpl implements DeliverySlipService {
	private final RegistItemRepository registItemRepository;
	private final RegistSizeRepository registSizeRepository;
	private final DeliverySlipRepository deliverySlipRepository;
	private final StockRecordRepository stockRecordRepository;

	@Override
	public void createDeliverySlip(DeliveryForm deliveryForm, DeliveryItemFormWrapper deliveryItemFormWrapper, CompanyDto company, List<String> check) {
		Integer latestId = deliverySlipRepository.getLatestId();
		if (latestId == null) {
			latestId = 0;
		}
		latestId += 1;
        String latestIdStr = String.format("%06d", latestId);
		byte[] document =CreateDeliverySlip.create(deliveryForm, deliveryItemFormWrapper, latestIdStr, company);
		deliverySlipRepository.updateDeliveryRecord(document, deliveryForm, latestId);
		deliverySlipRepository.saveDocument(document);
		if (check.contains("download")) {
			String path = System.getProperty("user.home")+"\\Downloads\\";
			System.out.println(path);
			try(FileOutputStream fos = new FileOutputStream(path + "DS" + latestIdStr +".pdf")) {
				fos.write(document);
				System.out.println("ok");
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		if (check.contains("reflectToStock")) {
			for (DeliveryItemDto list : deliveryItemFormWrapper.getDeliveryItemList()) {
				StockFormEntity sfe = new StockFormEntity();
				int stockId = stockRecordRepository.serchStockId(list.getItemId(),list.getSizeId());
				int curentAmount = stockRecordRepository.currentAmount(stockId);
				int updateAmount = curentAmount - list.getAmount();
				sfe.setCategoryId(list.getCategoryId());
				sfe.setItemId(list.getItemId());
				sfe.setSizeId(list.getSizeId());
				sfe.setAmount(list.getAmount());
				sfe.setPerson(deliveryForm.getPerson());
				sfe.setPrice(list.getUnitPrice());
				sfe.setComment("納品書No.DS" + latestIdStr);
				sfe.setRegisterDate(deliveryForm.getDeliveryDate());
				stockRecordRepository.wrightRecordShipping(sfe,stockId);
				stockRecordRepository.stockShipping(updateAmount, stockId);
			}
		}
	}
	@Override
	public byte[] previewDeliverySlip(DeliveryForm deliveryForm, DeliveryItemFormWrapper deliveryItemFormWrapper, CompanyDto company) {
		Integer latestId = deliverySlipRepository.getLatestId();
		if (latestId == null) {
			latestId = 0;
		}
		latestId += 1;
        String latestIdStr = String.format("%06d", latestId);
		byte[] document =CreateDeliverySlip.create(deliveryForm, deliveryItemFormWrapper, latestIdStr, company);
		return document;
	}
	
	@Override
	public byte[] viewDeliveryHistory(Integer id) {
		byte[] document = deliverySlipRepository.getDeliverySlipById(id);
		return document;
	}
	@Override
	public void downloadDocument(Integer id) {
        String latestIdStr = String.format("%06d", id);
		byte[] document = deliverySlipRepository.getDeliverySlipById(id);
		String path = System.getProperty("user.home")+"\\Downloads\\";
		try(FileOutputStream fos = new FileOutputStream(path + "DS" + latestIdStr +".pdf")) {
			fos.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public List<DeliveryItemDto> deliveryItemAddToList(DeliveryItemForm form, List<DeliveryItemDto> list) {
		if (form.getAmount() == null || form.getAmount() == 0) {
			return list;
		}
		DeliveryItemDto deliveryItem = new DeliveryItemDto();
		ItemDto itemDto = registItemRepository.getSelectDtoByItemId(form.getItemId());
		SizeDto sizeDto = null; 
		if (form.getSizeId() != 0 ) {
			sizeDto = registSizeRepository.getSelectDtoByItemId(form.getSizeId());
		}
		String sizeName;
		Integer sizeId;
		if (sizeDto == null) {
			sizeName = "-";
			sizeId = 0;
		} else {
			sizeName = sizeDto.getSizeName();
			sizeId = sizeDto.getSizeId();
		}
		deliveryItem.setItemNo(list.size());
		deliveryItem.setCategoryId(form.getCategoryId());
		deliveryItem.setItemName(itemDto.getItemName());
		deliveryItem.setItemId(itemDto.getItemId());
		deliveryItem.setSizeId(sizeId);
		deliveryItem.setSizeName(sizeName);
		deliveryItem.setUnitPrice(itemDto.getPrice());
		deliveryItem.setAmount(form.getAmount());
		deliveryItem.setPrice(form.getAmount()*itemDto.getPrice());	

		list.add(deliveryItem);
		return list;
	}
	
	@Override
	public DeliveryItemFormWrapper deliveryItemAddToWrapper(DeliveryItemForm form, DeliveryItemFormWrapper list) {
		if (form.getAmount() == null || form.getAmount() == 0) {
			return list;
		}
		DeliveryItemDto deliveryItem = new DeliveryItemDto();
		ItemDto itemDto = registItemRepository.getSelectDtoByItemId(form.getItemId());
		SizeDto sizeDto = null; 
		if (form.getSizeId() != 0 ) {
			sizeDto = registSizeRepository.getSelectDtoByItemId(form.getSizeId());
		}
		String sizeName;
		Integer sizeId;
		if (sizeDto == null) {
			sizeName = "-";
			sizeId = 0;
		} else {
			sizeName = sizeDto.getSizeName();
			sizeId = sizeDto.getSizeId();
		}
		deliveryItem.setItemNo(list.getDeliveryItemList().size());
		deliveryItem.setCategoryId(form.getCategoryId());
		deliveryItem.setItemName(itemDto.getItemName());
		deliveryItem.setItemId(itemDto.getItemId());
		deliveryItem.setSizeId(sizeId);
		deliveryItem.setSizeName(sizeName);
		deliveryItem.setUnitPrice(itemDto.getPrice());
		deliveryItem.setAmount(form.getAmount());
		deliveryItem.setPrice(form.getAmount()*itemDto.getPrice());	

		list.getDeliveryItemList().add(deliveryItem);
		return list;
	}

	@Override
	public DeliveryItemFormWrapper deliveryItemRemoveFromWrapper(DeliveryItemFormWrapper list, int itemNo) {
		List<DeliveryItemDto> updateList = new ArrayList<>();
		for (int i=0; i<list.getDeliveryItemList().size(); i++) {
			DeliveryItemDto proxy = list.getDeliveryItemList().get(i);
			DeliveryItemDto dto = new DeliveryItemDto();
		  	dto.setItemNo(proxy.getItemNo());
		  	dto.setCategoryId(proxy.getCategoryId());
		  	dto.setItemId(proxy.getItemId());
		  	dto.setItemName(proxy.getItemName());
		  	dto.setSizeId(proxy.getSizeId());
		  	dto.setSizeName(proxy.getSizeName());
		  	dto.setUnitPrice(proxy.getUnitPrice());
		  	dto.setAmount(proxy.getAmount());
		  	dto.setPrice(proxy.getPrice());
		  	updateList.add(dto);
		};
		updateList.remove(itemNo);
		list.setDeliveryItemList(updateList);
		return list;
	}

}
