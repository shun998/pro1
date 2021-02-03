package com.zs.mapper;

import java.util.List;

import com.zs.core.common.Page;
import com.zs.model.Category;
import com.zs.model.User;
//品类mapper接口
public interface CateMapper {

	List<Category> getAllCates(Page<Category> page);

	void addCate(Category cate);

	Category getCateById(String cateId);

	void deleteCate(String cateId);

	void updateCate(Category cate);

}
