package com.zs.action;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zs.core.common.Page;
import com.zs.model.Category;
import com.zs.model.Goods;
import com.zs.service.CateService;
import com.zs.service.GoodsService;
//管理品类控制层
@Controller
@RequestMapping("/admin/cate")
public class AdminCateAction extends BaseAction{
	
	@Autowired
	CateService cateService;
	@Autowired
	GoodsService goodsService;
	
	@RequestMapping("/getAdminCates")
	public String getAdminCates(Map<String,Page<Category>> m){
		Page<Category> cates=cateService.getAdminCates();
		m.put("cates", cates);
		return "/admin/cate/cateList";
	}
	
	@RequestMapping("/handleCate")
	public String handleCate(@RequestParam(required=false) String cateId,Map<String,Category> m){
		if(cateId!=null && !cateId.equals("")){
			Category cate=cateService.getCateById(cateId);
			m.put("cate", cate);
		}
		return "/admin/cate/handleCate";
	}
	
	@RequestMapping("/doHandleCate")
	public String doHandleCate(HttpServletRequest request,Category cate){
		System.out.println("cate:"+cate);
		String oldpicpath=cate.getCatePic();
		String catepicpath="";
	
		if(cate.getCatePicFile()!=null){
			if(cate.getCatePicFile().getContentType().equals("image/jpeg")||
					cate.getCatePicFile().getContentType().equals("image/png")){
				String oriFilename=cate.getCatePicFile().getOriginalFilename();
				String extFilename=oriFilename.substring(oriFilename.lastIndexOf("."), oriFilename.length());
				catepicpath="/images/cate/"+Calendar.getInstance().getTimeInMillis()+extFilename;
				cate.setCatePic(catepicpath);
				System.out.println("ext:"+extFilename);
			
				
				String path=request.getServletContext().getRealPath(catepicpath);
				File file=new File(path);
				try {
					if(cate.getCatePicFile()!=null){
						if(cate.getCateId()!=0){	
							System.out.println("----------------"+request.getServletContext().getRealPath(oldpicpath));
							File f=new File(request.getServletContext().getRealPath(oldpicpath));
							f.delete();
						}
						cate.getCatePicFile().transferTo(file);
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error(this.getClass().getName()+":",e);			
					this.addMessage("图片上传失败");
					this.addRedirURL("返回","@back");
					return EXECUTE_RESULT;
				}
			}
			else{
				this.addMessage("上传图片类型不正确，请上传jpg或png格式图片");
				this.addRedirURL("返回","@back");
				return EXECUTE_RESULT;
			}		
		}
		try{
			if(cate.getCateId()==0){
				cateService.addCate(cate);
				this.addMessage("添加商品分类成功");
				this.addRedirURL("返回","/admin/cate/getAdminCates");	
				return EXECUTE_RESULT;
			}
			else{
				cateService.updateCate(cate);
				this.addMessage("修改商品分类成功");
				this.addRedirURL("返回",getReferUrl());	
				return EXECUTE_RESULT;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("操作商品分类失败");
			this.addRedirURL("返回","@back");
			return EXECUTE_RESULT;
		}
		
	}
	
	@RequestMapping("/delCate")
	public String delCate(@RequestParam String cateId,HttpServletRequest request){
		try{
			String[] goodsIds=goodsService.getGoodsIdsByCateId(cateId);
			System.out.println("goodsIds:"+goodsIds.length);
			for(int i=0;i<goodsIds.length;i++){
				Goods goods=goodsService.getGoodsById(goodsIds[i]);
				String picpath=goods.getGoodsPic();
				File f=new File(request.getServletContext().getRealPath(picpath));
				f.delete();
				String[] picPathes=goodsService.getGoodsPicPathesByGoodsId(goodsIds[i]);
				System.out.println(picPathes);
				for(int j=0;j<picPathes.length;j++){
					File ff=new File(request.getServletContext().getRealPath(picPathes[j]));
					ff.delete();
				}
			}
			Category cate=cateService.getCateById(cateId);
			File f=new File(request.getServletContext().getRealPath(cate.getCatePic()));
			f.delete();
			cateService.deleteCate(cateId);
			this.addMessage("删除商品分类成功");
			this.addRedirURL("返回",getReferUrl());	
			return EXECUTE_RESULT;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("删除商品分类失败");
			this.addRedirURL("返回","@back");
			return EXECUTE_RESULT;
		}
	}
}
