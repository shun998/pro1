package com.zs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zs.core.common.Page;
import com.zs.mapper.GoodsMapper;
import com.zs.model.Goods;
import com.zs.model.Stock;
import com.zs.service.GoodsService;

@Service
@Transactional 
public class GoodsServiceImpl implements GoodsService{

	@Autowired
	private GoodsMapper mapper;
	
	@Override
	public Page<Goods> getGoodsByCate(String cateId,String sort) {
		// TODO Auto-generated method stub
		Page<Goods> page = new Page<Goods>(15);
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("cateId", cateId);
		m.put("sort", sort);
		page.setParams(m);
		List<Goods> goodsList=mapper.getGoodsByCate(page);
		page.setList(goodsList);
		return page;
	}

	@Override
	public List<Goods> getNewGoods() {
		// TODO Auto-generated method stub
		List<Goods> newGoods=mapper.getNewGoods();
		//System.out.println(newGoods);
		return newGoods;
	}

	@Override
	public List<Goods> getSalesGoods() {
		// TODO Auto-generated method stub
		List<Goods> salesGoods=mapper.getSalesGoods();
		System.out.println(salesGoods);
		return salesGoods;
	}

	@Override
	public Page<Goods> getGoodsBySearchKeyword(String keyword,String sort) {
		// TODO Auto-generated method stub
		System.out.println("-------"+sort);
		Page<Goods> page = new Page<Goods>(15);
		String[] keywords=keyword.split(" ");
		
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("keywords", keywords);
		m.put("sort", sort);
		page.setParams(m);
		List<Goods> goodsList=mapper.getGoodsByKeyword(page);
		System.out.println("-------"+goodsList);
		page.setList(goodsList);
		return page;
	}

	@Override
	public Goods getGoodsDetailById(String goodsId) {
		// TODO Auto-generated method stub
		Goods goodsDetail=mapper.getGoodsDetailById(goodsId);		
		System.out.println(goodsDetail);	
		return goodsDetail;
	}
	@Override
	public Goods getGoodsSizesById(String goodsId) {
		// TODO Auto-generated method stub		
		Goods goodsSizes=mapper.getGoodsSizesById(goodsId);
		System.out.println(goodsSizes);
		return goodsSizes;
	}
	@Override
	public Goods getGoodsColorsById(String goodsId) {
		// TODO Auto-generated method stub
		Goods goodsColors=mapper.getGoodsColorsById(goodsId);
		System.out.println(goodsColors);
		return goodsColors;
	}
	
