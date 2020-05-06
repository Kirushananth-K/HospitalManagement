$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateDoctorForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	$("#formDoctor").submit();
});
// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidDIDSave").val($(this).closest("tr").find('#hidDIDUpdate').val());
	$("#DoctorID").val($(this).closest("tr").find('td:eq(0)').text());
	$("#DoctorName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#Specialization").val($(this).closest("tr").find('td:eq(2)').text());
	$("#Contact").val($(this).closest("tr").find('td:eq(3)').text());
	$("#Address").val($(this).closest("tr").find('td:eq(4)').text());
});
// CLIENTMODEL=========================================================================
function validateDoctorForm() {
	// ID
	if ($("#DoctorID").val().trim() == "") {
		return "Insert Doctor ID.";
	}
	// NAME
	if ($("#DoctorName").val().trim() == "") {
		return "Insert Doctor Name.";
	}
	// Specialization-------------------------------
	if ($("#Specialization").val().trim() == "") {
		return "Insert Specialization.";
	}
	// Contact-------------------------------
	if ($("#Contact").val().trim() == "") {
		return "Insert Doctor Contact.";
	}
	// Address-------------------------------
	if ($("#Address").val().trim() == "") {
		return "Insert Doctor Address.";
	}
}

// DESCRIPTION------------------------
if ($("#DoctorDesc").val().trim() == "") {
	return "Insert Doctor Description.";
}
return true;

$.ajax({
	url : "DoctorAPI",
	type : type,
	data : $("#formDoctor").serialize(),
	dataType : "text",
	complete : function(response, status) {
		onDoctorSaveComplete(response.responseText, status);
	}
});

function onDoctorSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divDoctorGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidDIDSave").val("");
	$("#formDoctor")[0].reset();
}

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "DoctorAPI",
		type : "DELETE",
		data : "DID=" + $(this).data("DID"),
		dataType : "text",
		complete : function(response, status) {
			onDoctorDeleteComplete(response.responseText, status);
		}
	});
});

function onDoctorDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divDoctorGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}
