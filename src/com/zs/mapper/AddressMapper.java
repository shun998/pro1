package com.zs.mapper;

import java.util.List;

import com.zs.model.Address;
//地址mapper映射接口
public interface AddressMapper {

	List<Address> getAddrByUserId(int loginUserId);

	void addAddress(Address address);

	void updateAddress(Address address);

	void deleteAddress(String addrId);

	void setDefaultAddress(String addrId);

	void unsetDefaultAddress(int loginUserId);

	Address getAddressById(String addrId);

}
