$(document).ready(function() {
	
	$('a.go').click(function(event) {
		displayForm($(this));
	});

	$('.add-product-action-button').click(function() {
		addCategories();
		addBrands();
		displayForm($(this));
	});
	
	function addCategories() {
		$("#add-product-category-select").text();
		var options = $(".category-option-to-copy");
		if (options !== 'undefined' && options.length != 0) {
			for (var i = 0; i < options.size(); i++) {
				var option = options[i];
				var categoryId = $(option).attr("data-category-id");
				var categoryName = $(option).attr("data-category-name");
				$("#add-product-category-select").append("<option name='id_category' value='" + categoryId + "'>" + categoryName + "</option>");
			}
		}
	}
	
	function addBrands() {
		$("#add-product-brand-select").text();
		var checkboxes = $(".brand-checkbox-to-copy");
		if (checkboxes !== 'undefined' && checkboxes.length != 0) {
			for (var i = 0; i < checkboxes.size(); i++) {
				var checkbox = checkboxes[i];
				var brandId = $(checkbox).attr("data-brand-id");
				var brandName = $(checkbox).attr("data-brand-name");
				$("#add-product-brand-select").append("<option name='id_brand' value='" + brandId + "'>" + brandName + "</option>");
			}
		}
	}
	
	function displayForm(link) {
		var modalFromId = link.attr("data-modal-form-id");
		event.preventDefault();
		$('.overlay').fadeIn(400, function() {
			$('#' + modalFromId).css('display', 'block').animate({
				opacity : 1,
				top : '40%'
			}, 200);
		});
	}
	
	$('.modal_close, .overlay').click(function() {
		$('.modal_form').animate({
			opacity : 0,
			top : '10%'
		}, 200, function() {
			$(this).css('display', 'none');
			$('.overlay').fadeOut(400);
		});
	});
	
	$(".inspect-product").click(function() {
		var id_product = $(this).attr("data-product-id");
		var userRole = $("#global-user-role").val();
		
		$.get($("#global-context-path").val() + "/product?id_product=" + id_product, function(data) {
			$("#product-identifier").val(id_product);
			changeTdPaddingIfNeeded(userRole);
			addSizes(data.sizes, userRole);
			addProductName(data.name);
			addProductPrice(userRole, data.price);
			addCategory(data.category);
			addBrand(data.brand);
			addImageIdentifier(data.imageIdentifier);
			addActionButton(userRole, data.sizes);
		});
		displayForm($(this));
	});
	
	function addSizes(sizes, userRole) {
		$("#product-detailed-info-sizes").text("");
		$("#product-detailed-info-sizes").append(getInputsForSizes(sizes, userRole));
	}
	
	function changeTdPaddingIfNeeded(userRole) {
		if (userRole === 'ADMIN') {
			$(".product-information-table td").css("padding", 0);
		}
	}
	
	function addProductName(name) {
		$("#product-name-td").text(name);
	}
	
	function addProductPrice(userRole, price) {
		if (userRole === 'ADMIN') {
			$("#product-price-td").text("");
			$("#product-price-td").append("<input id='amount' maxlength='9' style='text-align: center;' class='edit-product-input product-price-input' type='text' placeholder='$' name='product_price' value='" + price + "'/>");
		} else {
			$("#product-price-td").text("$" + price);
		}
	}
	
	function addCategory(category) {
		$("#product-category-td").text(category);
	}
	
	function addBrand(brand) {
		$("#product-brand-td").text(brand);
	}
	
	function addImageIdentifier(imageIdentifier) {
		$("#product-detailed-info-image").text("");
		var toAppend = "<img width='250' height='220' alt='Product image' src='" + $("#global-context-path").val() + "/images?imageIdentifier=" + imageIdentifier + "'>";
		$("#product-detailed-info-image").append(toAppend);
	}
	
	function addActionButton(userRole, sizes) {
		$("#product-detailed-info-add-to-cart").text("");
		if (userRole === 'ADMIN') {
			$("#product-detailed-info-add-to-cart").append("<a class='red-button' href='" + $("#global-context-path").val() 
					+"/controller/delete-product?id_product=" + $("#product-identifier").val() + "'>" + $("#delete-product-button-text").val() + "</a>");
			$("#product-detailed-info-add-to-cart").append("<input class='green-button' type='submit' value='" + $("#save-product-button-text").val() + "'/>");
		} else {
			if (sizes.length === 0) {
				$("#product-detailed-info-add-to-cart").append("<p>" + $("#clothes-has-no-sizes").val() + ".</p>");
			} else {
				$("#product-detailed-info-add-to-cart").append("<a href='#' class='green-button add-to-cart-link'>" + $("#link-add-identifier-text").val() + "</a>");
			}
		}
	}
	
	$("body").on("keyup", ".product-price-input", function() {
	    var valid = /^\d{0,6}(\.\d{0,2})?$/.test(this.value),
	        val = this.value;
	    
	    if(!valid){
	        console.log("Invalid input!");
	        this.value = val.substring(0, val.length - 1);
	    }
	});
	
	$("#product-detailed-info-add-to-cart").on('click', '.add-to-cart-link', function() {
		var id_product = $("#product-identifier").val();
		var selectedVal = "";
		var selected = $("input[type='radio'][name='product-size-radio']:checked");
		if (selected.length > 0) {
		    selectedVal = selected.val();
		}
		$.ajax({
			  type: "POST",
			  url: $("#global-context-path").val() + "/cart",
			  data: {"id_product": id_product, "size" : selectedVal},
			  success: function(data) {
				  if ($("#cart-size-span").length === 0) {
					  $("#cart-content").text("");
					  $("#cart-content").append("Cart contains <span id='cart-size-span'>" + data.count + "</span> product(s)<br /> worth $<span id='cart-total-price-span'>" + data.totalPrice + "</span>.");
				  } else {
					  $("#cart-size-span").text(data.count);
					  $("#cart-total-price-span").text(data.totalPrice);
				  }
			  },
			  dataType: "json"
			});
		return false;
	});
	
	function getInputsForSizes(sizes, userRole) {
		if (userRole === 'ADMIN') {
			return getCheckBoxesString(sizes);
		} else {
			return getRadioButtonsString(sizes);
		}
	}
	
	function getRadioButtonsString(sizes) {
		var sizesString = '';
		for (var i = 0; i < sizes.length; i++) {
			var checkedString = '';
			if (i === 0) {
				checkedString = 'checked';
			}
			var toAppend = "<input type='radio' " + checkedString + " value='" + sizes[i] + "' name='product-size-radio' /> " + sizes[i] + " ";
			sizesString += toAppend;
		}
		return sizesString;
	}
	
	function getCheckBoxesString(sizes) {
		var sizesString = '';
		var allSizes = ['XS', 'S', 'M', 'L', 'XL', 'XXL', 'XXXL'];
		for (var i = 0; i < allSizes.length; i++) {
			var checkedString = '';
			if (contains(sizes, allSizes[i])) {
				checkedString = 'checked';
			}
			var toAppend = "<input type='checkbox' " + checkedString + " value='" + allSizes[i] + "' name='product-size-radio' /> " + allSizes[i] + " ";
			sizesString += toAppend;
		}
		return sizesString;
	}
	
	function contains(a, obj) {
	    var i = a.length;
	    while (i--) {
	       if (a[i] === obj) {
	           return true;
	       }
	    }
	    return false;
	}
	
	$(".registration-form-error-close-button-link").click(function() {
		$(".registration-form-error-area").css({"display" : "none"});
		return false;
	});
	
	$("#sign-up-form").submit(function() {
		
		// Validate from data before sending it to server
		var firstName = $("#reg-form-first-name").val();
		var lastName = $("#reg-form-last-name").val();
		var email = $("#reg-form-email").val();
		var password = $("#reg-form-password").val();
		var confirmPassword = $("#reg-form-confirm-password").val();
		
		var nameRegex = /^([a-zA-Zа-яА-Я]{3,30})$/;
		var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var passwordRegex = /^([a-zA-Z0-9_-]{6,30})$/;
		
		if (!nameRegex.test(firstName)) {
			printErrorToRegistrationForm("First name should be at least 3 characters in length and contain only latin symbols!");
			return false;
		}
		if (!nameRegex.test(lastName)) {
			printErrorToRegistrationForm("Last name should be at least 3 characters in length and contain only latin symbols!");
			return false;
		}
		
		if (!emailRegex.test(email)) {
			printErrorToRegistrationForm("Email is incorrect.");
			return false;
		}
		
		if (!passwordRegex.test(password)) {
			printErrorToRegistrationForm("Password cannot be less than 6 characters and should contain only following: latin characters, numbers and symbols \"_\", \"-\".");
			return false;
		}
		
		if (password !== confirmPassword) {
			printErrorToRegistrationForm("Passwords do not match!");
			return false;
		}
		
		// Everything is fine here
		return true;
	});
	
	$(".page-message-close-link").click(function() {
		var target = $(this).attr("data-close-target");
		$("#" + target).remove();
		return false;
	});
	
	function printErrorToRegistrationForm(message) {
		$(".registration-form-error-area").css({"display" : "block"});
		$(".registration-form-error-message").text(message);
	}
	
	$(".dec-sign-button").click(function() { 
		var productHash = $(this).attr("data-product-item-hash");
		var id_product = $("#row-for-product-"+productHash).attr("data-product-id-item");
		var size = $("#product-size-item-" + productHash).text();
		$.ajax({
			type: "POST",
			url: $("#global-context-path").val() + "/changed",
			data: {"id_product": id_product, "size" : size, "action" : "decrement"},
			success: function(data) {
				if (parseInt($("#cart-size-span").text()) === 1) {
					$("#cart-content").text("Your cart is empty.");
					$("#user-table").remove();
					$("#checkout-cont").remove();
					$("#some-text-style").remove();
					$("#container-for-cart").append("<p id='some-text-style'> No items in cart. </p>");
				} else {
					var oldValue = parseInt($("#cart-item-input-"+ productHash).val());
					var productPrice = $("#product-item-price-" + productHash).text();
					if (oldValue === 1) {
						$("#row-for-product-"+productHash).remove();
					} 
					if (oldValue > 0) {
						var newTotalCount = parseInt($("#cart-size-span").text()) - 1;
						var newValue = oldValue - 1;
						var newTotalPriceForRow = $("#total-price-for-position-" + productHash).text() - productPrice;
						var newTotalPrice = $("#cart-total-price-span").text() - productPrice;
					} else {
						newValue = 0;
					}
					$("#cart-size-span").text(newTotalCount);
					$("#cart-total-price-span").text(newTotalPrice.toFixed(2));
					$("#total-price-for-position-" + productHash).text(newTotalPriceForRow.toFixed(2));
					$("#cart-item-input-" + productHash).val(newValue);
				}
			},
			dataType: "json"
		});   
		return false;
		
	});
	
	$(".inc-sign-button").click(function() {
		var productHash = $(this).attr("data-product-item-hash");
		var id_product = $("#row-for-product-"+productHash).attr("data-product-id-item");
		var size = $("#product-size-item-" + productHash).text();
		var oldValue = parseInt($("#cart-item-input-"+ productHash).val());
		var newValue = oldValue + 1;
		var newTotalCount = parseInt($("#cart-size-span").text()) + 1;
		var productPrice = parseFloat($("#product-item-price-" + productHash).text());
		var newTotalPriceForRow = parseFloat($("#total-price-for-position-" + productHash).text()) + productPrice;
	    var newTotalPrice = parseFloat($("#cart-total-price-span").text()) + productPrice;
	    $.ajax({
			type: "POST",
			url: $("#global-context-path").val() + "/changed",
			data: {"id_product": id_product, "size" : size, "action" : "increment"},
			success: function(data) {
				$("#cart-size-span").text(newTotalCount);
				$("#cart-total-price-span").text(newTotalPrice.toFixed(2));
				$("#total-price-for-position-" + productHash).text(newTotalPriceForRow.toFixed(2));
				$("#cart-item-input-" + productHash).val(newValue);
			},
			dataType: "json"
		});   
		return false;
	});
	
	$('.go_to_page').click(function () {
        setParameter('page', $(this).text());
        return false;
    });
	
	function addParameterToURL(URLString, paramName, paramValue) {
	    if (URLString.indexOf(paramName + "=") >= 0) {
	        var prefix = URLString.substring(0, URLString.indexOf(paramName));
	        var suffix = URLString.substring(URLString.indexOf(paramName));
	        suffix = suffix.substring(suffix.indexOf("=") + 1);
	        suffix = (suffix.indexOf("&") >= 0) ? suffix.substring(suffix.indexOf("&")) : "";
	        URLString = prefix + paramName + "=" + paramValue + suffix;
	    }
	    else {
	        if (URLString.indexOf("?") < 0)
	            URLString += "?" + paramName + "=" + paramValue;
	        else
	            URLString += "&" + paramName + "=" + paramValue;
	    }
	    return URLString;
	}

	function setParameter(paramName, paramValue) {
	    window.location.href = addParameterToURL(window.location.href, paramName, paramValue);
	}
	
	$(".delete-position-from-table").click(function() {
		var productHash = $(this).attr("data-product-item-hash");
		var newTotalPrice = $("#cart-total-price-span").text() - $("#total-price-for-position-" + productHash).text();
		var newTotalCount = $("#cart-size-span").text() - $("#cart-item-input-" + productHash).val();
		var id_product = $("#row-for-product-"+productHash).attr("data-product-id-item");
		var size = $("#product-size-item-" + productHash).text();
		$.ajax({
			type: "POST",
			url: $("#global-context-path").val() + "/changed",
			data: {"id_product": id_product, "size" : size, "action" : "delete"},
			success: function(data) {
				$("#row-for-product-" + productHash).remove();
				$("#cart-size-span").text(newTotalCount);
				$("#cart-total-price-span").text(newTotalPrice.toFixed(2));
				if (parseInt($("#cart-size-span").text()) === 0) {
					$("#cart-content").text("Your cart is empty.");
					$("#user-table").remove();
					$("#checkout-cont").remove();
					$("#some-text-style").remove();
					$("#container-for-cart").append("<p id='some-text-style'> No items in cart. </p>");
				}
			},
			dataType: "json"
		});
		
		return false;
	});
	
});
