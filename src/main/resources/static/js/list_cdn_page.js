import Common from './common_original.js';
import pageComp from './page_component.js';
import './vue_filter.js';

var model = {
	search: '',
	isActive: true,
	itemName: '',
	itemPrice: '',
	link: '/api/findAllPage',
	content: [],
	totalPages: 0,
	totalElements: 0,
	number: 0
};

new Vue({
	el: '#example',
	data() {
		return model;
	},
	mounted() {
		var _vue = this;
		Common.fetch('/api/findAllPage', { page: '0' }, function (data) {
			$.extend(_vue, data);
		});
	},
	watch: {
		/* 서버 데이터 검색 */
		search(v) {
			var param = { search: `%${v}%` };
			var _vue = this;
			Common.fetch('/api/findAllPage', param, function (data) {
				$.extend(_vue, data);
			});
		}
	},
	methods: {
		parentMethod(content, number) {
			this.content = content;
			this.number = number;
		},
		addClick() {
			this.isActive = false;
		},
		updateItem(k) {
			var _vue = this;

			var params = {
				id: _vue.content[k].id,
				itemName: _vue.content[k].itemName,
				itemPrice: _vue.content[k].itemPrice
			};

			if (params.itemName && params.itemPrice) {
				Common.fetch('/api/saveItem', params, function (data) {
					_vue.content.splice(k, 1, data);
				});
			}
		},
		deleteItem(k) {
			var _vue = this;

			Common.fetch('/api/deleteItem', { id: _vue.content[k].id }, function (data) {
				if (data) {
					_vue.content.splice(k, 1);
				}
			});
		},
		saveItem() {
			var _vue = this;
			var params = { itemName: _vue.itemName, itemPrice: _vue.itemPrice };
			if (params.itemName && params.itemPrice) {
				Common.fetch('/api/saveItem', params, function (data) {
					_vue.itemName = '';
					_vue.itemPrice = '';
					_vue.content.push(data);
				});
			}
		},
		cancelItem() {
			this.isActive = true;
		}
	},
	components: {
		'pagination': pageComp
	}
});