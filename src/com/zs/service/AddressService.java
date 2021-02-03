package com.zs.service;

import java.util.List;

import com.zs.model.Address;
//用户业务层接口
public interface AddressService {

	List<Address> getAddressByUserId(int loginUserId);

	Address addAddress(Address address);

	void updateAddress(Address address);

	void deleteAddress(String addrId);

	void setDefaultAddress(String addrId, int loginUserId);

	Address getAddressById(String addrId);

}
