package com.zs.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zs.core.common.Page;
import com.zs.model.Color;
import com.zs.service.ColorService;


//颜色控制层
@Controller
@RequestMapping("/admin/color")
public class ColorAction extends BaseAction{
	
	@Autowired
	ColorService colorService;

	@RequestMapping("/getAdminColors")
	public String getAdminColors(Map<String,Page<Color>> m){
		Page<Color> colors=colorService.getAllColors();
		m.put("colors",colors);
		return "/admin/color/colorList";
	}
	

	@RequestMapping("/handleColor")
	public String handleColor(@RequestParam(required=false) String colorId,Map<String,Color> m){
		if(colorId!=null && !colorId.equals("")){
			Color color=colorService.getColorById(colorId);
			m.put("color", color);
		}
		return "/admin/color/handleColor";
	}
	
	@RequestMapping("/doHandleColor")
	public String doHandleColor(HttpServletRequest request,Color color){
		System.out.println("color:"+color);
		try{
			if(color.getColorId()==0){
				colorService.addColor(color);
				this.addMessage("添加商品颜色成功");
				this.addRedirURL("返回",getReferUrl());	
			}
			else{
				colorService.updateColor(color);
				this.addMessage("修改商品颜色成功");
				this.addRedirURL("返回",getReferUrl());			
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName(),e);			
			this.addMessage("操作商品颜色失败");  
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/delColor")
	public String delColor(@RequestParam String colorId){
		try{
			colorService.deleteColor(colorId);
			this.addMessage("删除商品颜色成功");
			this.addRedirURL("返回",getReferUrl());	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName(),e);
			this.addMessage("目前仍有这个颜色的商品，无法删除该颜色");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
}

