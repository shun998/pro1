package com.zs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zs.core.common.Page;
import com.zs.mapper.CateMapper;
import com.zs.mapper.GoodsMapper;
import com.zs.model.Category;
import com.zs.service.CateService;

@Service
@Transactional
public class CateServiceImpl implements CateService{
	@Autowired
	private CateMapper mapper;
	
	@Autowired
	private GoodsMapper goodsmapper;

	@Override
	public List<Category> getAllCates() {
		// TODO Auto-generated method stub
		List<Category> cates=mapper.getAllCates(null);
		return cates;
	}
	@Override
	public Page<Category> getAdminCates() {
		// TODO Auto-generated method stub
		Page<Category> page = new Page<Category>(10);
		List<Category> catesList=mapper.getAllCates(page);
		page.setList(catesList);  
		return page;
	}
	@Override
	public void updateCate(Category cate) {
		// TODO Auto-generated method stub
		mapper.updateCate(cate);
	}
	@Override
	public void addCate(Category cate) {
		// TODO Auto-generated method stub
		mapper.addCate(cate);
	}
	@Override
	public Category getCateById(String cateId) {
		// TODO Auto-generated method stub
		return mapper.getCateById(cateId);
	}
	@Override
	public void deleteCate(String cateId) {
		// TODO Auto-generated method stub
		List<Integer> goodsIds=goodsmapper.getGoodsIdByCateId(cateId);
		System.out.println(goodsIds);
		if(goodsIds!=null && goodsIds.size()>0){
			goodsmapper.deleteGoodsSizeByIds(goodsIds);
			goodsmapper.deleteGoodsColorByIds(goodsIds);
			goodsmapper.deleteGoodsPicByIds(goodsIds);
		}
		goodsmapper.deleteGoodsByCateId(cateId);
		mapper.deleteCate(cateId);
	}
}
