

$(document).ready(function() {
	
//show cac anh con
	$("input[name='extraImage']").each(function(index){
		$(this).change(function(){
			showExtraImageThumbnail(this,index);
		});
	});

	$("a[name='linkRemoveExtraImage']").each(function(index){
		$(this).click(function(){
			 removeExtraImage(index);
		});
	});
});
function showExtraImageThumbnail(fileInput,index){
	var file = fileInput.files[0]
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#extraThumbnai" + index).attr("src", e.target.result)  // lua chon thumbnail 
	};
	reader.readAsDataURL(file)
	
	addNextExtraImageSection(index +1); // next select
}

function addNextExtraImageSection(index){
	htmlExtra =`
		<div class ="col border m-3 p-2" id ="divExtraImage${index}"> 
			<div id = "extraImageHeader${index}"> <label> Extra  Image #${index +1} : </label> </div>
			<div class = "m-2">
				<img id ="extraThumbnai${index}" alt="extra image #${index +1} preview" class ="img-fluid"
				 src = "${defaultImageThumbnailSrc}"/>
			</div>
			<div>
				<input type = "file" name="extraImage" 
				onchange = "showExtraImageThumbnail(this,${index})"
				accept="image/png, image/jpeg, image/jpg" />
				
			</div>
		</div>
	` ;
	htmlLinkRemove = `
		<a class ="btn fas fa-times-circle fa-2x float-right"
			href="javascript:removeExtraImage(${index - 1 })"
		> </a>	
		`		
	;
	
	$("#divProductImages").append(htmlExtra);
	$("#extraImageHeader" + (index-1)).append(htmlLinkRemove)
}

function removeExtraImage(index){
	$("#divExtraImage" + index).remove();
}


