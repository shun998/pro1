package com.zs.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zs.core.common.Page;
import com.zs.model.Size;
import com.zs.service.SizeService;
//尺寸控制层
@Controller
@RequestMapping("/admin/size")
public class SizeAction extends BaseAction{
	
	@Autowired
	SizeService sizeService;

	@RequestMapping("/getAdminSizes")
	public String getAdminSizes(Map<String,Page<Size>> m){
		Page<Size> sizes=sizeService.getAllSizes();
		m.put("sizes",sizes);
		return "/admin/size/sizeList";
	}
	

	@RequestMapping("/handleSize")
	public String handleSize(@RequestParam(required=false) String sizeId,Map<String,Size> m){
		if(sizeId!=null && !sizeId.equals("")){
			Size size=sizeService.getSizeById(sizeId);
			m.put("size", size);
		}
		return "/admin/size/handleSize";
	}
	
	@RequestMapping("/doHandleSize")
	public String doHandleSize(HttpServletRequest request,Size size){
		System.out.println("size:"+size);
		try{
			if(size.getSizeId()==0){
				sizeService.addSize(size);
				this.addMessage("添加商品尺寸成功");
				this.addRedirURL("返回",getReferUrl());
				return EXECUTE_RESULT;
			}
			else{
				sizeService.updateSize(size);
				this.addMessage("修改商品尺寸成功");
				System.out.println("getReferUrl:"+getReferUrl());
				this.addRedirURL("返回",getReferUrl());	
				System.out.println(this.getExecuteResult());
				return EXECUTE_RESULT;
			}
		}
		catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			this.addMessage("操作商品尺寸失败");
			this.addRedirURL("返回","@back");
			return EXECUTE_RESULT;
		}
	}
	
	@RequestMapping("/delSize")
	public String delSize(@RequestParam String sizeId){
		try{
			sizeService.deleteSize(sizeId);
			this.addMessage("删除商品尺寸成功");
			this.addRedirURL("返回",getReferUrl());
			return EXECUTE_RESULT;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("目前仍有这个尺寸的商品，无法删除该尺寸");
			this.addRedirURL("返回","@back");
			return EXECUTE_RESULT;
		}
	}
}

