package com.zs.mapper;

import java.util.List;

import com.zs.core.common.Page;
import com.zs.model.Color;
//颜色mapper接口
public interface ColorMapper {
	List<Color> getAllColors();

	List<Color> getAllColors(Page<Color> page);

	Color getColorById(String colorId);

	void updateColor(Color color);

	void deleteColor(String colorId);

	void addColor(Color color);

}
