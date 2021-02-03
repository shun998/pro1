package com.zs.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.zs.core.Constants;
import com.zs.core.common.Page;
import com.zs.model.Address;
import com.zs.model.Order;
import com.zs.model.OrderDetail;
import com.zs.service.AddressService;
import com.zs.service.GoodsService;
import com.zs.service.OrderService;
//订单控制层
@Controller
@SessionAttributes(types={String[].class})
@RequestMapping("/order")
public class OrderAction extends BaseAction{
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	AddressService addressService;
	
/*	@RequestMapping("/buyGoods")
	public String buyGoods(@RequestParam(required=false) String[] goodsId,@RequestParam(required=false) String[] goodsName,@RequestParam(required=false) String[] goodsPrice,
			@RequestParam(required=false) String[] goodsDiscount,@RequestParam(required=false) String[] num,@RequestParam(required=false) String[] size,
			@RequestParam(required=false) String[] color,
			@RequestParam(required=false) String[] pic,@RequestParam(required=false) String[] goodsPostalfee,
			Map<String,Object> m){
		if(goodsId!=null && goodsName!=null && goodsPrice!=null && goodsDiscount!=null && num!=null
				&& size!=null && color!=null && pic!=null && goodsPostalfee!=null ){
			for(int i=0;i<goodsId.length;i++){
				System.out.println(goodsId[i]);
				System.out.println(goodsName[i]);
				System.out.println(goodsPrice[i]);
				System.out.println(goodsDiscount[i]);
				System.out.println(num[i]);
				System.out.println(size[i]);
				System.out.println(color[i]);
				System.out.println(pic[i]);
			}

			
		}	
		m.put("goodsId", goodsId);
		m.put("goodsName", goodsName);
		m.put("goodsDiscount", goodsDiscount);
		m.put("size", size);
		m.put("color", color);
		m.put("num", num);
		//m.put("goodsPostalfee", goodsPostalfee);
		m.put("pic",pic);
		List<Address> addrs=this.getServMgr().getAddressService().getAddressByUserId(this.getLoginUserId());
		m.put("addrs", addrs);
		return "/order/buyGoods";
	}*/
	@RequestMapping("/buyGoods")
	public String buyGoods(@RequestParam(required=false) String[] goodsId,
			@RequestParam(required=false) String[] num,
			@RequestParam(required=false) String[] size,
			@RequestParam(required=false) String[] color,
			Map<String,Object> m){
		if(goodsId!=null && goodsId.length>0){
			List<Map<String,Object>> goodsList=goodsService.getGoodsListByIds(goodsId,size,color,num);
			List<Address> addrs=addressService.getAddressByUserId(this.getLoginUserId());
			m.put("goodsList", goodsList);
			m.put("addrs", addrs);
			System.out.println(goodsList);
		}
		m.put("goodsId", goodsId);
		m.put("size", size);
		m.put("color", color);
		m.put("num", num);
		return "/order/buyGoods";
	}
	
