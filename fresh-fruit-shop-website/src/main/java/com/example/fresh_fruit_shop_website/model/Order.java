package com.example.fresh_fruit_shop_website.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "shipping_name")
    private String shippingName;

    @Column(name = "shipping_address", columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "shipping_city")
    private String shippingCity;

    @Column(name = "shipping_zipcode")
    private String shippingZipcode;

    @Column(name = "shipping_phone")
    private String shippingPhone;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public Order() {
        // Default constructor for JPA
    }

    public enum Status {
        PENDING,
        CONFIRMED,  // <-- Added this status
        PROCESSING,
        SHIPPED,
        DELIVERED
    }

    // Getters and Setters

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getShippingName() { return shippingName; }
    public void setShippingName(String shippingName) { this.shippingName = shippingName; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getShippingCity() { return shippingCity; }
    public void setShippingCity(String shippingCity) { this.shippingCity = shippingCity; }

    public String getShippingZipcode() { return shippingZipcode; }
    public void setShippingZipcode(String shippingZipcode) { this.shippingZipcode = shippingZipcode; }

    public String getShippingPhone() { return shippingPhone; }
    public void setShippingPhone(String shippingPhone) { this.shippingPhone = shippingPhone; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    @Override
    public String toString() {
        return "Order{" +
            "orderId=" + orderId +
            ", user=" + user +
            ", totalAmount=" + totalAmount +
            ", status=" + status +
            ", shippingName='" + shippingName + '\'' +
            ", shippingAddress='" + shippingAddress + '\'' +
            ", shippingCity='" + shippingCity + '\'' +
            ", shippingZipcode='" + shippingZipcode + '\'' +
            ", shippingPhone='" + shippingPhone + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}
