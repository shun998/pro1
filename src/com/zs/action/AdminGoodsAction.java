package com.zs.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zs.core.common.Page;
import com.zs.model.Category;
import com.zs.model.Color;
import com.zs.model.Goods;
import com.zs.model.Size;
import com.zs.model.Stock;
import com.zs.service.CateService;
import com.zs.service.ColorService;
import com.zs.service.GoodsService;
import com.zs.service.SizeService;
//管理商品控制层
@Controller
@RequestMapping("/admin/goods")
public class AdminGoodsAction extends BaseAction{
	
	@Autowired
	CateService cateService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	SizeService sizeService;
	@Autowired
	ColorService colorService;
	
	@RequestMapping("/getAdminGoods")
	public String getAdminGoods(@RequestParam(required=false) String cateId,@RequestParam(required=false) String goodsName,
			@RequestParam(required=false) String startPrice,@RequestParam(required=false) String endPrice,
			@RequestParam(required=false) String sort,Map<String,Object> m){
		List<Category> cates=cateService.getAllCates();
		Page<Goods> goods=goodsService.getAdminSearchGoods(cateId,goodsName,startPrice,endPrice,sort );
		m.put("cates", cates);
		m.put("goods", goods);
		System.out.println(goods);
		return "/admin/goods/goodsList";
	}
	
	@RequestMapping("/handleGoods")
	public String handleGoods(@RequestParam(required=false) String goodsId,Map<String,Object> m){
		if(goodsId!=null && !goodsId.equals("")){
			Goods goods=goodsService.getGoodsById(goodsId);
			m.put("goods", goods);
		}
		List<Category> cates=cateService.getAllCates();
		m.put("cates", cates);
		return "/admin/goods/handleGoods";
	}
	
