package com.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity									// JPA Entity임을 표시
@Table(name = "mt_support_item")		// 테이블명
@Setter									
@Getter
@NoArgsConstructor						// 기본 생성자
public class SupportItem {
	@Id
	@GeneratedValue						// 자동 채번
	@Column(nullable = false)
	private int id;
	
	@Column
	private String item_name;
	
	@Column
	private String item_price;
	
	@Column
	private String item_img;
	
	@Column
	private Date created_dt;
	
	@Column
	private Date updated_dt;
	
	@Builder
	public SupportItem(String item_name, String item_price, String item_img) {
		this.item_name = item_name;
		this.item_price = item_price;
		this.item_img = item_img;
	}
}
