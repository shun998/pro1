package com.zs.mapper;

import java.util.List;
import java.util.Map;

import com.zs.core.common.Page;
import com.zs.model.Goods;
import com.zs.model.Stock;
//商品mapper接口
public interface GoodsMapper {

	List<Goods> getGoodsByCate(Page<Goods> page);

	List<Goods> getNewGoods();

	List<Goods> getSalesGoods();

	List<Goods> getGoodsByKeyword(Page<Goods> page);

	Goods getGoodsDetailById(String goodsId);

	Goods getGoodsSizesById(String goodsId);

	Goods getGoodsColorsById(String goodsId);
	
	List<Integer> getGoodsIdByCateId(String cateId);
	
	void deleteGoodsSizeByIds(List<Integer> goodsIds);

	void deleteGoodsColorByIds(List<Integer> goodsIds);

	void deleteGoodsPicByIds(List<Integer> goodsIds);

	void deleteGoodsByCateId(String cateId);

	List<Goods> getGoodsBySearchKeyword(Page<Goods> page);

	Goods getGoodsById(String goodsId);

	void addGoods(Goods goods);

	void updateGoods(Goods goods);

	void deleteGoodsSize(String goodsId);

	void deleteGoodsColor(String goodsId);

	void deleteGoodsPic(String goodsId);

	void deleteGoods(String goodsId);

	void addGoodsPics(Map<String,Object> m);

	void deleteGoodsPicById(String picId);

	void deleteGoodsSizes(String goodsId, String delId);

	void addGoodsSizes(String goodsId, String insertId);

	void deleteGoodsColors(String goodsId, String delId);

	void addGoodsColors(String goodsId, String insertId);

	List<Stock> getGoodsStocksByGoodsId(String goodsId);

	void updateGoodsStockById(String goodsId, String sizeId, String colorId, String stockNum);

	String[] getGoodsSizeIds(String goodsId);

	String[] getGoodsColorIds(String goodsId);

	void deleteGoodsStock(String goodsId);

	List<Goods> getGoodsListByIds(String[] goodsId);

	String getPicPath(String picId);

	String[] getGoodsPicPahtesByGoodsId(String goodsId);

	String[] getGoodsIdsByCateId(String cateId);

	

}
