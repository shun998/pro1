package com.zs.service;

import java.util.List;

import com.zs.core.common.Page;
import com.zs.model.Order;
import com.zs.model.OrderDetail;
//订单业务处接口
public interface OrderService {

	int addOrder(Order order,List<OrderDetail> orderDetails);

	Page<Order> getMyOrders(int loginUserId,String status);

	void deleteOrder(String orderId);

	void updateOrderStatus(String orderId, int status);

	int getOrderStatusById(String orderId);

	Order getOrderDetailById(String orderId);

	Page<Order> getAdminOrders(String orderCode, String userName, String orderStatus, String startDate, String endDate,
			String sort);

	void updateOrderPostInfo(String orderId, String orderPostname, String orderPostcode);

	void deleteOrderByIds(String[] orderIds);

	Integer[] getOrderStatusByIds(String[] orderIds);

}
