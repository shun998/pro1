package com.zs.service;

import java.util.List;

import com.zs.core.common.Page;
import com.zs.model.Color;
//颜色业务层接口
public interface ColorService {

	Page<Color> getAllColors();

	Color getColorById(String colorId);

	void updateColor(Color color);

	void deleteColor(String colorId);

	void addColor(Color color);

	List<Color> getAllColorsWithoutPage();
}
