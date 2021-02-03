package com.zs.mapper;

import java.util.List;
import java.util.Map;

import com.zs.core.common.Page;
import com.zs.model.Order;
import com.zs.model.OrderDetail;
//订单mapper接口
public interface OrderMapper {

	void addOrder(Order order);

	List<Order> getMyOrders(Page<Order> page);

	void addOrderDetail(OrderDetail od);

	void deleteOrderDetail(String orderId);

	void deleteOrder(String orderId);

	void updateOrderStatus(String orderId, int status);

	int getOrderStatusById(int orderId);

	Order getOrderDetailById(String orderId);

	List<Order> getOrders(Page<Order> page);

	

	Integer[] getOrderStatusByIds(String[] orderIds);

	void updateOrderPostInfo(String orderId, String orderPostname, String orderPostcode);

}
