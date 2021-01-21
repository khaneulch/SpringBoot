
import Vue from './vue';
import Common from './common';
//import * as pageComp from '../vue/pagination.vue';

Common.fetch('/findAllPage', {page : '0'}, vueListFiltered);

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
				if( v.length >= 2) {
					var param = {search : '%' + v + '%'};
					var _vue = this;
					Common.fetch('/findAllPage', param, function (data) {
						_vue.content = data.content;
					});
				}
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
			//'pagination' : pageComp
		}
	});
}