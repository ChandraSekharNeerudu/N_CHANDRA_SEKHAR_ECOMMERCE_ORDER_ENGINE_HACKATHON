package com.Ecommerce.engine;

import com.Ecommerce.Service.AuditService;
import com.Ecommerce.Service.CartService;
import com.Ecommerce.Service.DiscountService;
import com.Ecommerce.Service.EventService;
import com.Ecommerce.Service.FraudDetectionService;
import com.Ecommerce.Service.OrderService;
import com.Ecommerce.Service.PaymentService;
import com.Ecommerce.Service.ProductService;

public class ServiceRegistry {
	 private final AuditService auditService;
	    private final ProductService productService;
	    private final CartService cartService;
	    private final DiscountService discountService;
	    private final PaymentService paymentService;
	    private final EventService eventService;
	    private final FraudDetectionService fraudDetectionService;
	    private final OrderService orderService;
	    private final ConcurrencyEngine concurrencyEngine;

	    public ServiceRegistry() {
	        // Build in dependency order
	        this.auditService          = new AuditService();
	        this.productService        = new ProductService(auditService);
	        this.cartService           = new CartService(productService, auditService);
	        this.discountService       = new DiscountService();
	        this.paymentService        = new PaymentService();
	        this.eventService          = new EventService(auditService);
	        this.fraudDetectionService = new FraudDetectionService(auditService);
	        this.orderService          = new OrderService(
	                productService, cartService, paymentService,
	                auditService, eventService, fraudDetectionService);
	        this.concurrencyEngine     = new ConcurrencyEngine(productService, auditService);
	    }

	    public AuditService audit()         { return auditService; }
	    public ProductService product()     { return productService; }
	    public CartService cart()           { return cartService; }
	    public DiscountService discount()   { return discountService; }
	    public PaymentService payment()     { return paymentService; }
	    public EventService event()         { return eventService; }
	    public FraudDetectionService fraud(){ return fraudDetectionService; }
	    public OrderService order()         { return orderService; }
	    public ConcurrencyEngine concurrency() { return concurrencyEngine; }

	    public void shutdown() {
	        productService.shutdown();
	        System.out.println("  Services shut down.");
	    }
}
