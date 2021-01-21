package com.demo.domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity									// JPA Entity임을 표시
@Table(name = "mt_support_item")		// 테이블명
@Setter									
@Getter
@NoArgsConstructor						// 기본 생성자
@DynamicUpdate
public class SupportItem {
	@Id
	@GeneratedValue						// 자동 채번
	@Column(nullable = false)
	private int id;
	
	@Column(name="item_name")
	private String itemName;
	
	@Column(name="item_price")
	private String itemPrice;
	
	@Column(name="itemImg")
	private String itemImg;
	
	@Column(name="created_dt", updatable=false)
	@CreatedDate
	private LocalDateTime createdDt;
	
	@Column(name="updated_dt")
	@LastModifiedDate
	private LocalDateTime updatedDt;
	
	@Builder
	public SupportItem(String itemName, String itemPrice, String itemImg) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemImg = itemImg;
	}
	
	@PrePersist
	public void createdDt() {
		this.createdDt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void updatedDt() {
		this.updatedDt = LocalDateTime.now();
	}
}
