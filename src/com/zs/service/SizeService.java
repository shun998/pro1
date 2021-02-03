package com.zs.service;

import java.util.List;

import com.zs.core.common.Page;
import com.zs.model.Size;
//尺寸业务层接口
public interface SizeService {

	Page<Size> getAllSizes();

	Size getSizeById(String sizeId);

	void addSize(Size size);

	void deleteSize(String sizeId);

	void updateSize(Size size);
	
	List<Size> getAllSizesWithoutPage();
}
