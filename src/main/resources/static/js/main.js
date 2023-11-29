$(".show-password").hover(
	function() {
		$("#password").attr("type", "text").focus();
	},
	function() {
		$("#password").attr("type", "password");
	}
);

//remain scroll position after redirect
$(window).scroll(function() {
	sessionStorage.scrollTop = $(this).scrollTop();
});

$(document).ready(function() {
	if (sessionStorage.scrollTop !== "undefined") {
		$(window).scrollTop(sessionStorage.scrollTop);
	}
});

//DataTable plug-in
$(document).ready(function() {
	$('#sortableTable').DataTable(
		{
			columnDefs: [
				{
					orderable: false,
					targets: [8, 9]
				}
			],
			pageLength: 25,
			paging: true, // Enable paging
			lengthChange: true, // Show the page length dropdown
			searching: true, // Enable search box
			info: true, // Show information about the table
			ordering: true // Enable ordering
		});
	$('.dataTables_length').addClass('bs-select');

	//Category sorting datatable	
	$('#sortableCatTable').DataTable(
		{
			columnDefs: [
				{
					orderable: false,
					targets: [3, 4]
				}
			],
			pageLength: 25,
			paging: true, // Enable paging
			lengthChange: true, // Show the page length dropdown
			searching: true, // Enable search box
			info: true, // Show information about the table
			ordering: true // Enable ordering
		});
	$('.dataTables_length').addClass('bs-select');
});


$(document).ready(function() {

	$(".show-password").on("click", function() {
		let passwordInput = $("#password");
		let passwordType = passwordInput.attr("type");

		if (passwordType === "password") {
			passwordInput.attr("type", "text");
		} else {
			passwordInput.attr("type", "password");
		}
	});
});

