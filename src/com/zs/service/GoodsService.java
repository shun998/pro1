package com.zs.service;


import java.util.List;
import java.util.Map;

import com.zs.core.common.Page;

import com.zs.model.Goods;
import com.zs.model.Stock;
//商品业务层接口
public interface GoodsService {

	Page<Goods> getGoodsByCate(String cateId,String sort);

	List<Goods> getNewGoods();

	List<Goods> getSalesGoods();

	Page<Goods> getGoodsBySearchKeyword(String keyword,String sort);

	Goods getGoodsDetailById(String goodsId);
	
	Goods getGoodsSizesById(String goodsId);
	
	Goods getGoodsColorsById(String goodsId);

	Page<Goods> getAdminSearchGoods(String cateId, String goodsName, String startPrice, String endPrice, String sort);

	Goods getGoodsById(String goodsId);

	void addGoods(Goods goods);

	void updateGoods(Goods goods);

	void deleteGoods(String goodsId);

	void addGoodsPics(String goodsId, String[] goodspicpaths);

	void deleteGoodsPics(String picId);

	void deleteGoodsByIds(String[] goodsIds);

	void updateGoodsSizesById(String goodsId, List<String> insertIdsIds,List<String> delIdsIds);

	void updateGoodsColorsById(String goodsId, List<String> insertIds, List<String> delIds);

	List<Stock> getGoodsStocksById(String goodsId);

	void updateGoodsStocksById(String[] goodsId, String[] sizeId, String[] colorId, String[] stockNum);

	String[] getGoodsSizeIds(String goodsId);

	String[] getGoodsColorIds(String goodsId);

	List<Map<String, Object>> getGoodsListByIds(String[] goodsId, String[] size, String[] color, String[] num);

	String getPicPath(String picId);

	String[] getGoodsPicPathesByGoodsId(String goodsId);

	String[] getGoodsIdsByCateId(String cateId);


}
