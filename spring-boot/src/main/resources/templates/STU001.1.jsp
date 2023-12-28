<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <link th:href="@{/resources/css/test.css}" rel="stylesheet" />
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="test.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<title>Course Registration</title>
</head>
<body>
	<div id="testheader">
		<div class="container">
			<div class=row>
				<div class="col-md-5 ">
					<a href="setupstudentbyuser"><h3>Student Registration</h3></a>
				</div>
				<div class="col-md-6">
					<span>User : </span><span th:text="${session.loginName}"></span><br>
        			<span>Current Date : </span><span th:text="${#dates.format(new java.util.Date(), 'yyyy-MM-dd HH:mm')}"></span>
				</div>
				<div class="col-md-1">
					<a href="/"><input type="button" class="btn-basic" id="lgnout-button" value="Log Out"></a>
				</div>
			</div>
		</div>

	</div>
	<!-- <div id="testsidebar">Hello World </div> -->
	<div class="container">
		<div class="sidenav">
			<button class="dropdown-btn">Class Management <i class="fa fa-caret-down"></i> </button>

			<div class="dropdown-container">
	        <a href="setupstudentbyuser">Student Registration </a>
			</div>
		</div>
		<div class="main_contents">
			<div id="sub_content">
			
			
<form action="registerstudentbyuser" method="post" th:object="${studentBean}" enctype="multipart/form-data" onsubmit="return validateForm();">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Registration</h2>
            <p class="col-md-6 offset-md-2 mb-5 mt-4" id="gerror" style="color:red"></p>
            <input type="hidden" name="hiddenField" th:field="*{action}" value="${studentBean.action}" >
            
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="studentID" class="col-md-2 col-form-label">Student ID</label>
                <div class="col-md-4">
                	<input type="text" class="form-control" id="id" th:field="*{studentId}" th:readonly="true">
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                	<input type="text" class="form-control" id="name" th:field="*{studentName}" onblur="validateUsername();" onfocus="clearUsernameError();">
                    <div id="nameError" style="color:red"></div>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="dob" class="col-md-2 col-form-label">DOB</label>
                <div class="col-md-4">
                    <input type="date" class="form-control" id="dob" th:field="*{dob}" onblur="validateDob();" onfocus="clearDobError();">
                    <div id="dobError" style="color:red"></div>                   
                </div>
            </div>
            
            <fieldset class="row mb-4">
			    <div class="col-md-2"></div>
			    <legend class="col-form-label col-md-2 pt-0">Gender</legend>
			    <div class="col-md-4">
			        <div class="form-check-inline">
			            <label class="form-check-label">
			                <input type="radio" th:field="*{gender}" th:value="Male" id="gridRadios1"/> Male
			            </label>
			
			            <label class="form-check-label">
			                <input type="radio" th:field="*{gender}" th:value="Female" id="gridRadios2"/> Female
			            </label>
			        </div>
			    </div>
			</fieldset>
			
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="phone" class="col-md-2 col-form-label">Phone</label>
                <div class="col-md-4">
                	<input type="text" class="form-control" id="phone" value="+95 " th:field="*{studentPhone}" onblur="validatePhone();" onfocus="clearPhoneError();">
                    <div id="phoneError" style="color:red;"></div>
                </div>
            </div>
            
            
            <fieldset class="row mb-4">
			    <div class="col-md-2"></div>
			    <legend class="col-form-label col-md-2 pt-0">Attend</legend>
			    <div class="col-md-4">
			        <div class="form-check-inline" th:each="course : ${list}">
			            <input type="checkbox" class="form-check-input" th:field="${studentBean.attendlist}" th:value="${course.id}" id="course_${course.id}" />
			            <label class="form-check-label" th:for="'course_' + ${course.id}" th:text="${course.name}"></label>
			        </div>
			    </div>
			</fieldset>
            
            	<div class="row mb-4">
					    <div class="col-md-2"></div>
					    <label for="photo" class="col-md-2 col-form-label">Photo</label>
					    <div class="col-md-4">
					        <input type="file" class="form-control" id="photo" name="inputPhoto"  />
					    </div>
					</div>
				
            	<div class="row mb-4">
				    <div class="col-md-2"></div>
				    <label for="userRole" class="col-md-2 col-form-label">Education</label>
				    <div class="col-md-4">
				        <select class="form-select" aria-label="Education" id="edu" th:field="${studentBean.edu}">
				            <option value="Bachelor of Information Technology" th:selected="${studentBean.edu == 'Bachelor of Information Technology'}">Bachelor of Information Technology</option>
				            <option value="Diploma in IT" th:selected="${studentBean.edu == 'Diploma in IT'}">Diploma in IT</option>
				            <option value="Bachelor of Computer Science" th:selected="${studentBean.edu == 'Bachelor of Computer Science'}">Bachelor of Computer Science</option>
				        </select>
				    </div>
				</div>
				
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-4">
           
                    <button type="submit" class="btn btn-secondary col-md-2" data-bs-toggle="modal" onclick="return checkData();">
                        Add
                    </button>
                    
                    
                    
                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Student Registration</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <h5 style="color: rgb(127, 209, 131);">Registered Succesfully !</h5>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>
                               
                            </div>
                        </div>
                    </div>
            </div>
                </div>

    
            </div>
   
           </form>
           <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
           <script>
			    function validateForm() {
			        var username = document.getElementById("name").value;
			        var dob = document.getElementById("dob").value;	
			        var phone = document.getElementById("phone").value;
			        var usernameErrorElement = document.getElementById("nameError");
			        var dobErrorElement = document.getElementById("dobError");
			        var phoneErrorElement = document.getElementById("phoneError");
			        
			
			        var isValid = true;
			
			        // Reset error messages
			        usernameErrorElement.innerText = "";
			        dobErrorElement.innerText = "";
			        phoneErrorElement.innerText = "";
			        
			
			     	
			        // Validate username
			        if (username.trim() === "") {
			            usernameErrorElement.innerText = "Username is required.";
			            isValid = false;
			        }
			
			     // Validate username
			        if (dob.trim() === "") {
			        	dobErrorElement.innerText = "DOB is required.";
			            isValid = false;
			        }
			 		        
				
			        if (phone.trim() === "") {
			        	phoneErrorElement.innerText = "Phone is required.";
			            isValid = false;
			        }else if (phone.length > 9 ) {
			        	phoneErrorElement.innerText = "Phone number is must be 9 numbers.";
			            isValid = false;
			        }
			     
			        return isValid;
			    }			    
			
			    // Add similar functions for onblur and onfocus validation for other fields as needed
			    function validateUsername() {
			        var username = document.getElementById("name").value;
			        var usernameErrorElement = document.getElementById("nameError");
			
			        // Reset the error message
			        usernameErrorElement.innerText = "";
			
			        if (username.trim() === "") {
			            usernameErrorElement.innerText = "Username is required.";
			        }
			    }
			
			    function clearUsernameError() {
			        var usernameErrorElement = document.getElementById("nameError");
			        usernameErrorElement.innerText = "";
			    }
			
				////////////////////////////////////////////////////////////////////////////
			    function validateDob() {
			        var dob = document.getElementById("dob").value;
			        var dobErrorElement = document.getElementById("dobError");
			
			        // Reset the error message
			       	dobErrorElement.innerText = "";
			
			        if (dob.trim() === "") {
			        	dobErrorElement.innerText = "Dob is required.";
			        }
			    }
			
			    function clearDobError() {
			        var dobErrorElement = document.getElementById("dobError");
			        dobErrorElement.innerText = "";
			    }
			    /////////////////////////////////////////////////////////////////////////////
			    function validatePhone() {
			        var phone = document.getElementById("phone").value;
			        var phoneErrorElement = document.getElementById("phoneError");
			
			        // Reset the error message
			        phoneErrorElement.innerText = "";
			
			        if (phone.trim() === "") {
			            phoneErrorElement.innerText = "Phone is required.";
			        }
			    }
			
			    function clearPhoneError() {
			        var phoneErrorElement = document.getElementById("phoneError");
			        phoneErrorElement.innerText = "";
			    }
					 
			</script>
    </div>
</div>
</div>
        <div id="testfooter">
            <span>Copyright &#169; ACE Inspiration 2022</span>
        </div>
        <script>
            /* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
            var dropdown = document.getElementsByClassName("dropdown-btn");
            var i;
            
            for (i = 0; i < dropdown.length; i++) {
              dropdown[i].addEventListener("click", function() {
              this.classList.toggle("active");
              var dropdownContent = this.nextElementSibling;
              if (dropdownContent.style.display === "block") {
              dropdownContent.style.display = "none";
              } else {
              dropdownContent.style.display = "block";
              }
              });
            }
            </script>
</body>

</html>