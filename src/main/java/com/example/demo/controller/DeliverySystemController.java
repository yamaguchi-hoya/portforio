package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.DeliveryHistoryDAOImpl;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CompanyDto;
import com.example.demo.dto.DeliveryDashboardDtoWrapper;
import com.example.demo.dto.DeliveryHistoryDto;
import com.example.demo.dto.DeliveryItemDto;
import com.example.demo.dto.ItemDto;
import com.example.demo.dto.SizeDto;
import com.example.demo.form.CompanyForm;
import com.example.demo.form.DeliveryForm;
import com.example.demo.form.DeliveryHistoryForm;
import com.example.demo.form.DeliveryItemForm;
import com.example.demo.form.DeliveryItemFormWrapper;
import com.example.demo.form.DeliverySlipIdWrapper;
import com.example.demo.repository.DeliverySlipRepository;
import com.example.demo.service.CompanyService;
import com.example.demo.service.DeliverySlipService;
import com.example.demo.service.RegistCategoryService;
import com.example.demo.service.RegistItemService;
import com.example.demo.service.RegistSizeService;
import com.example.demo.service.StockRecordService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"deliveryItemList", "deliveryForm"})
public class DeliverySystemController {
	private final DeliverySlipService deliverySlipService;
	private final RegistCategoryService registCategoryService;
	private final RegistItemService registItemService;
	private final RegistSizeService registSizeService;
	private final StockRecordService stockRecordService;
	private final CompanyService companyService;
	private final DeliverySlipRepository deliverySlipRepository;
	
	@Autowired
	DeliveryHistoryDAOImpl deliveryHistoryDAO;
	
