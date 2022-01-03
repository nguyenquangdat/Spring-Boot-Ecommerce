
function addNnextDetailSection(){
	allDivDetails = $("[id ^= 'divDetail']") ;
	divDetailsCount = allDivDetails.length;
	

	//alert(divDetailsCount);
	
 htmlDetailSection = `
	<div class ="form-inline" id = "divDetail${divDetailsCount}"> 
		<label class ="m-3"> Name :</label>
		<input type = text class ="form-control" name ="detailNames" maxlength="255"/>
		<label class ="m-3"> Value :</label>
		<input type = text class ="form-control" name ="detailValues" maxlength="255"/>
	</div> 
 `;
 
 $("#divProductDetails").append(htmlDetailSection);
 
  previousDetailSection = allDivDetails.last();
  previousDetailSectionID = previousDetailSection.attr("id");
  
 htmlLinkRemove = `
		<a class ="btn fas fa-times-circle fa-2x "
			href = "javascript:removedetailSecitionById('${previousDetailSectionID}')"
		> </a>
		`		
	;
 previousDetailSection.append(htmlLinkRemove);
 
 $("input[name ='detailNames']").last().focus();
 	
}

function removedetailSecitionById(id){
	
	$("#"+id).remove();
}