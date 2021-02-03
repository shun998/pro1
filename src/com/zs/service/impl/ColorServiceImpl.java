package com.zs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zs.core.common.Page;
import com.zs.mapper.ColorMapper;
import com.zs.model.Color;
import com.zs.service.ColorService;

@Service
@Transactional 
public class ColorServiceImpl implements ColorService{
	
	@Autowired
	private ColorMapper mapper;

	@Override
	public Page<Color> getAllColors() {
		// TODO Auto-generated method stub
		Page<Color> page = new Page<Color>(15);
		List<Color> colorList=mapper.getAllColors(page);
		page.setList(colorList);
		return page;
	}

	@Override
	public Color getColorById(String colorId) {
		// TODO Auto-generated method stub
		return mapper.getColorById(colorId);
	}

	@Override
	public void updateColor(Color color) {
		// TODO Auto-generated method stub
		mapper.updateColor(color);
	}

	@Override
	public void deleteColor(String colorId) {
		// TODO Auto-generated method stub
		mapper.deleteColor(colorId);
	}

	@Override
	public void addColor(Color color) {
		// TODO Auto-generated method stub
		mapper.addColor(color);
	}

	@Override
	public List<Color> getAllColorsWithoutPage() {
		// TODO Auto-generated method stub
		List<Color> colorList=mapper.getAllColors();
		return colorList;
	}
}