	//セッションスコープ（List<DeliveryItemDto>）
	@ModelAttribute("deliveryItemList")
	public DeliveryItemFormWrapper deliveryItemList() {
		DeliveryItemFormWrapper wrapper = new DeliveryItemFormWrapper();
		return wrapper;
	}
	//セッションスコープ（List<DeliveryItemDto>）
	@ModelAttribute("deliveryForm")
	public DeliveryForm deliveryForm() {
		DeliveryForm form = new DeliveryForm();
		return form;
	}
	
	
	@GetMapping("/delivery-top")
	public String deliveryTop(Model model) {
		LocalDate nowDate = LocalDate.now();
		DeliveryDashboardDtoWrapper wrapper = deliverySlipService.getDeliveryDashboard(nowDate);
		int totalPrice = 0;
		for (Map.Entry<String, Integer> map : wrapper.getCategoryPriceTotal().entrySet()) {
			totalPrice = totalPrice + map.getValue();
		}
		int totalAmount = 0;
		for (Map.Entry<String, Integer> map : wrapper.getCategoryAmountTotal().entrySet()) {
			totalAmount = totalAmount + map.getValue();
		}
		model.addAttribute("dailyPrice", new ArrayList<>(wrapper.getDailyPrice().values()));
		model.addAttribute("totalDailyAmount",new ArrayList<>(wrapper.getTotalDailyAmount().values()));
		model.addAttribute("categoryDailyPriceList", wrapper.getCategoryDailyPriceList());
		model.addAttribute("categoryAmountTotal", wrapper.getCategoryAmountTotal());
		model.addAttribute("categoryPriceTotal", wrapper.getCategoryPriceTotal());
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("dayList", wrapper.getDayList());
		model.addAttribute("yearAndMonth","ダッシュボード　" + wrapper.getYear() + "年" + wrapper.getMonth() + "月");
		model.addAttribute("year", wrapper.getYear());
		model.addAttribute("month", wrapper.getMonth());
		return "delivery/delivery-top";
	}
	@PostMapping("/delivery-month-before")
	public String deliveryMonthBefore(Model model, int year, int month) {
		if (month == 1) {
			year = year - 1 ;			
			month = 12;
		} else {
			month = month - 1;
		}
		LocalDate newDate = LocalDate.of(year,month,15);
		DeliveryDashboardDtoWrapper wrapper = deliverySlipService.getDeliveryDashboard(newDate);
		int totalPrice = 0;
		for (Map.Entry<String, Integer> map : wrapper.getCategoryPriceTotal().entrySet()) {
			totalPrice = totalPrice + map.getValue();
		}
		int totalAmount = 0;
		for (Map.Entry<String, Integer> map : wrapper.getCategoryAmountTotal().entrySet()) {
			totalAmount = totalAmount + map.getValue();
		}
		model.addAttribute("dailyPrice", new ArrayList<>(wrapper.getDailyPrice().values()));
		model.addAttribute("totalDailyAmount",new ArrayList<>(wrapper.getTotalDailyAmount().values()));
		model.addAttribute("categoryDailyPriceList", wrapper.getCategoryDailyPriceList());
		model.addAttribute("categoryAmountTotal", wrapper.getCategoryAmountTotal());
		model.addAttribute("categoryPriceTotal", wrapper.getCategoryPriceTotal());
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("dayList", wrapper.getDayList());
		model.addAttribute("yearAndMonth","ダッシュボード　" + wrapper.getYear() + "年" + wrapper.getMonth() + "月");
		model.addAttribute("year", wrapper.getYear());
		model.addAttribute("month", wrapper.getMonth());
		return "delivery/delivery-top";
	}
	@PostMapping("/delivery-month-after")
	public String deliveryMonthAfter(Model model, int year, int month) {
		if (month == 12) {
			year = year + 1 ;			
			month = 1;
		} else {
			month = month + 1;
		}
		LocalDate newDate = LocalDate.of(year,month,15);
		DeliveryDashboardDtoWrapper wrapper = deliverySlipService.getDeliveryDashboard(newDate);
		int totalPrice = 0;
		for (Map.Entry<String, Integer> map : wrapper.getCategoryPriceTotal().entrySet()) {
			totalPrice = totalPrice + map.getValue();
		}
		int totalAmount = 0;
		for (Map.Entry<String, Integer> map : wrapper.getCategoryAmountTotal().entrySet()) {
			totalAmount = totalAmount + map.getValue();
		}
		model.addAttribute("dailyPrice", new ArrayList<>(wrapper.getDailyPrice().values()));
		model.addAttribute("totalDailyAmount",new ArrayList<>(wrapper.getTotalDailyAmount().values()));
		model.addAttribute("categoryDailyPriceList", wrapper.getCategoryDailyPriceList());
		model.addAttribute("categoryAmountTotal", wrapper.getCategoryAmountTotal());
		model.addAttribute("categoryPriceTotal", wrapper.getCategoryPriceTotal());
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("dayList", wrapper.getDayList());
		model.addAttribute("yearAndMonth","ダッシュボード　" + wrapper.getYear() + "年" + wrapper.getMonth() + "月");
		model.addAttribute("year", wrapper.getYear());
		model.addAttribute("month", wrapper.getMonth());
		return "delivery/delivery-top";
	}
	//---------------------------------------------------------------納品書作成-----------------------------------------------------------------------
	//納品書作成①
	@GetMapping("/create-delivery-slip-step1")
	public String CreateDeliverySlip(@ModelAttribute DeliveryItemForm deliveryItemForm, 
									 @ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper,
									 Model model) {
		List<CategoryDto> list = registCategoryService.getAll();
		model.addAttribute("categoryList",list);
		model.addAttribute("deliveryItemForm", deliveryItemForm);
		model.addAttribute("deliveryList", deliveryItemFormWrapper.getDeliveryItemList());
		return "delivery/create/create-delivery-slip-step1";
	}
	@GetMapping("/create-delivery-slip-step1/by-category")
	@ResponseBody
	public List<ItemDto> getItemByCategoryToStockHistory(@RequestParam("categoryId") int categoryId) {
		return registItemService.getSelectByCategoryId(categoryId);
	}	
	@GetMapping("/create-delivery-slip-step1/by-item")
	@ResponseBody
	public List<SizeDto> getSizeByItemToStockHistory(@RequestParam("itemId") int itemId) {
		return registSizeService.getSelectByItemId(itemId);
	}
	@GetMapping("/create-delivery-slip-step1/by-size")
	@ResponseBody
	public int getAmountBySizeToStockShipping(@RequestParam("itemId") int itemId, @RequestParam("sizeId") int sizeId) {
		return stockRecordService.serchCurrentAmount(itemId,sizeId);
	}
	//納品書作成①-商品追加
	@PostMapping("/create-delivery-slip-step1")
	public String CreateDeliverySlipStep1(@Validated @ModelAttribute DeliveryItemForm deliveryItemForm, BindingResult result,
										  @ModelAttribute("deliveryForm") DeliveryForm deliveryForm,
										  @ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper, 
										  Model model) {
		List<DeliveryItemDto> deliveryItemList = deliveryItemFormWrapper.getDeliveryItemList();
		if (result.hasErrors()) {
			List<CategoryDto> categoryList = registCategoryService.getAll();
			model.addAttribute("categoryList",categoryList);
			List<ItemDto> itemlist = registItemService.getAll();
			model.addAttribute("itemList",itemlist);
			List<SizeDto> sizelist = registSizeService.getAll();
			model.addAttribute("sizeList",sizelist);
			model.addAttribute("deliveryItemForm", deliveryItemForm);
			model.addAttribute("deliveryList", deliveryItemList);
			model.addAttribute("deliveryForm", deliveryForm);
			return "delivery/create/create-delivery-slip-step1";
		} else {
			List<CategoryDto> list = registCategoryService.getAll();
			DeliveryItemFormWrapper updateList = deliverySlipService.deliveryItemAddToWrapper(deliveryItemForm, deliveryItemFormWrapper);		
			
			DeliveryItemForm resetForm = new DeliveryItemForm();
			resetForm.setCategoryId(deliveryItemForm.getCategoryId());
			resetForm.setItemId(deliveryItemForm.getItemId());
			resetForm.setSizeId(deliveryItemForm.getSizeId());
			resetForm.setAmount(deliveryItemForm.getAmount());
			
			model.addAttribute("categoryList",list);
			model.addAttribute("deliveryItemForm", resetForm);
			model.addAttribute("deliveryList", updateList.getDeliveryItemList());
			model.addAttribute("deliveryForm", deliveryForm);
			return "delivery/create/create-delivery-slip-step1";			
		}
	}
	//納品書作成①-商品削除
	@PostMapping("/remove-delivery-item")
	public String CreateDeliverySlipStep1Remove(@ModelAttribute DeliveryItemForm deliveryItemForm,
												@ModelAttribute("deliveryForm") DeliveryForm deliveryForm,
												@ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper,
												int itemNo, Model model) {
		DeliveryItemFormWrapper updateList = deliverySlipService.deliveryItemRemoveFromWrapper(deliveryItemFormWrapper, itemNo);
		List<CategoryDto> list = registCategoryService.getAll();
		model.addAttribute("categoryList",list);
		model.addAttribute("deliveryList", updateList.getDeliveryItemList());
		model.addAttribute("deliveryItemForm", deliveryItemForm);
		model.addAttribute("deliveryForm", deliveryForm);
		return "delivery/create/create-delivery-slip-step1";
	}
	//納品書作成②
	@PostMapping("/create-delivery-slip-step1-next")
	public String CreateDeliverySlipStep1Next(@ModelAttribute DeliveryItemForm deliveryItemForm,
											  @ModelAttribute("deliveryForm") DeliveryForm deliveryForm,
											  @ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper, 
											  Model model) {
		model.addAttribute("deliveryItemForm", deliveryItemForm);
		model.addAttribute("deliveryForm", deliveryForm);
		model.addAttribute("deliveryList", deliveryItemFormWrapper.getDeliveryItemList());
		
		return "delivery/create/create-delivery-slip-step2";
	}
	//納品書作成②-戻り
	@PostMapping("/create-delivery-slip-step2-ret")
	public String CreateDeliverySlipStep2Ret(@ModelAttribute DeliveryItemForm deliveryItemForm,
											 @ModelAttribute("deliveryForm") DeliveryForm deliveryForm,
											 @ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper, 
											 Model model) {
		List<CategoryDto> list = registCategoryService.getAll();
		model.addAttribute("categoryList",list);
		model.addAttribute("deliveryItemForm", deliveryItemForm);
		model.addAttribute("deliveryForm", deliveryForm);
		model.addAttribute("deliveryList", deliveryItemFormWrapper.getDeliveryItemList());
		return "delivery/create/create-delivery-slip-step1";
	}
	//納品書作成③
	@PostMapping("/create-delivery-slip-step2-next")
	public String previewCreateDeliverySlip(@ModelAttribute DeliveryItemForm deliveryItemForm, 
											@Validated @ModelAttribute("deliveryForm") DeliveryForm deliveryForm, BindingResult result,
											@ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper, 
											Model model) {
		if (result.hasErrors()) {
			model.addAttribute("deliveryForm", deliveryForm);
			model.addAttribute("deliveryItemForm", deliveryItemForm);
			model.addAttribute("deliveryList", deliveryItemFormWrapper.getDeliveryItemList());
			return "delivery/create/create-delivery-slip-step2";
		} else {
			model.addAttribute("deliveryForm", deliveryForm);
			model.addAttribute("deliveryItemForm", deliveryItemForm);
			model.addAttribute("deliveryList", deliveryItemFormWrapper.getDeliveryItemList());
			return "delivery/create/preview-create-delivery-slip";			
		}
	}
	//納品書作成③-戻り
	@PostMapping("/preview-create-delivery-slip-ret")
	public String previewCreateDeliverySlipRet(@ModelAttribute DeliveryItemForm deliveryItemForm,
											   @ModelAttribute("deliveryForm") DeliveryForm deliveryForm,
										 	   @ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper, 
												byte[] document,
											   Model model) {
		model.addAttribute("deliveryItemForm", deliveryItemForm);
		model.addAttribute("deliveryForm", deliveryForm);
		model.addAttribute("deliveryList", deliveryItemFormWrapper.getDeliveryItemList());
		return "delivery/create/create-delivery-slip-step2";
	}
	//納品書作成③-画面内表示
	@GetMapping("/pdf/view")
	public ResponseEntity<byte[]> viewPdf(@ModelAttribute("deliveryForm") DeliveryForm deliveryForm, 
										  @ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper, 
										  Model model) {
		CompanyDto company = companyService.getCompany();
		byte[] document = deliverySlipService.previewDeliverySlip(deliveryForm, deliveryItemFormWrapper, company);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.inline().filename("document.pdf").build());
		
