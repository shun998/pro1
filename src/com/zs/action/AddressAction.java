package com.zs.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zs.model.Address;
import com.zs.service.AddressService;
//地址控制层
@Controller
@RequestMapping("/address")
public class AddressAction extends BaseAction {
	
	@Autowired
	AddressService addressService;
	
	@RequestMapping("/getMyAddress")
	public String getMyAddress(Map<String,List<Address>> m){
		List<Address> addrs=addressService.getAddressByUserId(this.getLoginUserId());
		m.put("addrs", addrs);
		return "/address/addressList";
	}
	
	@RequestMapping("/handleAddress")
	public String handleAddress(Address address){
		try{System.out.println("======="+address.getAddrId());
			if(address.getAddrId()==0){
				address.setUserId(this.getLoginUserId());
				addressService.addAddress(address);			
			}
			else{
				addressService.updateAddress(address);
			}
			return "forward:/address/getMyAddress";
		}catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			this.addMessage("操作收货地址失败");
			this.addRedirURL("返回", "@back");
			return EXECUTE_RESULT;
		}
	}
	
	@RequestMapping("/delAddress")
	public String delAddress(@RequestParam String addrId){
		try{
			addressService.deleteAddress(addrId);
			return "forward:/address/getMyAddress";
		}catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			this.addMessage("删除收货地址失败");
			this.addRedirURL("返回", "@back");
			return EXECUTE_RESULT;
		}
	}
	
	@ResponseBody
	@RequestMapping("/setDefaultAddress")
	public Map<String,String> setDefaultAddress(@RequestParam String addrId){
		Map<String,String> m=new HashMap<String,String>();
		try{ 
			addressService.setDefaultAddress(addrId,this.getLoginUserId());
			m.put("setDefault","success");
		}catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			m.put("setDefault","failure");
		}
		return m;
	}
	
	@ResponseBody
	@RequestMapping("getAddressById")
	public Map<String,Address> getAddressById(@RequestParam String addrId){
		Map<String,Address> m=new HashMap<String,Address>();
		try{ 
			Address addr=addressService.getAddressById(addrId);
			m.put("addr",addr);
		}catch(Exception e){
			log.error(this.getClass().getName()+":",e);
		}
		System.out.println("-------"+m);
		return m;
	}

	////////////////////////////////////////Ajax////////////////////////////////////////////
	@RequestMapping("/getMyAddressAjax")
	public String getMyAddressAjax(Map<String, List<Address>> m) {
		List<Address> addrs = addressService.getAddressByUserId(this.getLoginUserId());
		m.put("addrs", addrs);
		return "/address/addressListAjax";
	}
	
	@RequestMapping("/handleAddressAjax")
	public String handleAddressAjax(Address address,Map<String,Address> m) {
		try {
			System.out.println("=======" + address.getAddrId());
			if (address.getAddrId() == 0) {
				address.setUserId(this.getLoginUserId());
				address=addressService.addAddress(address);
			} else {
				addressService.updateAddress(address);
			}
			m.put("a", address);
			return "/address/addressRowAjax";
		} catch (Exception e) {
			log.error(this.getClass().getName()+":",e);
			return null;
		}
	}

	@ResponseBody
	@RequestMapping("/delAddressAjax")
	public Map<String,String> delAddressAjax(@RequestParam String addrId) {
		Map<String,String> m=new HashMap<String,String>();
		try {
			addressService.deleteAddress(addrId);
			m.put("del", "success");
		} catch (Exception e) {
			log.error(this.getClass().getName()+":",e);
			m.put("del", "failure");		
		}
		return m;
	}

/*	@ResponseBody
	@RequestMapping("/setDefaultAddressAjax")
	public Map<String, String> setDefaultAddressAjax(@RequestParam String addrId) {
		Map<String, String> m = new HashMap<String, String>();
		try {
			this.getServMgr().getAddressService().setDefaultAddress(addrId);
			m.put("setDefault", "success");
		} catch (Exception e) {
			log.error(this.getClass().getName()+":",e);
			m.put("setDefault", "failure");
		}
		return m;
	}

	@ResponseBody
	@RequestMapping("getAddressByIdAjax")
	public Map<String, Address> getAddressByIdAjax(@RequestParam String addrId) {
		Map<String, Address> m = new HashMap<String, Address>();
		try {
			Address addr = this.getServMgr().getAddressService().getAddressById(addrId);
			m.put("addr", addr);
		} catch (Exception e) {
			log.error(this.getClass().getName()+":",e);
		}
		System.out.println("-------" + m);
		return m;
	}*/
}
