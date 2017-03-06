var $RegSamoBrojevi=/[^0-9]/g;
var $RegSamoBrojevi2=/^([0-9]+)$/;
var $RegSamoTelefon=/[^0-9 \-\(\)\/\+]/g;
var $RegSamoTelefon2=/^([0-9 \-\(\)\/\+]+)$/;
var $RegSamoBrojeviSlova=/[^a-zA-Z0-9 ]/g;
var $RegSamoBrojeviSlova2=/^([a-zA-Z0-9 ]+)$/;

var $RegSamoSlova=/[^a-zA-Z-ŠĐŽĆČšđžćčéÉ]/g;
var $RegSamoSlova2=/[^a-zA-Z-ŠĐŽĆČšđžćčéÉ ]/g; //sa razmakom

var $RegSamoTekst=/[^a-zA-Z0-9ŠĐŽĆČšđžćčéÉ \!\,\.\"\'\:\;\?\+\-\–\_\(\)\&\u0100-\u017F\u0400-\u04FF\&\']/g;
var $RegSamoTekst3=/[^a-zA-Z0-9ŠĐŽĆČšđžćčéÉ \/\!\,\.\"\'\:\;\?\+\-\–\_\(\)\&\u0100-\u017F\u0400-\u04FF\&\'\/]/g;

var $RegSamoTekst2=/^([a-zA-Z0-9ŠĐŽĆČšđžćčéÉ \!\,\.\"\'\:\;\?\+\-\–\_\(\)\&\u0100-\u017F\u0400-\u04FF\&\'\/]+)$/;
var $RegSamoTekst33=/^([a-zA-Z0-9ŠĐŽĆČšđžćčéÉ \/\!\,\.\"\'\:\;\?\+\-\–\_\(\)\&\u0100-\u017F\u0400-\u04FF\&\'\/]+)$/;
var $RegSamoTekstEnter=/[^a-zA-Z0-9ŠĐŽĆČšđžćčéÉ \!\,\.\"\'\:\;\?\+\-\–\_\(\)\&\u0100-\u017F\u0400-\u04FF\n\&\']/g;
var $RegSamoTekstEnter2=/^([a-zA-Z0-9ŠĐŽĆČšđžćčéÉ \!\,\.\"\'\:\;\?\+\-\–\_\(\)\&\u0100-\u017F\u0400-\u04FF\n\&\']+)$/;
//var $RegSamoEmail=/[^a-zA-Z0-9\!\#\$\%\&\'\*\+\-\/\=\?\^\_\`\{\|\}\~\.\@]/g;
var $RegSamoEmail=/[^a-zA-Z0-9\.\_\-\@]/g;
var $RegSamoEmail2=/^[a-zA-Z0-9\.\_\-]+@[a-zA-Z0-9\.\-]+\.[a-zA-Z]{2,4}$/
 
jQuery.fn.proveraZnakova=
function($reg)
{
    return this.each(function()
    {
        $(this).keyup(function()
        {
            if (this.value.match($reg)) {
                    this.value = this.value.replace($reg, '');                                        
            }
            //$(this).parent().css('border-color','#ccc');
        });
        $(this).blur(function()
        {
            if (this.value.match($reg)) {
                    this.value = this.value.replace($reg, '');                                        
            }
            //$(this).parent().css('border-color','#ccc');
        });
    });
};

//focus blur >> Enter personal data
jQuery.fn.focusBlur =
	function()
	{
	    return this.each(function()
	    {
	    	$(this).focus(function()
			{
				$(this).css('border-color','#3B5998');
			});
			$(this).blur(function(){
				$(this).css('border-color','#D9D9D9');
			});
	    });
	};  


//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
$(document).ready(function(){
	
	$('#sc_name').proveraZnakova($RegSamoSlova).focusBlur();
	$('#sc_surname').proveraZnakova($RegSamoSlova).focusBlur();
	$('#sc_street_name').proveraZnakova($RegSamoSlova2).focusBlur();
	$('#sc_house_number').proveraZnakova($RegSamoBrojeviSlova).focusBlur();
	$('#sc_postal_code').proveraZnakova($RegSamoBrojevi).focusBlur();
	$('#sc_place').proveraZnakova($RegSamoSlova2).focusBlur();
	$('#sc_country').proveraZnakova($RegSamoSlova).focusBlur();
	$('#sc_phone_number').proveraZnakova($RegSamoTelefon).focusBlur();
	
	var maxCount = 200;
	$('.korpa_kolicina_input').proveraZnakova($RegSamoBrojevi)
	.focus(function(){
	    $(this).addClass('korpa_kolicina_input_trenutni');
	})
	.keyup(function(){
	    var $count = $(this).val();
	    
	    $count = parseInt($count);
	    
	    if($count < 1) $(this).val(1);
	    if($count > maxCount) $(this).val(maxCount);
	})
	.blur(function(){
		$(this).removeClass('korpa_kolicina_input_trenutni');
		
		var $spanId = $(this).attr('data-price-span-id');
		var $oldPrice = $('#'+$spanId).text();
		var $oldTotalPrice = $('#total_price_span').text();
		
		$oldPrice = parseFloat($oldPrice);
		$oldTotalPrice = parseFloat($oldTotalPrice);
		
		var $count2 = $(this).val();
		$count2 = parseInt($count2);
	    if(isNaN($count2)) $(this).val(1);
		
	    var $count = $(this).val();
	    var $itemId = $(this).attr('data-id');
	    var $itemPrice = $(this).attr('data-price');
	    
	    var $newPrice = null;
	    var $newTotalPrice = null;
	    
		if($count > 0  &&  $count <= maxCount)
	    {
	    	addItemToCart3($itemId, $count, true);
	    	//izracunaj novu cenu!
	    	$newPrice = $count * $itemPrice;
	    	
	    	$('#'+$spanId).empty();
	    	$('#'+$spanId).append(roundNumber($newPrice, 2));
	    	
	    	//izracunaj novu ukupnu cenu
	    	if($newPrice != $oldPrice){
	    		if($newPrice > $oldPrice){
	    			$newTotalPrice = $oldTotalPrice + ($newPrice - $oldPrice);
	    		}else{
	    			$newTotalPrice = $oldTotalPrice - ($oldPrice - $newPrice);
	    		}
	    		$('#total_price_span').empty();
	    		$('#total_price_span').append(roundNumber($newTotalPrice, 2));
	    	}
	    }
	});
	
});
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

function limitText(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    }
}

function roundNumber(num, places) {
    return Math.round(num * Math.pow(10, places)) / Math.pow(10, places);
}

function addItemToCart3(itemId, quantity, addType){
	 $.post("PostItem",
			 {
				jsonData:JSON.stringify({
					addItemId:itemId,
					addItemQuantity:quantity,
					addType:addType
				})
			 }
)};

function removeItemFromCart(itemId){
	 $.post("ShoppingCart",
			 {
				jsonData:JSON.stringify({
					addItemId:itemId
				})
			 }
)};

function removeItem(btnDel, itemId) {
    if (typeof(btnDel) == "object") {
    	//umanji total price
    	var $input = $(btnDel).parent().find("input");
        var $count = $input.val();
        var $itemPrice = $input.attr('data-price');
        var $oldTotalPrice = $('#total_price_span').text();
		
        $oldTotalPrice = parseFloat($oldTotalPrice);
		$count = parseFloat($count);
		$itemPrice = parseFloat($itemPrice);
        
		var $newTotalPrice = $oldTotalPrice - ($count * $itemPrice);
		
		$('#total_price_span').empty();
		$('#total_price_span').append(roundNumber($newTotalPrice, 2));
    
    	//ukloni item
    	removeItemFromCart(itemId);
        $(btnDel).closest("tr").remove();
        
    } else {
        return false;
    }
};