		model.addAttribute("document", document);
		return new ResponseEntity<>(document,headers, HttpStatus.OK);
	}
	//納品書作成④
	@PostMapping("/preview-create-delivery-slip-next")
	public String previewCreateDeliverySlipNext(@ModelAttribute DeliveryItemForm deliveryItemForm,
											@ModelAttribute("deliveryForm") DeliveryForm deliveryForm,
											@ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper, 
											Model model) {
		model.addAttribute("deliveryForm", deliveryForm);
		model.addAttribute("deliveryItemForm", deliveryItemForm);
		model.addAttribute("deliveryList", deliveryItemFormWrapper.getDeliveryItemList());
		return "delivery/create/confirm-create-delivery-slip";
	}
	//納品書作成④-戻り
	@PostMapping("/confirm-create-delivery-slip-ret")
	public String confirmCreateDeliverySlipRet(@ModelAttribute DeliveryItemForm deliveryItemForm,
											@ModelAttribute("deliveryForm") DeliveryForm deliveryForm,
											@ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper, 
											Model model) {
		model.addAttribute("deliveryForm", deliveryForm);
		model.addAttribute("deliveryItemForm", deliveryItemForm);
		model.addAttribute("deliveryList", deliveryItemFormWrapper.getDeliveryItemList());
		return "delivery/create/preview-create-delivery-slip";
	}
