package com.zs.service;


import java.util.List;

import com.zs.core.common.Page;
import com.zs.model.Category;
import com.zs.model.Goods;
//品类业务层接口
public interface CateService {
	List<Category> getAllCates();
	
	Page<Category> getAdminCates();

	void updateCate(Category cate);

	void addCate(Category cate);

	Category getCateById(String cateId);

	void deleteCate(String cateId);
	
}
