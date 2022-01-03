dropdownBrands = $("#brand");
dropdownCategories = $("#category")

$(document).ready(function() {
	$("#shortDescription").richText();
	$("#fullDescription").richText();
	dropdownBrands.change(function() {
		dropdownCategories.empty();
		getCategories();
	});
	getCategoriesNewform();
//show cac anh con


});

function getCategoriesNewform(){
	cateIdFile = $("#categoryId");
	editMode = false;
	
	if(cateIdFile.length) {
		editMode = true;
	}
	
	if(!editMode) getCategories();
}

function getCategories() {
	brandId = dropdownBrands.val();
	url = moduleBrandURL + "/" + brandId + "/categories";

	$.get(url, function(responseJson) {
		$.each(responseJson, function(index, category) {
			$("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
		});
		
	});

}
