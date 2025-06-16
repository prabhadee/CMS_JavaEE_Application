$(document).ready(function(){

    $("#complaintForm").on("submit", function(e){

        $(".error").text('');
        $("input, textarea, select").removeClass("invalid");

        let isValid = true;

        let title = $("#title").val();
        if(title.length < 3){
            $("#titleError").text("Title must be at least 3 characters.");
            $("#title").addClass("invalid");
            isValid = false;
        }

        let description = $("#description").val();
        if(description.length < 10){
            $("#descriptionError").text("Description must be at least 10 characters.");
            $("#description").addClass("invalid");
            isValid = false;
        }

        let department = $("#department").val();
        if(department.length < 3 || !/^[A-Za-z\s]+$/.test(department)){
            $("#departmentError").text("Enter a valid department name (letters only, min 3 chars).");
            $("#department").addClass("invalid");
            isValid = false;
        }

        let priority = $("#priority").val();
        if(priority === ""){
            $("#priorityError").text("Please select a priority.");
            $("#priority").addClass("invalid");
            isValid = false;
        }

        if(!isValid){
            e.preventDefault();
        }

    });

});