$(function() {
	$("#studentList").tablesorter({
		headers: { 2: { sorter: false } },
		sortList: [ [0, 0] ],
		textExtraction: "complex"
	});
	$("a.deleteStudent").click(function() {
		$('.deleteForm', $(this).closest('td')).submit();
		return false;
	});
});