//	//納品書作成⑤
//	@PostMapping("/confirm-create-delivery-slip-next")
//	public String confirmCreateDeliverySlipNext(@ModelAttribute DeliveryItemForm deliveryItemForm,
//											@ModelAttribute("deliveryForm") DeliveryForm deliveryForm,
//											@ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper, 
//											@RequestParam(value = "deliveryCheck", required = false) List<String> check, 											
//											SessionStatus sessionStatus,
//											Model model,  HttpServletResponse response) {
//		CompanyDto company = companyService.getCompany();
//		deliverySlipService.createDeliverySlip(deliveryForm, deliveryItemFormWrapper, company, check, response);
//		sessionStatus.setComplete();
//		return "delivery/create/complete-create-delivery-slip";
//	}
	@PostMapping("/confirm-create-delivery-slip-next")
	public String confirmCreateDeliverySlipNext(
	        @ModelAttribute DeliveryItemForm deliveryItemForm,
	        @ModelAttribute("deliveryForm") DeliveryForm deliveryForm,
	        @ModelAttribute("deliveryItemList") DeliveryItemFormWrapper deliveryItemFormWrapper,
	        @RequestParam(value = "deliveryCheck", required = false) List<String> check,
	        SessionStatus sessionStatus,
	        RedirectAttributes redirectAttributes) {

	    CompanyDto company = companyService.getCompany();

	    // チェックされたオプションに基づいて処理
	    deliverySlipService.createDeliverySlip(
	        deliveryForm, deliveryItemFormWrapper, company, check
	    );
	    Integer latestSlipId = deliverySlipRepository.getLatestId();
	    System.out.println("latestSlipId : " + latestSlipId);
	    redirectAttributes.addAttribute("id", latestSlipId);
	    redirectAttributes.addAttribute("check", check);

	    // セッション終了
	    sessionStatus.setComplete();

	    return "redirect:/complete-create-delivery-slip?id=" + latestSlipId;
	}
	@GetMapping("/complete-create-delivery-slip")
	public String completeCreateDeliverySlip(@RequestParam("id") Integer id, Model model,	        
											 @RequestParam(value = "deliveryCheck", required = false) List<String> check) {
		System.out.println("id : " + id);
	    model.addAttribute("deliverySlipId", id);
	    model.addAttribute("check", check);
	    return "delivery/create/complete-create-delivery-slip";
	}
	
	@GetMapping("/download-delivery-slip-preview")
	public void downloadDeliverySlipPreview(@RequestParam("id") Integer id, HttpServletResponse response) {
	    byte[] document = deliverySlipRepository.getDeliverySlipById(id);

	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=DS" + String.format("%06d", id) + ".pdf");

	    try (ServletOutputStream os = response.getOutputStream()) {
	        os.write(document);
	        os.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new RuntimeException("PDFの出力に失敗しました", e);
	    }
	}
	
	