	@Override
	public List<Map<String, Object>> getGoodsListByIds(String[] goodsId,
			String[] size, String[] color, String[] num) {
		// TODO Auto-generated method stub
		List<Goods> goodsInfos=mapper.getGoodsListByIds(goodsId);
		List<Map<String,Object>> goodsList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<goodsId.length;i++){
			Goods g=new Goods();
			g.setGoodsId(Integer.parseInt(goodsId[i]));
			int j=goodsInfos.indexOf(g);
			if(j!=-1){
				Goods goods=goodsInfos.get(j);
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("goodsId", goodsId[i]);
				m.put("goodsName", goods.getGoodsName());
				m.put("goodsPrice", goods.getGoodsPrice());
				m.put("goodsDiscount", goods.getGoodsDiscount());
				m.put("goodsPostalfee", goods.getGoodsPostalfee());
				m.put("goodsPic", goods.getGoodsPic());
				m.put("size", size[i]);
				m.put("color", color[i]);
				m.put("num", num[i]);
				goodsList.add(m);
			}
		}
		return goodsList;
	}

	@Override
	public Page<Goods> getAdminSearchGoods(String cateId, String goodsName, String startPrice, String endPrice,
			String sort) {
		// TODO Auto-generated method stub
		String[] goodsNames=null;
		if(goodsName!=null){
			goodsNames=goodsName.split(" ");
		}
		Page<Goods> page = new Page<Goods>(15);
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("cateId", cateId);
		m.put("goodsNames", goodsNames);
		m.put("startPrice", startPrice);
		m.put("endPrice", endPrice);
		m.put("sort", sort);
		System.out.println("=========="+m);
		page.setParams(m);
		List<Goods> goodsList=mapper.getGoodsBySearchKeyword(page);
		page.setList(goodsList);
		return page;
	}

	@Override
	public Goods getGoodsById(String goodsId) {
		// TODO Auto-generated method stub
		return mapper.getGoodsById(goodsId);
	}

	@Override
	public void addGoods(Goods goods) {
		// TODO Auto-generated method stub
		mapper.addGoods(goods);
	}

	@Override
	public void updateGoods(Goods goods) {
		// TODO Auto-generated method stub
		mapper.updateGoods(goods);
	}

	@Override
	public void deleteGoods(String goodsId) {
		// TODO Auto-generated method stub
		//mapper.delGoodsStock(goodsId);触发器删除
		mapper.deleteGoodsSize(goodsId);
		mapper.deleteGoodsColor(goodsId);
		mapper.deleteGoodsPic(goodsId);
		mapper.deleteGoods(goodsId);
	}

	@Override
	public void addGoodsPics(String goodsId, String[] goodspicpaths) {
		// TODO Auto-generated method stub
		Map<String,Object> m=new HashMap<String,Object>();
		for(int i=0;i<goodspicpaths.length;i++){
			if(goodspicpaths[i]!=null&&!goodspicpaths[i].equals("")){
				m.put("goodsId", Integer.parseInt(goodsId));
				m.put("goodsPic", goodspicpaths[i]);
				mapper.addGoodsPics(m);
			}
		}
		
	}

	@Override
	public void deleteGoodsPics(String picId) {
		// TODO Auto-generated method stub
		mapper.deleteGoodsPicById(picId);
	}

	@Override
	public void deleteGoodsByIds(String[] goodsIds) {
		// TODO Auto-generated method stub
		for(int i=0;i<goodsIds.length;i++){
			mapper.deleteGoodsSize(goodsIds[i]);
			mapper.deleteGoodsColor(goodsIds[i]);
			mapper.deleteGoodsPic(goodsIds[i]);
			mapper.deleteGoods(goodsIds[i]);
		}
	}

	@Override
	public void updateGoodsSizesById(String goodsId, List<String> insertIds,List<String> delIds) {
		// TODO Auto-generated method stub
		for(int i=0;i<delIds.size();i++){
			mapper.deleteGoodsSizes(goodsId,delIds.get(i));
		}
		for(int i=0;i<insertIds.size();i++){
			mapper.addGoodsSizes(goodsId,insertIds.get(i));
		}
	}
	@Override
	public void updateGoodsColorsById(String goodsId, List<String> insertIds, List<String> delIds) {
		// TODO Auto-generated method stub
		for(int i=0;i<delIds.size();i++){
			mapper.deleteGoodsColors(goodsId,delIds.get(i));
		}
		for(int i=0;i<insertIds.size();i++){
			mapper.addGoodsColors(goodsId,insertIds.get(i));
		}
	}
	

	@Override
	public List<Stock> getGoodsStocksById(String goodsId) {
		// TODO Auto-generated method stub
		return mapper.getGoodsStocksByGoodsId(goodsId);
	}

	@Override
	public void updateGoodsStocksById(String[] goodsId, String[] sizeId, String[] colorId, String[] stockNum) {
		// TODO Auto-generated method stub
		for(int i=0;i<goodsId.length;i++){
			mapper.updateGoodsStockById(goodsId[i],sizeId[i],colorId[i],stockNum[i]);
		}
	}

	@Override
	public String[] getGoodsSizeIds(String goodsId) {
		// TODO Auto-generated method stub
		return mapper.getGoodsSizeIds(goodsId);
	}

	@Override
	public String[] getGoodsColorIds(String goodsId) {
		// TODO Auto-generated method stub
		return mapper.getGoodsColorIds(goodsId);
	}

	@Override
	public String getPicPath(String picId) {
		// TODO Auto-generated method stub
		return mapper.getPicPath(picId);
	}

	@Override
	public String[] getGoodsPicPathesByGoodsId(String goodsId) {
		// TODO Auto-generated method stub
		return mapper.getGoodsPicPahtesByGoodsId(goodsId);
	}

	@Override
	public String[] getGoodsIdsByCateId(String cateId) {
		// TODO Auto-generated method stub
		return mapper.getGoodsIdsByCateId(cateId);
	}



	


}