	@RequestMapping("/doHandleGoods")
	public String doHandleGoods(HttpServletRequest request,Goods goods){
		System.out.println("goods:"+goods);
		String oldpicpath=goods.getGoodsPic();
		
		String goodspicpath="";
		if(goods.getGoodsPicFile()!=null){
			if(goods.getGoodsPicFile().getContentType().equals("image/jpeg")||
					goods.getGoodsPicFile().getContentType().equals("image/png")){
				
				String oriFilename=goods.getGoodsPicFile().getOriginalFilename();
				String extFilename=oriFilename.substring(oriFilename.indexOf("."), oriFilename.length());
				System.out.println("ext:"+extFilename);
				goodspicpath="/images/goods/"+Calendar.getInstance().getTimeInMillis()+extFilename;
				goods.setGoodsPic(goodspicpath);
					
				String path=request.getServletContext().getRealPath(goodspicpath);
				File file=new File(path);
				try {
					if(goods.getGoodsPicFile()!=null){
						if(goods.getGoodsId()!=0){							
							File f=new File(request.getServletContext().getRealPath(oldpicpath));
							f.delete();
						}
						goods.getGoodsPicFile().transferTo(file);					
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					log.error(this.getClass().getName()+":",e);			
					this.addMessage("图片上传失败");
					this.addRedirURL("返回","@back");
				}
			}
			else{
				this.addMessage("上传图片类型不正确，请上传jpg或png格式图片");
				this.addRedirURL("返回","@back");
				return EXECUTE_RESULT;
			}
		}
		
		try {
			if(goods.getGoodsId()==0){
				goodsService.addGoods(goods);
				this.addMessage("添加商品成功");
				System.out.println("referurl:"+getReferUrl());
				this.addRedirURL("返回","/admin/goods/getAdminGoods");	
			}
			else{
				goodsService.updateGoods(goods);
				this.addMessage("修改商品成功");
				this.addRedirURL("返回",getReferUrl());			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("操作商品失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/delGoods")
	public String delGoods(@RequestParam String goodsId,HttpServletRequest request){
		try{
			Goods goods=goodsService.getGoodsById(goodsId);
			String picpath=goods.getGoodsPic();
			File f=new File(request.getServletContext().getRealPath(picpath));
			f.delete();
			String[] picPathes=goodsService.getGoodsPicPathesByGoodsId(goodsId);
			System.out.println(picPathes);
			for(int i=0;i<picPathes.length;i++){
				File ff=new File(request.getServletContext().getRealPath(picPathes[i]));
				ff.delete();
			}
			goodsService.deleteGoods(goodsId);
			this.addMessage("删除商品成功");
			this.addRedirURL("返回",getReferUrl());	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("删除商品失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/getGoodsPics")
	public String getGoodsPics(@RequestParam(required=false) String cateId,@RequestParam(required=false) String goodsName,
			@RequestParam(required=false) String startPrice,@RequestParam(required=false) String endPrice,
			@RequestParam(required=false) String sort,Map<String,Object> m){
		List<Category> cates=cateService.getAllCates();
		m.put("cates", cates);
		Page<Goods> goods=goodsService.getAdminSearchGoods(cateId,goodsName,startPrice,endPrice,sort );
		m.put("goods", goods);
		return "/admin/goods/goodsPicsList";
	}
	
	@RequestMapping("/handleGoodsPics")
	public String handleGoodsPics(@RequestParam String goodsId,Map<String,Goods> m){
		Goods goods=goodsService.getGoodsDetailById(goodsId);
		m.put("goods", goods);
		return "/admin/goods/handleGoodsPics";
	}
	
	@RequestMapping("/addGoodsPics")
	public String addGoodsPics(HttpServletRequest request,@RequestParam String goodsId,@RequestParam MultipartFile[] goodsPicFile){
		System.out.println(goodsPicFile.length);
		for(int i=0;i<goodsPicFile.length;i++){
			System.out.println(goodsPicFile[i].getContentType());
			if(!goodsPicFile[i].isEmpty()&&
				!goodsPicFile[i].getContentType().equals("image/jpeg")&&
				!goodsPicFile[i].getContentType().equals("image/png")){
				this.addMessage("上传图片类型不正确，请上传jpg或png格式图片");
				this.addRedirURL("返回","@back");
				return EXECUTE_RESULT;
			}
		}
		try {
			String[] goodspicpaths=new String[5];
			if(null!=goodsPicFile&&goodsPicFile.length>0){  
				for(int i=0;i<goodsPicFile.length;i++){
					MultipartFile goodsPic=goodsPicFile[i];
					if(!goodsPic.isEmpty()){
						String oriFilename=goodsPic.getOriginalFilename();
						String extFilename=oriFilename.substring(oriFilename.indexOf("."), oriFilename.length());
						System.out.println("ext:"+extFilename);
						goodspicpaths[i]="/images/goods/pics/"+Calendar.getInstance().getTimeInMillis()+i+extFilename;
									
						String path=request.getServletContext().getRealPath(goodspicpaths[i]);
						File file=new File(path);		
						goodsPic.transferTo(file);
					}
				}
			}
			goodsService.addGoodsPics(goodsId,goodspicpaths);
			this.addMessage("添加图片成功");
			this.addRedirURL("返回","/admin/goods/handleGoodsPics?goodsId="+goodsId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("添加图片失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/delGoodsPics")
	public String delGoodsPics(@RequestParam String picId,@RequestParam String goodsId,HttpServletRequest request){
		try{
			String picpath=goodsService.getPicPath(picId);
			File f=new File(request.getServletContext().getRealPath(picpath));
			f.delete();
			goodsService.deleteGoodsPics(picId);
			this.addMessage("删除图片成功");
			this.addRedirURL("返回","/admin/goods/handleGoodsPics?goodsId="+goodsId);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("删除图片失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/delGoodsByIds")
	public String delGoodsByIds(@RequestParam String[] goodsIds,HttpServletRequest request){
		try{
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
			goodsService.deleteGoodsByIds(goodsIds);
			this.addMessage("批量删除商品成功");
			this.addRedirURL("返回",getReferUrl());
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("批量删除商品失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/getGoodsSizesAndColors")
	public String getGoodsSizesAndColors(@RequestParam(required=false) String cateId,@RequestParam(required=false) String goodsName,
			@RequestParam(required=false) String startPrice,@RequestParam(required=false) String endPrice,
			@RequestParam(required=false) String sort,Map<String,Object> m){
		List<Category> cates=cateService.getAllCates();
		m.put("cates", cates);
		Page<Goods> goods=goodsService.getAdminSearchGoods(cateId,goodsName,startPrice,endPrice,sort );
		m.put("goods", goods);
		return "/admin/goods/goodsSizesAndColorsList";
	}
	
	@RequestMapping("/handleGoodsSizes")
	public String handleGoodsSizes(@RequestParam String goodsId,Map<String,Object> m){
		Goods goodsSizes=goodsService.getGoodsSizesById(goodsId);
		List<Size> sizes=sizeService.getAllSizesWithoutPage();
		//System.out.println("sizes:"+sizes);
		//System.out.println("goodsSizes:"+goodsSizes.getSizes());
		sizes.removeAll(goodsSizes.getSizes());
		m.put("goodsSizes", goodsSizes);
		m.put("sizes", sizes);
		m.put("goodsId",goodsId);
		return "/admin/goods/handleGoodsSizes";
	}
	
	@RequestMapping("/handleGoodsColors")
	public String handleGoodsColors(@RequestParam String goodsId,Map<String,Object> m){
		
		Goods goodsColors=goodsService.getGoodsColorsById(goodsId);
		List<Color> colors=colorService.getAllColorsWithoutPage();
		//System.out.println("sizes:"+sizes);
		//System.out.println("goodsSizes:"+goodsSizes.getSizes());
		colors.removeAll(goodsColors.getColors());	
		m.put("goodsColors", goodsColors);
		m.put("colors",colors);
		m.put("goodsId",goodsId);
		return "/admin/goods/handleGoodsColors";
	}
	
	@RequestMapping("/doHandleGoodsSizes")
	public String doHandleGoodsSizes(@RequestParam String goodsId,@RequestParam String[] sizeIds){
		try{
			String[] nowIds=goodsService.getGoodsSizeIds(goodsId);
			List<String> retainIdsList=new ArrayList<String>(Arrays.asList(sizeIds));
			List<String> nowIdsList=new ArrayList<String>(Arrays.asList(nowIds));
			List<String> insertIdsList=new ArrayList<String>(Arrays.asList(sizeIds));
			System.out.println(insertIdsList);
			System.out.println(nowIdsList);
			retainIdsList.retainAll(nowIdsList);
			System.out.println(retainIdsList);
			
			insertIdsList.removeAll(retainIdsList);
			nowIdsList.removeAll(retainIdsList);
			System.out.println(insertIdsList);
			System.out.println(nowIdsList);
			
			goodsService.updateGoodsSizesById(goodsId,insertIdsList,nowIdsList);
			this.addMessage("更新商品尺寸成功");
			this.addRedirURL("返回","/admin/goods/handleGoodsSizes?goodsId="+goodsId);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("更新商品尺寸失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/doHandlGoodsColors")
	public String doHandlGoodsColors(@RequestParam String goodsId,@RequestParam String[] colorIds){
		try{
			String[] nowIds=goodsService.getGoodsColorIds(goodsId);
			List<String> retainIdsList=new ArrayList<String>(Arrays.asList(colorIds));
			List<String> nowIdsList=new ArrayList<String>(Arrays.asList(nowIds));
			List<String> insertIdsList=new ArrayList<String>(Arrays.asList(colorIds));
			System.out.println(insertIdsList);
			System.out.println(nowIdsList);
			retainIdsList.retainAll(nowIdsList);
			System.out.println(retainIdsList);
			
			insertIdsList.removeAll(retainIdsList);
			nowIdsList.removeAll(retainIdsList);
			System.out.println(insertIdsList);
			System.out.println(nowIdsList);
			
			goodsService.updateGoodsColorsById(goodsId,insertIdsList,nowIdsList);
			this.addMessage("更新商品颜色成功");
			this.addRedirURL("返回","/admin/goods/handleGoodsColors?goodsId="+goodsId);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("更新商品颜色失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/getGoodsStocks")
	public String getGoodsStocks(@RequestParam(required=false) String cateId,@RequestParam(required=false) String goodsName,
			@RequestParam(required=false) String startPrice,@RequestParam(required=false) String endPrice,
			@RequestParam(required=false) String sort,Map<String,Object> m){
		List<Category> cates=cateService.getAllCates();
		m.put("cates", cates);
		Page<Goods> goods=goodsService.getAdminSearchGoods(cateId,goodsName,startPrice,endPrice,sort );
		m.put("goods", goods);
		return "/admin/goods/goodsStocksList";
	}
	
	@RequestMapping("/handleGoodsStocks")
	public String handleGoodsStocks(@RequestParam String goodsId,Map<String,Object> m){
		List<Stock> goodsStocks=goodsService.getGoodsStocksById(goodsId);	
		m.put("goodsStocks", goodsStocks);
		System.out.println(goodsStocks);
		m.put("goodsId",goodsId);
		return "/admin/goods/handleGoodsStocks";
	}
	
	@RequestMapping("/doHandleGoodsStocks")
	public String doHandleGoodsStocks(String[] goodsId,String[] sizeId,String[] colorId,String[] stockNum){
		try{
			goodsService.updateGoodsStocksById(goodsId,sizeId,colorId,stockNum);
			this.addMessage("修改商品库存成功");
			this.addRedirURL("返回","/admin/goods/handleGoodsStocks?goodsId="+goodsId[0]);
		
		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("修改商品库存失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
}
