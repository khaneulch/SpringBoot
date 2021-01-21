<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Vue Test</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/sketchy/bootstrap.min.css"/>
<link rel="stylesheet" href="../css/style.css" />
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha256-4+XzXVhsDmqanXGHaHvgh1gMQKX40OUvDEBTu8JcmNs=" crossorigin="anonymous"></script>
<script src="./js/common_original.js"></script>
</head>
<body>
	<div id="example" class="container" v-cloak>
		<h2>total : {{totalElements}}</h2>
		<div class="input-group mb-3">
			<input type="text" class="form-control" placeholder="search" v-model="search">
		</div>
		<table id="list" class="table">
			<colgroup>
				<col width="20%">
				<col width="20%">
				<col width="20%">
				<col width="20%">
				<col width="20%">
			</colgroup>
			<thead>
				<tr>	
					<th scope="col">ID</th>
					<th scope="col">NAME</th>
					<th scope="col">PRICE</th>
					<th scope="col">-</th>
					<th scope="col">-</th>
				</tr>
			</thead>
			<tbody id="items">
				<tr v-for="(i, k) in content">
					<th scope="row">{{i.id}}</th>
					<td>
						<input type="text" class="form-control" placeholder="name" v-model="i.itemName">
					</td>
					<td>
						<input type="text" class="form-control" placeholder="name" v-model.number="i.itemPrice">
					</td>
					<td><button type="button" class="btn btn-primary" @click="updateItem(k)">수정</button></td>
					<td><button type="button" class="btn btn-primary" @click="deleteItem(k)">삭제</button></td>
				</tr>
				<tr v-bind:class="{ none : isActive }">
					<td colspan="2">
						<div class="input-group mb-3">
							<input type="text" class="form-control" placeholder="name" v-model="itemName">
						</div>
					</td>
					<td>
						<div class="input-group mb-3">
							<input type="number" class="form-control" placeholder="price" v-model.number="itemPrice">
						</div>
					</td>
					<td>
						<button type="button" class="btn btn-primary" @click="saveItem">저장</button>
					</td>
					<td>
						<button type="button" class="btn btn-primary" @click="cancelItem">취소</button>
					</td>
				</tr>
			</tbody>
		</table>
		
		<button type="button" class="btn btn-primary" v-bind:class="{none : !isActive}" @click="addClick">추가</button>
		
		<pagination :total="totalPages" :pagenum="number" :link="link"></pagination>
	</div>

</body>

<script>
	$( function() {
		Common.fetch('/findAllPage', {page : '0'}, vueListFiltered);
	});
	
	
	var pageComp = Vue.component('pagination', {
		template	: '#pagination-template',
		props		: ['total', 'pagenum', 'link'],
		data() {
			return {
				pagearr : []
			}
		},
		mounted() {
			this.pageDataRender();
		},
		watch : {
			pagenum : function() {
				this.pageDataRender();
			}
		},
		methods : {
			pageChange( page) {
				var _arr = this.pagearr;
				var _vue = this;
				
				if( page == 'prev') page = _arr[1] - 1;
				if( page == 'next') page = _arr[_arr.length - 2] + 1;
				
				Common.fetch('/findAllPage', {page : String(page - 1)}, function( data) {
					_vue.$root.content = data.content;
					_vue.$root.number = data.number;
				});
			},
			pageDataRender() {
				this.pagearr = [];
				var _total = this.total;
				var _pageNum = this.pagenum;
				if( this.total < 10) {
					for(var i = 1; i <= _total; i ++) {  
						this.pagearr.push(i);
					}
				} else if( _total >= 10) {
					if( _pageNum < 5) {
						for( var i = 1; i <= _pageNum + 4; i++) {
							this.pagearr.push(i);
						}
					} else {
						if( _pageNum + 4 > _total) {
							for( var i = _pageNum - 4; i <= _total; i++) {
								this.pagearr.push(i);
							}
						} else if( _pageNum + 4 <= _total) {
							for( var i = _pageNum - 4; i <= _pageNum + 4; i++) {
								this.pagearr.push(i);
							}
						}
					}
				}
				
				if( this.pagearr[0] > 1) {
					this.pagearr.splice( 0, 0, 'prev' );
				}
				
				if( this.pagearr[this.pagearr.length - 1] < _total) {
					this.pagearr.splice( this.pagearr.length, 0, 'next' );
				}
			}
		}
	});
	

	/* 검색된 데이터를 뿌려줌 */
	function vueListFiltered( resp) {
		
		var model = {
			search 			: '',
			isActive		: true,
			itemName		: '',
			itemPrice		: '',
			link			: '/findAllPage'
		};
		
		$.extend( model, resp);
		
		var list = new Vue({
			el : '#example',
			data : model,
			watch : {
				/* 서버 데이터 검색 */
				search : function( v) {
					//if( v.length >= 2) {
						var param = {search : '%' + v + '%'};
						var _vue = this;
						Common.fetch('/findAllPage', param, function (data) {
							_vue.content = data.content;
						});
					//}
				}
			},
			methods : {
				addClick : function(e) {
					this.isActive = false;
				},
				updateItem : function( k) {
					var _vue = this;
					
					var params = { 
						id : _vue.content[k].id, 
						itemName : _vue.content[k].itemName, 
						itemPrice : _vue.content[k].itemPrice
					};
					
					if( params.itemName && params.itemPrice) {
						Common.fetch('/saveItem', params, function (data) {
							_vue.content.splice( k, 1, data);
						});
					}
				},
				deleteItem : function( k) {
					var _vue = this;
					
					Common.fetch('/deleteItem', {id : _vue.content[k].id}, function( data) {
						if( data) {
							_vue.content.splice(k, 1);
						}
					});
				},
				saveItem : function() {
					var _vue = this;
					var params = { itemName : _vue.itemName, itemPrice : _vue.itemPrice};
					if( params.itemName && params.itemPrice) {
						Common.fetch('/saveItem', params, function (data) {
							_vue.itemName = '';
							_vue.itemPrice = '';
							_vue.content.push( data);
						});
					}
				},
				cancelItem : function() {
					this.isActive = true;
				}
			},
			components : {
				'pagination' : pageComp
			}
		});
	}
</script>

<template id="pagination-template">
	<div id="pagination">
		<nav aria-label="Page navigation example">
			<ul class="pagination">
				<li class="page-item" v-for="i in pagearr" v-bind:key="i" v-bind:class="{active : i == pagenum + 1}"><a class="page-link" @click="pageChange(i)">{{i}}</a></li>				
			</ul>
		</nav>
	</div>
</template>
</html>
