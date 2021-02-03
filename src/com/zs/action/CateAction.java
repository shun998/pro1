package com.zs.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zs.model.Category;
import com.zs.service.CateService;
//品类控制层
@Controller
@RequestMapping("/cate")
public class CateAction extends BaseAction{

	@Autowired
	private CateService cateService;
	
	@ResponseBody
	@RequestMapping("/getAllCates")
	public Map<String,List<Category>> getAllCates(){
		List<Category> cates=cateService.getAllCates();
		Map<String,List<Category>> m=new HashMap<String,List<Category>>();
		m.put("cates", cates);
		return m;
	}
	@RequestMapping("/getAllCatesList")
	public String getAllCates(Map<String,List<Category>> m){
		List<Category> cates=cateService.getAllCates();
		m.put("cates", cates);
		return "/cate/cateList";
	}

}
