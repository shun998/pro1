package com.zs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zs.core.common.Page;

import com.zs.mapper.SizeMapper;
import com.zs.model.Category;
import com.zs.model.Size;
import com.zs.service.SizeService;

@Service
@Transactional 
public class SizeServiceImpl implements SizeService{
	
	@Autowired
	private SizeMapper mapper;

	@Override
	public Page<Size> getAllSizes() {
		// TODO Auto-generated method stub
		Page<Size> page = new Page<Size>(15);
		List<Size> sizeList=mapper.getAllSizes(page);
		page.setList(sizeList);
		return page;
	}

	@Override
	public Size getSizeById(String sizeId) {
		// TODO Auto-generated method stub
		return mapper.getSizeById(sizeId);
	}

	@Override
	public void addSize(Size size) {
		// TODO Auto-generated method stub
		mapper.addSize(size);
	}

	@Override
	public void deleteSize(String sizeId) {
		// TODO Auto-generated method stub
		mapper.deleteSize(sizeId);
	}

	@Override
	public void updateSize(Size size) {
		// TODO Auto-generated method stub
		mapper.updateSize(size);
	}
	
	@Override
	public List<Size> getAllSizesWithoutPage() {
		// TODO Auto-generated method stub
		List<Size> sizeList=mapper.getAllSizes();		
		return sizeList;
	}
}
