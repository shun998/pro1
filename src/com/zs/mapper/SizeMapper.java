package com.zs.mapper;

import java.util.List;

import com.zs.core.common.Page;
import com.zs.model.Size;
//尺寸mapper接口
public interface SizeMapper {

	List<Size> getAllSizes(Page<Size> page);

	void updateSize(Size size);

	void deleteSize(String sizeId);

	void addSize(Size size);

	Size getSizeById(String sizeId);

	List<Size> getAllSizes();

}