//	セッションスコープ破棄
	@GetMapping("/reset-delivery-item-list")
	public String resetDeliveryItemList(SessionStatus sessionStatus) {
	    sessionStatus.setComplete(); // セッションのdeliveryItemListを破棄
	    return "redirect:create-delivery-slip-step1";
	}
	@GetMapping("/reset-delivery-form")
	public String resetDeliveryForm(HttpSession session) {
	    session.removeAttribute("deliveryForm"); // セッションのdeliveryItemListを破棄
	    return "redirect:create-delivery-slip-step1";
	}
	
	//---------------------------------------------------------------会社情報-----------------------------------------------------------------------
	//会社情報①
	@GetMapping("/company")
	public String company(@ModelAttribute CompanyForm form, Model model) {
		model.addAttribute("companyForm", companyService.getCompany());
	    return "delivery/company/company";
	}
	//会社情報②
	@PostMapping("/company")
	public String company(@Validated @ModelAttribute CompanyForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("companyForm", form);
		    return "delivery/company/company";
		}
		if (!form.getAddressNumber().contains("-")) {
			StringBuilder sb = new StringBuilder(form.getAddressNumber());
			form.setAddressNumber(sb.insert(3,"-").toString());
		}
		if (!form.getPhoneNumber().contains("-")) {
			StringBuilder sb = new StringBuilder(form.getPhoneNumber());
			form.setPhoneNumber(sb.insert(3,"-").insert(7,"-").toString());
		}
		model.addAttribute("companyForm", form);
	    return "delivery/company/confirm-company";
	}
	//会社情報②-戻り
	@PostMapping("/confirm-company-ret")
	public String confirmCompanyRet(@ModelAttribute CompanyForm form, Model model) {
		model.addAttribute("companyForm", form);
	    return "delivery/company/company";
	}
	//会社情報③
	@PostMapping("/confirm-company")
	public String confirmCompany(@ModelAttribute CompanyForm form, Model model) {
		companyService.registCompany(form);
		model.addAttribute("companyForm", form);
	    return "delivery/company/complete-company";
	}
	
	//---------------------------------------------------------------納品履歴-----------------------------------------------------------------------
	//納品履歴①
	@GetMapping("/delivery-history")
	public String deliveryHistory(@ModelAttribute DeliveryHistoryForm historyForm, Model model) {
		model.addAttribute("historyForm", historyForm);	
		return "delivery/history/delivery-history";
	}
	//納品履歴-絞り込み
	@PostMapping("/delivery-history-serch")
	public String deliveryHistoryConditions(@ModelAttribute DeliveryHistoryForm historyForm, Model model) {
		String destination = historyForm.getDestination();
		LocalDate startDate = historyForm.getStartDate();
		LocalDate endDate = historyForm.getEndDate();
		List<DeliveryHistoryDto> list = deliveryHistoryDAO.findByConditions(destination, startDate, endDate);
		model.addAttribute("historyForm", historyForm);	
		model.addAttribute("historyList", list);	
		return "delivery/history/delivery-history";
	}
	//納品履歴-表示
	@PostMapping("/delivery-history-view")
	public String ViewdeliveryHistory(@RequestParam("deliverySlipId")Integer id, Model model) {
		DeliverySlipIdWrapper idWrapper = new DeliverySlipIdWrapper();
		idWrapper.setId(id);
		model.addAttribute("idWrapper", idWrapper);	
		model.addAttribute("id", id);	
		return "delivery/history/view-delivery-history";
	}
	//納品履歴-表示-戻り
	@GetMapping("/view-delivery-history-ret")
	public String ViewdeliveryHistoryRet(@ModelAttribute DeliveryHistoryForm historyForm, Model model) {
		List<DeliveryHistoryDto> list = deliveryHistoryDAO.getAll();
		model.addAttribute("historyForm", historyForm);	
		model.addAttribute("historyList", list);	
		return "delivery/history/delivery-history";
	}
	//納品履歴-表示-画面内表示
	@GetMapping("/pdf/view-history")
	public ResponseEntity<byte[]> viewPdfHistory(@RequestParam("deliverySlipId")Integer id,
										  		 Model model) {
		byte[] document = deliverySlipService.viewDeliveryHistory(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.inline().filename("document.pdf").build());
		model.addAttribute("document", document);
		return new ResponseEntity<>(document,headers, HttpStatus.OK);
	}
	//納品履歴-表示-ダウンロード
	@GetMapping("/delivery-history-download")
	public void deliveryHistoryDownload(@ModelAttribute DeliverySlipIdWrapper idWrapper, Model model, HttpServletResponse response) {
		deliverySlipService.downloadDocument(idWrapper.getId(), response);	
		
	}
	
}
