package com.zs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zs.mapper.AddressMapper;
import com.zs.model.Address;
import com.zs.service.AddressService;

@Service
@Transactional 
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	private AddressMapper mapper;

	@Override
	public List<Address> getAddressByUserId(int loginUserId) {
		// TODO Auto-generated method stub
		List<Address> addrs=mapper.getAddrByUserId(loginUserId);
		return addrs;
	}

	@Override
	public Address addAddress(Address address) {
		// TODO Auto-generated method stub
		mapper.addAddress(address);
		return address;
	}

	@Override
	public void updateAddress(Address address) {
		// TODO Auto-generated method stub
		mapper.updateAddress(address);
	}

	@Override
	public void deleteAddress(String addrId) {
		// TODO Auto-generated method stub
		mapper.deleteAddress(addrId);
	}

	@Override
	public void setDefaultAddress(String addrId,int loginUserId) {
		// TODO Auto-generated method stub
		mapper.unsetDefaultAddress(loginUserId);
		mapper.setDefaultAddress(addrId);
	}

	@Override
	public Address getAddressById(String addrId) {
		// TODO Auto-generated method stub
		Address addr=mapper.getAddressById(addrId);
		return addr;
	}
}
