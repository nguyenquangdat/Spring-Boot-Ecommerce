$(document).ready(function() {
	$("#btnCancel").on("click", function() {
		window.location = "[[@{/products}]]";

	});

	$("#fileImage").change(function() {  // xu ly cho anh vao trong thumbnail 
	  if(!checkFileSize(this)) {
		return ;
	}
		showImageThumbnail(this);
	});

	$("#logoutLink").on("click", function(e) {
		e.preventDefault();
		document.LogoutForm.submit();
	});

	customizeDropDownMenu();

});

function showImageThumbnail(fileInput) {
	var file = fileInput.files[0]
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result)  // lua chon thumbnail 
	};
	reader.readAsDataURL(file)
}

function checkFileSize(fileInput) {
	fileSize = fileInput.files[0].size;
	if
		(fileSize > 1048576) {
		fileInput.setCustomValidity("You must choose an image small than 1MB ")
		return false;
	}

	else {
		fileInput.setCustomValidity("")
		return true;
	}
}


function customizeDropDownMenu() {
	$(".navbar .dropdown").hover(
		function() {
			$(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
		},
		function() {
			$(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideUp();
		}
	);

	$(".dropdown > a").click(function() {
		location.href = this.href;
	});


}