	@RequestMapping("/addOrder")
	public String addOrder(@RequestParam String address,HttpSession session,
			Map<String,String> m){
		try{
			Order order=new Order();
			order.setUserId(this.getLoginUserId());
			order.setOrderAddress(address);
			//order.setOrderPostalfee(Float.parseFloat(orderPostalfee));
			String[] goodsId=(String[])session.getAttribute("goodsId");
			String[] size=(String[])session.getAttribute("size");
			String[] color=(String[])session.getAttribute("color");
			String[] num=(String[])session.getAttribute("num");
			List<Map<String,Object>> goodsList=goodsService.getGoodsListByIds(goodsId,size,color,num);
			List<OrderDetail> orderDetails=new ArrayList<OrderDetail>();
			Map map=goodsList.get(0);
			float postalfee=(Float)map.get("goodsPostalfee");
			for(int i=0;i<goodsList.size();i++){
				OrderDetail od=new OrderDetail();
				od.setGoodsId(Integer.parseInt((String)(goodsList.get(i).get("goodsId"))));
				od.setOdetailName((String)goodsList.get(i).get("goodsName"));
				od.setOdetailPrice((Float)(goodsList.get(i).get("goodsDiscount")));
				od.setOdetailSize((String)goodsList.get(i).get("size"));
				od.setOdetailColor((String)goodsList.get(i).get("color"));
				od.setOdetailNum(Integer.parseInt(((String)goodsList.get(i).get("num"))));
				od.setOdetailPic((String)goodsList.get(i).get("goodsPic"));
				float temp=(Float)(goodsList.get(i).get("goodsPostalfee"));
				if(postalfee>temp)
					postalfee=temp;
				orderDetails.add(od);
			}
			order.setOrderPostalfee(postalfee);
			//System.out.println(orderDetails);
		
			
			orderService.addOrder(order,orderDetails);
			m.put("orderId",order.getOrderId()+"");
			return "/order/addOrder";
		}
		catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			this.addMessage("提交订单失败");
			this.addRedirURL("返回", "@back");
			return EXECUTE_RESULT;
		}
	}
	
	/*@RequestMapping("/addOrder")
	public String addOrder(@RequestParam String address,@RequestParam String orderPostalfee,HttpSession session,
			Map<String,String> m){
		System.out.println(address);	
		for(int i=0;i<((String[])session.getAttribute("goodsId")).length;i++){
			System.out.print(((String[])session.getAttribute("goodsId"))[i]+"  ");
			System.out.print(((String[])session.getAttribute("goodsName"))[i]+"  ");
			System.out.print(((String[])session.getAttribute("goodsDiscount"))[i]+"  ");
			System.out.print(((String[])session.getAttribute("size"))[i]+"  ");
			System.out.print(((String[])session.getAttribute("color"))[i]+"  ");
			System.out.print(((String[])session.getAttribute("num"))[i]+"  ");
			System.out.println(orderPostalfee);
			
		}
			Map<String,String[]> m=new HashMap<String,String[]>();
		m.put("goodsId", (String[])session.getAttribute("goodsId"));
		m.put("orderName", (String[])session.getAttribute("goodsName"));
		m.put("orderPrice", (String[])session.getAttribute("goodsDiscount"));
		m.put("orderSize", (String[])session.getAttribute("size"));
		m.put("orderColor", (String[])session.getAttribute("color"));
		m.put("orderNum", (String[])session.getAttribute("num"));
		m.put("orderPic", (String[])session.getAttribute("pic"));
		Order order=new Order();
		order.setUserId(this.getLoginUserId());
		order.setOrderAddress(address);
		order.setOrderPostalfee(Float.parseFloat(orderPostalfee));
		
		List<OrderDetail> orderDetails=new ArrayList<OrderDetail>();
		for(int j=0;j<((String[])session.getAttribute("goodsId")).length;j++){
			OrderDetail od=new OrderDetail();
			od.setGoodsId(Integer.parseInt(((String[])session.getAttribute("goodsId"))[j]));
			od.setOdetailName(((String[])session.getAttribute("goodsName"))[j]);
			od.setOdetailPrice(Float.parseFloat(((String[])session.getAttribute("goodsDiscount"))[j]));
			od.setOdetailSize(((String[])session.getAttribute("size"))[j]);
			od.setOdetailColor(((String[])session.getAttribute("color"))[j]);
			od.setOdetailNum(Integer.parseInt(((String[])session.getAttribute("num"))[j]));
			od.setOdetailPic(((String[])session.getAttribute("pic"))[j]);
			orderDetails.add(od);
		}
		//System.out.println(orderDetails);
		try{
			
			this.getServMgr().getOrderService().addOrder(order,orderDetails);
			m.put("orderId",order.getOrderId()+"");
			return "/order/addOrder";
		}
		catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			this.addMessage("提交订单失败");
			this.addRedirURL("返回", "@back");
			return EXECUTE_RESULT;
		}
	}*/
	
	@RequestMapping("/toPay")
	public String toPay(@RequestParam String orderId,Map<String,String> m){
		m.put("orderId",orderId);
		return "/order/addOrder";
	}
	@RequestMapping("/payForOrder")
	public String payForOrder(@RequestParam String orderId,Map<String,String> m){
		try{
			orderService.updateOrderStatus(orderId,Constants.ORDER_PAID);
			m.put("orderId",orderId);
			return "forward:/order/getMyOrders";
		}
		catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			return EXECUTE_RESULT;
		}
	}
	@RequestMapping("/pay")
	public String pay(@RequestParam String orderId,Map<String,String> m){
		try{
			
			return "/order/pay";
		}
		catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			return EXECUTE_RESULT;
		}
	}
	
	@RequestMapping("/getMyOrders")
	public String getMyOrders(@RequestParam(required=false) String status,Map<String,Page<Order>> m){
		System.out.println("-----------"+status);
		Page<Order> orders=orderService.getMyOrders(this.getLoginUserId(),status);
		m.put("orders", orders);
		return "/order/orderList";
	}
	
	@RequestMapping("/delOrder")
	public String delOrder(@RequestParam String orderId){
		try{
			int status=orderService.getOrderStatusById(orderId);
			if(status==Constants.ORDER_SUCCEED || status==Constants.ORDER_CLOSED){
				orderService.deleteOrder(orderId);
				//return "forward:/order/getMyOrders";
				return "forward:"+getReferUrl();
			}else{
				this.addMessage("抱歉，目前的订单状态不允许删除订单");
				this.addRedirURL("返回", "@back");
				return EXECUTE_RESULT;
			}
		}
		catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			this.addMessage("删除订单失败");
			this.addRedirURL("返回", "@back");
			return EXECUTE_RESULT;
		}
	}
	
	@ResponseBody
	@RequestMapping("/handleOrderStatus")
	public Map<String,String> handleOrderStatus(@RequestParam String orderId,@RequestParam int status){
		Map<String,String> m=new HashMap<String,String>();
		try{
			boolean flag=false;
			int curStatus=orderService.getOrderStatusById(orderId);
			switch(status){
			case 2://申请退款
				if(curStatus==Constants.ORDER_PAID)		{flag=true;}
				break;
			case 5://确认收货
				if(curStatus==Constants.ORDER_SENDING)	{flag=true;}
				break;
			case 6://申请退货
				if(curStatus==Constants.ORDER_SENDING)	{flag=true;}
				break;
			case 9://取消订单
				if(curStatus==Constants.ORDER_WAITING)	{flag=true;}
				break;
			default:
				flag=false;
			}		
			if(flag){
				orderService.updateOrderStatus(orderId,status);
				m.put("handle", "success");
			}else{
				m.put("handle", "failure");
			}
		}
		catch(Exception e){
			log.error(this.getClass().getName()+":",e);		
			m.put("handle", "exception");
		}
		return m;
	}
	
	@RequestMapping("/getOrderDetailById")
	public String getOrderDetailById(@RequestParam String orderId,Map<String,Order> m){
		Order orderDetail=orderService.getOrderDetailById(orderId);
		m.put("orderDetail", orderDetail);
		return "/order/orderDetail";
	}
	

}
