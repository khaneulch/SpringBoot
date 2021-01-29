<template>
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
				<tr v-for="(i, k) in content" :key="k">
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
		
		<pagination @childs-event="parentMethod" :totalPages="totalPages" :number="number" :link="link"></pagination>
	</div>
</template>

<script>
  import Common from '../../main/resources/static/js/common';
  import $ from '../../main/resources/static/js/jquery';
  import pageComp from './Pagination';

  var model = {
    search 			: '',
    isActive		: true,
    itemName		: '',
    itemPrice		: '',
    link			  : '/api/findAllPage',
    content     : [],
    totalPages  : 0,
    totalElements: 0,
    number      : 0
  };

  export default {
    name : 'example', 
    data () {
      return model;
    },
    mounted() {
      var _vue = this;
      Common.fetch('/api/findAllPage', {page : '0'}, function( data) {
        $.extend( _vue, data);
      });
    },
    watch : {
      /* 서버 데이터 검색 */
      search( v) {
        var param = {search : '%' + v + '%'};
        var _vue = this;
        Common.fetch('/api/findAllPage', param, function (data) {
          $.extend( _vue, data);
        });
      }
    },
    methods : {
      parentMethod( content, number) {
        this.content = content; 
        this.number = number; 
      },
      addClick() {
        this.isActive = false;
      },
      updateItem( k) {
        var _vue = this;
        
        var params = { 
          id : _vue.content[k].id, 
          itemName : _vue.content[k].itemName, 
          itemPrice : _vue.content[k].itemPrice
        };
        
        if( params.itemName && params.itemPrice) {
          Common.fetch('/api/saveItem', params, function (data) {
            _vue.content.splice( k, 1, data);
          });
        }
      },
      deleteItem( k) {
        var _vue = this;
        
        Common.fetch('/api/deleteItem', {id : _vue.content[k].id}, function( data) {
          if( data) {
            _vue.content.splice(k, 1);
          }
        });
      },
      saveItem() {
        var _vue = this;
        var params = { itemName : _vue.itemName, itemPrice : _vue.itemPrice};
        if( params.itemName && params.itemPrice) {
          Common.fetch('/api/saveItem', params, function (data) {
            _vue.itemName = '';
            _vue.itemPrice = '';
            _vue.content.push( data);
          });
        }
      },
      cancelItem() {
        this.isActive = true;
      }
    },
    components : {
      'pagination' : pageComp
    }
  }
</script>