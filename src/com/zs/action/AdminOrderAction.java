package com.zs.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zs.core.Constants;
import com.zs.core.common.Page;
import com.zs.model.Order;
import com.zs.service.OrderService;
//管理订单控制层
@Controller
@RequestMapping("/admin/order")
public class AdminOrderAction extends BaseAction{
	
	@Autowired
	OrderService orderService;

	@RequestMapping("/getAdminOrders")
	public String getAdminOrders(@RequestParam(required=false) String orderCode,@RequestParam(required=false) String userName,
	@RequestParam(required=false) String orderStatus,@RequestParam(required=false) String startDate,@RequestParam(required=false) String endDate,
	@RequestParam(required=false) String sort,Map<String,Object> m){
		Page<Order> orders=orderService.getAdminOrders(orderCode,userName,orderStatus,startDate,endDate,sort);
		m.put("orders", orders);
		return "/admin/order/orderList";
	}
	
	@RequestMapping("/getOrderDetailById")
	public String getOrderDetailById(@RequestParam String orderId,Map<String,Order> m){
		Order orderDetail=orderService.getOrderDetailById(orderId);
		m.put("orderDetail", orderDetail);
		return "/admin/order/orderDetail";
	}
	
	/*@ResponseBody
	@RequestMapping("/sendGoods")
	public Map<String,String> sendGoods(@RequestParam String orderId,@RequestParam int status,@RequestParam String orderPostname,@RequestParam String orderPostcode){
		Map<String,String> m=new HashMap<String,String>();
		try{
			if(orderService.getOrderStatusById(orderId)==Constants.ORDER_PAID){
				orderService.updateOrderPostInfo(orderId,orderPostname,orderPostcode,status);
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
	}*/
	
	@ResponseBody
	@RequestMapping("/handleOrderStatus")
	public Map<String,String> handleOrderStatus(@RequestParam String orderId,@RequestParam int status,@RequestParam(required=false) String orderPostname,@RequestParam(required=false) String orderPostcode){
		Map<String,String> m=new HashMap<String,String>();
		try{
			
			switch(status){
			case 3://同意退款
				//System.out.println("----"+this.getServMgr().getOrderService().getOrderStatusById(orderId));
				if(orderService.getOrderStatusById(orderId)==Constants.ORDER_ASKREFUND){
					orderService.updateOrderStatus(orderId,status);
					m.put("handle", "success");
				}else{
					m.put("handle", "failure");
				}
				break;
			
			case 7://同意退货
				if(orderService.getOrderStatusById(orderId)==Constants.ORDER_ASKRETURN){
					orderService.updateOrderStatus(orderId,status);
					m.put("handle", "success");
				}else{
					m.put("handle", "failure");
				}	
				break;
			case 8://确认收到退货
				if(orderService.getOrderStatusById(orderId)==Constants.ORDER_RETURNING){
					orderService.updateOrderStatus(orderId,status);
					m.put("handle", "success");
				}else{
					m.put("handle", "failure");
				}	
				break;	
			case 9://取消订单
				if(orderService.getOrderStatusById(orderId)==Constants.ORDER_WAITING){
					orderService.updateOrderStatus(orderId,status);
					m.put("handle", "success");
				}else{
					m.put("handle", "failure");
				}	
				break;
			case 4://去发货
				if(orderService.getOrderStatusById(orderId)==Constants.ORDER_PAID){
					orderService.updateOrderStatus(orderId,status);
					orderService.updateOrderPostInfo(orderId,orderPostname,orderPostcode);
					m.put("handle", "success");
				}else{
					m.put("handle", "failure");
				}
				break;
			default:
				m.put("handle", "failure");
				break;
			}		
		}
		catch(Exception e){
			log.error(this.getClass().getName()+":",e);		
			m.put("handle", "exception");
		}
		return m;
	}
	
	@RequestMapping("/delOrder")
	public String delOrder(@RequestParam String orderId){
		try{
			int status=orderService.getOrderStatusById(orderId);
			if(status==Constants.ORDER_SUCCEED || status==Constants.ORDER_CLOSED 
				|| status==Constants.ORDER_REFUNDSUCCEED || status==Constants.ORDER_RETURNSUCEED){
				orderService.deleteOrder(orderId);
				//return "forward:/order/getMyOrders";
				//return "forward:"+getReferUrl();
				this.addMessage("删除订单成功");
				this.addRedirURL("返回",getReferUrl());
			}else{
				this.addMessage("只有状态为交易成功、交易关闭、退款成功、退货成功的订单允许删除，当前删除操作失败");
				this.addRedirURL("返回", "@back");
				
			}
		}
		catch(Exception e){
			log.error(this.getClass().getName()+":",e);
			this.addMessage("取消订单失败");
			this.addRedirURL("返回", "@back");
			
		}
		return EXECUTE_RESULT;
	}
	
	@RequestMapping("/delOrderByIds")
	public String delOrderByIds(@RequestParam String[] orderIds){
		try{
			Integer[] status=orderService.getOrderStatusByIds(orderIds);
			boolean flag=true;
			for(int i=0;i<status.length;i++){
				if(status[i]!=Constants.ORDER_REFUNDSUCCEED
						&&status[i]!=Constants.ORDER_SUCCEED
						&&status[i]!=Constants.ORDER_RETURNSUCEED
						&&status[i]!=Constants.ORDER_CLOSED){
					flag=false;
				}
			}
			
			if(flag){
				orderService.deleteOrderByIds(orderIds);
				this.addMessage("批量删除订单成功");
				this.addRedirURL("返回",getReferUrl());
			}
			else{
				this.addMessage("只有状态为交易成功、交易关闭、退款成功、退货成功的订单允许删除，当前批量删除操作失败");
				this.addRedirURL("返回","@back");
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(this.getClass().getName()+":",e);			
			this.addMessage("批量删除订单失败");
			this.addRedirURL("返回","@back");
		}
		return EXECUTE_RESULT;
	}
}
