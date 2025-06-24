package com.example.demo.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.DashboardDto;
import com.example.demo.dto.HistoryDto;
import com.example.demo.dto.ItemDto;
import com.example.demo.dto.SizeDto;
import com.example.demo.dto.StockListDto;
import com.example.demo.entity.CategoryFormEntity;
import com.example.demo.entity.ItemFormEntity;
import com.example.demo.entity.SizeFormEntity;
import com.example.demo.entity.StockFormEntity;
import com.example.demo.form.CategoryForm;
import com.example.demo.form.HistoryForm;
import com.example.demo.form.ItemForm;
import com.example.demo.form.SizeForm;
import com.example.demo.form.SizeFormWrapper;
import com.example.demo.form.StockForm;
import com.example.demo.service.DashboardService;
import com.example.demo.service.RegistCategoryService;
import com.example.demo.service.RegistItemService;
import com.example.demo.service.RegistSizeService;
import com.example.demo.service.StockHistoryService;
import com.example.demo.service.StockRecordService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StockSystemController {
	private final StockRecordService stockRecordService;
	private final RegistCategoryService registCategoryService;
	private final RegistItemService registItemService;
	private final RegistSizeService registSizeService;
	private final StockHistoryService stockHistoryService;
	private final DashboardService dashboardservice;
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	//在庫-TOP
	@GetMapping("/stock-top")
	public String stockTop(Model model) {
		DashboardDto dto = dashboardservice.getHistoryListMonthly();
		model.addAttribute("yearAndMonth","ダッシュボード　" + dto.getYear() + "年" + dto.getMonth() + "月");
		model.addAttribute("year", dto.getYear());
		model.addAttribute("month", dto.getMonth());
		model.addAttribute("dayList", dto.getDayList());
		model.addAttribute("shippingCountList", new ArrayList<>(dto.getShippingCountMap().values()));
		model.addAttribute("arrivalCountList", new ArrayList<>(dto.getArrivalCountMap().values()));
		model.addAttribute("shippingCategoryMap", dto.getShippingCategoryMap());
		model.addAttribute("arrivalCategoryMap", dto.getArrivalCategoryMap());
		return "stock/stock-top";
	}
	@GetMapping("/in-preparation")
	public String inPreparation() {
		return "in-preparation";
	}
	@PostMapping("/month-before")
	public String monthBefore(Model model,int year, int month) {
		DashboardDto dto = dashboardservice.getHistoryListMonthlyBefore(year, month);
		model.addAttribute("yearAndMonth","ダッシュボード　" + dto.getYear() + "年" + dto.getMonth() + "月");
		model.addAttribute("year", dto.getYear());
		model.addAttribute("month", dto.getMonth());
		model.addAttribute("dayList", dto.getDayList());
		model.addAttribute("shippingCountList", new ArrayList<>(dto.getShippingCountMap().values()));
		model.addAttribute("arrivalCountList", new ArrayList<>(dto.getArrivalCountMap().values()));
		model.addAttribute("shippingCategoryMap", dto.getShippingCategoryMap());
		model.addAttribute("arrivalCategoryMap", dto.getArrivalCategoryMap());
		return "stock/stock-top";
	}
	@PostMapping("/month-after")
	public String monthAfter(Model model,int year, int month) {
		DashboardDto dto = dashboardservice.getHistoryListMonthlyAfter(year, month);
		model.addAttribute("yearAndMonth","ダッシュボード　" + dto.getYear() + "年" + dto.getMonth() + "月");
		model.addAttribute("year", dto.getYear());
		model.addAttribute("month", dto.getMonth());
		model.addAttribute("dayList", dto.getDayList());
		model.addAttribute("shippingCountList", new ArrayList<>(dto.getShippingCountMap().values()));
		model.addAttribute("arrivalCountList", new ArrayList<>(dto.getArrivalCountMap().values()));
		model.addAttribute("shippingCategoryMap", dto.getShippingCategoryMap());
		model.addAttribute("arrivalCategoryMap", dto.getArrivalCategoryMap());
		return "stock/stock-top";
	}
	//---------------------------------------------------------------出荷-----------------------------------------------------------------------
	//在庫-出荷①
	@GetMapping("/stock-shipping")
	public String stockShipping(@ModelAttribute StockForm form, Model model) {
		List<CategoryDto> list = registCategoryService.getAll();
		model.addAttribute("categoryList",list);
		return "stock/shipping/stock-shipping";
	}
	@GetMapping("/stock-shipping/by-category")
	@ResponseBody
	public List<ItemDto> getItemByCategoryToStockShipping(@RequestParam("categoryId") int categoryId) {
		return registItemService.getSelectByCategoryId(categoryId);
	}
	@GetMapping("/stock-shipping/by-item")
	@ResponseBody
	public List<SizeDto> getSizeByItemToStockShipping(@RequestParam("itemId") int itemId) {
		return registSizeService.getSelectByItemId(itemId);
	}
	@GetMapping("/stock-shipping/by-size")
	@ResponseBody
	public int getAmountBySizeToStockShipping(@RequestParam("itemId") int itemId, @RequestParam("sizeId") int sizeId) {
		return stockRecordService.serchCurrentAmount(itemId,sizeId);
	}
	//在庫-出荷②
	@PostMapping("/stock-shipping")
	public String stockShipping(@Validated @ModelAttribute("stockForm") StockForm form, BindingResult result, Model model) throws SQLException {
		if (result.hasErrors()) {
			model.addAttribute("stockForm", form);
			List<CategoryDto> categorylist = registCategoryService.getAll();
			model.addAttribute("categoryList",categorylist);
			List<ItemDto> itemlist = registItemService.getAll();
			model.addAttribute("itemList",itemlist);
			List<SizeDto> sizelist = registSizeService.getAll();
			model.addAttribute("sizeList",sizelist);
			
			return "stock/shipping/stock-shipping";			
		} else {
			int categoryId = form.getCategoryId();
			List<CategoryDto> categorylist = registCategoryService.getSelect(categoryId);
			model.addAttribute("categoryList",categorylist);
			int itemId = form.getItemId();
			List<ItemDto> itemlist = registItemService.getSelectByItemId(itemId);
			model.addAttribute("itemList",itemlist);
			int sizeId = form.getSizeId();
			List<SizeDto> sizelist = registSizeService.getSelectBySizeId(sizeId);
			model.addAttribute("sizeList",sizelist);
			return "stock/shipping/confirm-stock-shipping";		
		}
	}
	//在庫-出荷②-戻り
	@PostMapping("/confirm-stock-shipping-ret")
	public String ConfirmstockShippingRet(@ModelAttribute StockForm form, Model model) {
		List<CategoryDto> list = registCategoryService.getAll();
		model.addAttribute("categoryList",list);
		return "stock/shipping/stock-shipping";
	}
	//在庫-出荷③-登録
	@PostMapping("/confirm-stock-shipping")
	public String ConfirmstockShipping(@ModelAttribute StockForm form ,Model model) throws SQLException {
		int categoryId = form.getCategoryId();
		List<CategoryDto> categorylist = registCategoryService.getSelect(categoryId);
		model.addAttribute("categoryList",categorylist);
		int itemId = form.getItemId();
		List<ItemDto> itemlist = registItemService.getSelectByItemId(itemId);
		model.addAttribute("itemList",itemlist);
		int sizeId = form.getSizeId();
		List<SizeDto> sizelist = registSizeService.getSelectBySizeId(sizeId);
		model.addAttribute("categoryList",sizelist);
		
		StockFormEntity f = new StockFormEntity();
		f.setCategoryId(form.getCategoryId());
		f.setItemId(form.getItemId());
		f.setSizeId(form.getSizeId());
		f.setAmount(form.getAmount());
		f.setRegisterDate(form.getRegisterDate());
		f.setPerson(form.getPerson());
		f.setComment(form.getComment());
		stockRecordService.shipping(f);
		return "stock/shipping/complete-stock-shipping";
	}
	//---------------------------------------------------------------入荷-----------------------------------------------------------------------
	//在庫-入荷①
	@GetMapping("/stock-arrival")
	public String stockArrival(@ModelAttribute StockForm form, Model model) {
		List<CategoryDto> list = registCategoryService.getAll();
		model.addAttribute("categoryList",list);
		return "stock/arrival/stock-arrival";
	}
	@GetMapping("/stock-arrival/by-category")
	@ResponseBody
	public List<ItemDto> getItemByCategoryToStockArrival(@RequestParam("categoryId") int categoryId) {
		return registItemService.getSelectByCategoryId(categoryId);
	}
	@GetMapping("/stock-arrival/by-item")
	@ResponseBody
	public List<SizeDto> getSizeByItemToStockArrival(@RequestParam("itemId") int itemId) {
		return registSizeService.getSelectByItemId(itemId);
	}
	//在庫-入荷②
	@PostMapping("/stock-arrival")
	public String stockArrival(@Validated @ModelAttribute("stockForm") StockForm form, BindingResult result, Model model) throws SQLException {
		if (result.hasErrors()) {
			model.addAttribute("stockForm", form);
			List<CategoryDto> categorylist = registCategoryService.getAll();
			model.addAttribute("categoryList",categorylist);
			List<ItemDto> itemlist = registItemService.getAll();
			model.addAttribute("itemList",itemlist);
			List<SizeDto> sizelist = registSizeService.getAll();
			model.addAttribute("sizeList",sizelist);
			return "stock/arrival/stock-arrival";			
		} else {
			int categoryId = form.getCategoryId();
			List<CategoryDto> categorylist = registCategoryService.getSelect(categoryId);
			model.addAttribute("categoryList",categorylist);
			int itemId = form.getItemId();
			List<ItemDto> itemlist = registItemService.getSelectByItemId(itemId);
			model.addAttribute("itemList",itemlist);
			int sizeId = form.getSizeId();
			List<SizeDto> sizelist = registSizeService.getSelectBySizeId(sizeId);
			model.addAttribute("sizeList",sizelist);
			return "stock/arrival/confirm-stock-arrival";		
		}
	}
	//在庫-入荷②-戻り
	@PostMapping("/confirm-stock-arrival-ret")
	public String ConfirmstockArrivalRet(@ModelAttribute StockForm form, Model model) {
		List<CategoryDto> list = registCategoryService.getAll();
		model.addAttribute("categoryList",list);
		return "stock/arrival/stock-arrival";
	}
	//在庫-入荷③-登録
	@PostMapping("/confirm-stock-arrival")
	public String ConfirmstockArrival(@ModelAttribute StockForm form ,Model model) throws SQLException {
		int categoryId = form.getCategoryId();
		List<CategoryDto> categorylist = registCategoryService.getSelect(categoryId);
		model.addAttribute("categoryList",categorylist);
		int itemId = form.getItemId();
		List<ItemDto> itemlist = registItemService.getSelectByItemId(itemId);
		model.addAttribute("itemList",itemlist);
		int sizeId = form.getSizeId();
		List<SizeDto> sizelist = registSizeService.getSelectBySizeId(sizeId);
		model.addAttribute("categoryList",sizelist);
		
		StockFormEntity f = new StockFormEntity();
		f.setCategoryId(form.getCategoryId());
		f.setItemId(form.getItemId());
		f.setSizeId(form.getSizeId());
		f.setAmount(form.getAmount());
		f.setRegisterDate(form.getRegisterDate());
		f.setPerson(form.getPerson());
		f.setComment(form.getComment());
		stockRecordService.arrival(f);
		return "stock/arrival/complete-stock-arrival";
	}
	//---------------------------------------------------------------カテゴリー管理-----------------------------------------------------------------------
	//カテゴリー登録①
	@GetMapping("/regist-category")
	public String RegistCategory(@ModelAttribute CategoryForm form, Model model) {
		List<CategoryDto> list = registCategoryService.getAll();
		model.addAttribute("categoryList",list);
		return "stock/category/regist-category";
	}
	//カテゴリー登録②-戻り
	@PostMapping("/confirm-regist-category-ret")
	public String ConfirmRegistCategoryret(@ModelAttribute CategoryForm form, Model model) {
		List<CategoryDto> list = registCategoryService.getAll();
		model.addAttribute("categoryList",list);
		return "stock/category/regist-category";
	}
	//カテゴリー登録②
	@PostMapping("/regist-category")
	public String RegistCategory(@Validated @ModelAttribute CategoryForm form,BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<CategoryDto> list = registCategoryService.getAll();
			model.addAttribute("categoryList",list);
			return "stock/category/regist-category";
		} else {
			return "stock/category/confirm-regist-category";
		}
	}
	//カテゴリー登録③
	@PostMapping("/confirm-regist-category")
	public String ConfirmRegistCategory(@ModelAttribute CategoryForm form, Model model) {
		CategoryFormEntity f = new CategoryFormEntity();
		f.setCategoryName(form.getCategoryName());
		registCategoryService.regist(f);
		return "stock/category/complete-regist-category";
	}
	//カテゴリー削除①
	@PostMapping("/category-delete")
	public String CategoruDelete(@ModelAttribute CategoryForm form, Model model) {
		int id = form.getCategoryId();
		List<CategoryDto> list = registCategoryService.getSelect(id);
		model.addAttribute("categoryList",list);
		return "stock/category/confirm-category-delete";
	}
	//カテゴリー削除②
	@PostMapping("/confirm-category-delete")
	public String ConfirmCategoryDelete(@ModelAttribute CategoryForm form, Model model) {
		CategoryFormEntity f = new CategoryFormEntity();
		f.setCategoryId(form.getCategoryId());
		registCategoryService.remove(f);
		return "stock/category/complete-category-delete";
	}
	//カテゴリー編集①
	@PostMapping("/category-edit")
	public String CategoryEdit(@ModelAttribute CategoryForm form, Model model) {
		int id = form.getCategoryId();
		List<CategoryDto> list = registCategoryService.getSelect(id);
		model.addAttribute("categoryList",list);
		return "stock/category/category-edit-input";
	}
	//カテゴリー編集②
	@PostMapping("/category-edit-input")
	public String CategoryEditInput(@ModelAttribute CategoryForm form, Model model) {
		int id = form.getCategoryId();
		List<CategoryDto> list = registCategoryService.getSelect(id);
		model.addAttribute("categoryList",list);
		return "stock/category/confirm-category-edit";
	}
	//カテゴリー編集②-戻り
	@PostMapping("/confirm-category-edit-ret")
	public String CondirmCategoryEditRet(@ModelAttribute CategoryForm form, Model model) {
		int id = form.getCategoryId();
		List<CategoryDto> list = registCategoryService.getSelect(id);
		model.addAttribute("categoryList",list);
		return "stock/category/category-edit-input";
	}
	//カテゴリー編集③-完了
	@PostMapping("/confirm-category-edit")
	public String ComleteCategoryEdit(@ModelAttribute CategoryForm form, Model model) {
		CategoryFormEntity f = new CategoryFormEntity();
		f.setCategoryId(form.getCategoryId());
		f.setCategoryName(form.getCategoryName());
		registCategoryService.changeName(f);
		
		return "stock/category/complete-category-edit";
	}
	//---------------------------------------------------------------商品管理-----------------------------------------------------------------------
	//商品登録①
	@GetMapping("/regist-item")
	public String RegistItem(@ModelAttribute ItemForm form, ItemForm selectForm, 
							 @RequestParam(name="selectedCategoryId", required=false,defaultValue="0")int selectedCategory,Model model) {
		List<CategoryDto> categoryList = registCategoryService.getAll();
		model.addAttribute("categoryList",categoryList);
		int id = selectedCategory;
		List<ItemDto> selecteditemlist = registItemService.getSelectByCategoryId(id);
		model.addAttribute("selectedItemList",selecteditemlist);
//		model.addAttribute("selectedCategory", selectedCategory);
		return "stock/item/regist-item";
	}
	@GetMapping("/regist-item/by-category")
	@ResponseBody
	public List<ItemDto> getItemByCategoryToRegistItem(@RequestParam("categoryId") int categoryId) {
		return registItemService.getSelectByCategoryId(categoryId);
	}
	//商品登録②
	@PostMapping("/regist-item")
	public String RegistItem(@Validated @ModelAttribute ItemForm itemForm, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<CategoryDto> categoryList = registCategoryService.getAll();
			model.addAttribute("categoryList",categoryList);
			return "stock/item/regist-item";			
		} else {
			//値渡し
			int categoryId = itemForm.getCategoryId();
			List<CategoryDto> categoryList = registCategoryService.getSelect(categoryId);
			model.addAttribute("categoryList",categoryList);
			String itemName = itemForm.getItemName();
			model.addAttribute("itemName", itemName);
			int price = itemForm.getPrice();
			model.addAttribute("price", price);
			return "stock/item/regist-item-input-size";
		}
	}	
	//商品登録②-戻り
	@PostMapping("/regist-item-input-size-ret")
	public String RegistItemInputSizeRet(@ModelAttribute ItemForm form, ItemForm itemForm, 
										 @RequestParam(name="selectedCategoryId", required=false,defaultValue="0")int selectedCategory, Model model) {
		List<CategoryDto> categoryList = registCategoryService.getAll();
		model.addAttribute("categoryList",categoryList);
		String itemName = itemForm.getItemName();
		model.addAttribute("itemName", itemName);
		int price = itemForm.getPrice();
		model.addAttribute("price", price);
		model.addAttribute("selectedCategory", selectedCategory);
		return "stock/item/regist-item";
	}
	//商品登録③
	@PostMapping("/regist-item-input-size")
	public String RegistItemInputSize(@Validated @RequestParam(value = "sizes", required = false) List<String> sizes, 
									  @ModelAttribute ItemForm itemForm, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<CategoryDto> categoryList = registCategoryService.getAll();
			model.addAttribute("categoryList",categoryList);
			return "stock/item/regist-item-input-size";			
		} else {
			if (sizes == null || sizes.isEmpty()) {
				int categoryId = itemForm.getCategoryId();
				List<CategoryDto> categoryList = registCategoryService.getSelect(categoryId);
				model.addAttribute("categoryList",categoryList);
				String itemName = itemForm.getItemName();
				model.addAttribute("itemName", itemName);
				int price = itemForm.getPrice();
				model.addAttribute("price", price);
				return "stock/item/confirm-regist-item";	
			} else {
				int categoryId = itemForm.getCategoryId();
				List<CategoryDto> categoryList = registCategoryService.getSelect(categoryId);
				model.addAttribute("categoryList",categoryList);
				String itemName = itemForm.getItemName();
				model.addAttribute("itemName", itemName);
				int price = itemForm.getPrice();
				model.addAttribute("price", price);
				model.addAttribute("sizeList", sizes);
				return "stock/item/confirm-regist-item";		
			}
		}
	}
	//商品登録③-完了
	@PostMapping("/confirm-regist-item")
	public String ConfirmRegistItem(@ModelAttribute SizeFormWrapper sizeFormWrapper, ItemForm itemForm, 
									@RequestParam(value = "sizes", required = false) List<String> sizes, Model model) {
		//item登録処理
		ItemFormEntity fItem = new ItemFormEntity();
		fItem.setItemName(itemForm.getItemName());
		fItem.setPrice(itemForm.getPrice());
		fItem.setCategoryId(itemForm.getCategoryId());
		registItemService.regist(fItem);	
		//size登録処理
		List<ItemDto> selectedItemList = registItemService.getSelectByLatestId();
		int latestItemId = selectedItemList.get(0).getItemId();
		if (sizes != null) {
			for (String size : sizes) {
				SizeFormEntity fSize = new SizeFormEntity();
				fSize.setItemId(latestItemId);
				fSize.setSizeName(size);
				registSizeService.regist(fSize);
			}			
		}
		//stock登録処理
		if (sizes == null) {
			stockRecordService.registToStockByItem(fItem, latestItemId);			
		} else {
			stockRecordService.registToStockBySize(fItem, sizes, latestItemId);			
		}	
		return "stock/item/complete-regist-item";
	}
	//商品登録③-戻り
	@PostMapping("/confirm-regist-item-ret")
	public String ConfirmRegistItemret(@ModelAttribute ItemForm form, ItemForm selectForm, 
										@RequestParam(value = "sizes", required = false) List<String> sizes, Model model) {
		int categoryId = form.getCategoryId();
		List<CategoryDto> categorylist = registCategoryService.getSelect(categoryId);
		model.addAttribute("categoryList",categorylist);
		model.addAttribute("sizeList", sizes);		
		return "stock/item/regist-item-input-size";
	}

	//商品削除①
	@PostMapping("/item-delete")
	public String ItemDelete(@ModelAttribute ItemForm form, Model model) {
		int id = form.getItemId();
		List<ItemDto> list = registItemService.getSelectByItemId(id);
		model.addAttribute("itemList",list);
		return "stock/item/confirm-item-delete";
	}
	//商品削除②
	@PostMapping("/confirm-item-delete")
	public String ConfirmItemDelete(@ModelAttribute ItemForm form, Model model) {
		ItemFormEntity f = new ItemFormEntity();
		f.setItemId(form.getItemId());
		registItemService.remove(f);
		return "stock/item/complete-item-delete";
	}
	//商品編集①
	@PostMapping("/item-edit")
	public String ItemEdit(@ModelAttribute ItemForm itemForm, SizeForm sizeForm,  Model model) {
		int itemId = itemForm.getItemId();
		model.addAttribute("itemForm", itemForm);
		List<SizeDto> sizeDtos = registSizeService.getSelectByItemId(itemId);
		List<SizeForm> sizeForms = new ArrayList<>();
		for (SizeDto dto : sizeDtos) {
			SizeForm form = new SizeForm();
			form.setSizeId(dto.getSizeId());
			form.setSizeName(dto.getSizeName());
			form.setItemId(dto.getItemId());
			sizeForms.add(form);
		}
		model.addAttribute("SizeForms", sizeForms);
		SizeFormWrapper sizeFormWrapper = new SizeFormWrapper();
		sizeFormWrapper.setSizeFormList(sizeForms);
		model.addAttribute("sizeFormWrapper", sizeFormWrapper);
		return "stock/item/item-edit-input";
	}
		
	//商品編集②
	@PostMapping("/item-edit-input")
	public String ItemEditInput(@Validated @ModelAttribute("itemForm") ItemForm itemForm, BindingResult itemResult, 
								@Validated @ModelAttribute("sizeFormWrapper") SizeFormWrapper sizeFormWrapper, BindingResult sizeResult, 
								Model model) {
		int itemId = itemForm.getItemId();
		if 
		(itemResult.hasErrors() || sizeResult.hasErrors()) {
			List<ItemDto> itemlist = registItemService.getSelectByItemId(itemId);
			model.addAttribute("itemList", itemlist);
			List<SizeDto> sizelist = registSizeService.getSelectByItemId(itemId);
			model.addAttribute("sizeList", sizelist);
			return "stock/item/item-edit-input";
		} 
		else
		{
			List<ItemDto> itemlist = registItemService.getSelectByItemId(itemId);
			model.addAttribute("itemList", itemlist);
			model.addAttribute("sizeList", sizeFormWrapper.getSizeFormList());
			return "stock/item/confirm-edit-item";
		}
	}
	//商品編集②-戻り
	@PostMapping("/confirm-item-edit-ret")
	public String ConfirmItemEditRet(@ModelAttribute ItemForm itemForm, SizeForm sizeForm, SizeFormWrapper sizeFormWrapper, Model model) {
		int itemId = itemForm.getItemId();
		model.addAttribute("itemForm", itemForm);
		
		List<SizeForm> incomingSizeForms = sizeFormWrapper.getSizeFormList();
		if (incomingSizeForms == null) {
			incomingSizeForms = new ArrayList<>();
			sizeFormWrapper.setSizeFormList(incomingSizeForms);
		}
		
		Map<Integer, String> updateSizeName = new HashMap<>();
		for (SizeForm form : sizeFormWrapper.getSizeFormList() ) {
			updateSizeName.put(form.getSizeId(), form.getSizeName());
		}
		List<SizeDto> sizeDtos = registSizeService.getSelectByItemId(itemId);
		List<SizeForm> sizeForms = new ArrayList<>();
		for (SizeDto dto : sizeDtos) {
			SizeForm form = new SizeForm();
			form.setItemId(dto.getItemId());
			form.setSizeId(dto.getSizeId());
			form.setSizeName(updateSizeName.get(dto.getSizeId()));
			sizeForms.add(form);
		}
		sizeFormWrapper.setSizeFormList(sizeForms);
		model.addAttribute("sizeFormWrapper", sizeFormWrapper);
		model.addAttribute("sizeList", sizeFormWrapper.getSizeFormList());
		return "stock/item/item-edit-input";
	}
	//商品編集③-完了
	@PostMapping("/confirm-edit-item")
	public String ConfirmEditItem(@ModelAttribute ItemForm itemForm, SizeFormWrapper sizeFormWrapper, Model model) {
		List<SizeForm> sizeFormList = sizeFormWrapper.getSizeFormList();
		ItemFormEntity itemF = new ItemFormEntity();
		itemF.setItemName(itemForm.getItemName());
		itemF.setItemId(itemForm.getItemId());
		itemF.setCategoryId(itemForm.getCategoryId());
		itemF.setPrice(itemForm.getPrice());
		registItemService.change(itemF);
		if (sizeFormList != null) {
			for (SizeForm form : sizeFormList) {
				SizeFormEntity sizeF = new SizeFormEntity();
				sizeF.setSizeName(form.getSizeName());
				sizeF.setSizeId(form.getSizeId());
				sizeF.setItemId(itemForm.getItemId());
				registSizeService.changeName(sizeF);		
			}					
		}
		return "stock/item/complete-edit-item";
	}
	//---------------------------------------------------------------出納履歴-----------------------------------------------------------------------
	//出納履歴①
	@GetMapping("/stock-history")
	public String stockHistory(@ModelAttribute HistoryForm historyForm, Model model) {
		List<CategoryDto> list = registCategoryService.getAll();
		historyForm.setExecute("all");
		model.addAttribute("categoryList",list);
		model.addAttribute("historyForm", historyForm);
		return "stock/history/stock-history";
	}
	@GetMapping("/stock-history/by-category")
	@ResponseBody
	public List<ItemDto> getItemByCategoryToStockHistory(@RequestParam("categoryId") int categoryId) {
		return registItemService.getSelectByCategoryId(categoryId);
	}	
	@GetMapping("/stock-history/by-item")
	@ResponseBody
	public List<SizeDto> getSizeByItemToStockHistory(@RequestParam("itemId") int itemId) {
		return registSizeService.getSelectByItemId(itemId);
	}
	@GetMapping("/stock-history-get-all")
	public String stockHistoryGetAll(@ModelAttribute HistoryForm historyForm, Model model) {
		List<HistoryDto> historyList = stockHistoryService.getAllHistory();
		List<CategoryDto> categoryList = registCategoryService.getAll();
		model.addAttribute("historyForm", historyForm);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("historyList", historyList);
		return "stock/history/stock-history";
	}
	//出納履歴①-絞り込み
	@PostMapping("/stock-history-conditions")
	public String stockHistoryGetSerch(
									   @RequestParam(required = false) Integer categoryId,
									   @RequestParam(required = false) Integer itemId,
									   @RequestParam(required = false) Integer sizeId,
									   @RequestParam(required = false) String execute,
									   @ModelAttribute HistoryForm historyForm, 
									   Model model) {
		LocalDate startDate = historyForm.getStartDate();
		LocalDate endDate = historyForm.getEndDate();
		List<HistoryDto> historyList = stockHistoryService.getSearch(
				categoryId, itemId, sizeId, startDate, endDate, execute
				);
		List<CategoryDto> categoryList = registCategoryService.getAll();
		model.addAttribute("historyForm", historyForm);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("historyList", historyList);
		return "stock/history/stock-history";
	}
//---------------------------------------------------------------在庫一覧-----------------------------------------------------------------------
	//在庫一覧①
	@GetMapping("/stock-list")
	public String stockList(@ModelAttribute CategoryForm form, Model model) {
		List<CategoryDto> categoryList = registCategoryService.getAll();
		model.addAttribute("categoryList", categoryList);
		return "stock/stock-list/stock-list";
	}
	//在庫一覧①-絞り込み
	@PostMapping("/stock-list")
	public String stockListByCategory(@ModelAttribute StockForm stockform, CategoryForm categoryForm, Model model) {
		int categoryId = categoryForm.getCategoryId();
		List<StockListDto> stockList = stockRecordService.searchStockByCategoryId(categoryId);
		model.addAttribute("StockList", stockList);
		List<CategoryDto> categoryList = registCategoryService.getAll();
		model.addAttribute("categoryList", categoryList);
		return "stock/stock-list/stock-list";
	}


}
