<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <link th:href="@{/resources/css/test.css}" rel="stylesheet" />
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="test.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    
        <title>Course Registration</title>
</head>

<body>
    <div id="testheader">
        <div class="container">
            <div class=row>        
                <div class="col-md-5 ">
            <a href="setupstudent"><h3>Student Registration</h3></a>
        </div>  
        <div class="col-md-6">
            <span>User : </span><span th:text="${session.loginName}"></span><br>
        	<span>Current Date : </span><span th:text="${#dates.format(new java.util.Date(), 'yyyy-MM-dd HH:mm')}"></span>
        </div>  
 
        <div class="col-md-1" >
            <a href="/"><input type="button" class="btn-basic" id="lgnout-button" value="Log Out"></a>
        </div>       
    </div>
</div>

</div>
    <!-- <div id="testsidebar">Hello World </div> -->
    <div class="container">
    <div class="sidenav">
        
        <button class="dropdown-btn" > Class Management <i class="fa fa-caret-down"></i></button>
        
            <div class="dropdown-container">
          <a href="setupaddcourse">Course Registration </a>
	        <a href="setupstudent">Student Registration </a>
	        <a href="showstudent">Student Search </a>
        </div>
        <a href="showuser">Users Management</a>
      </div>
      <div class="main_contents">
    <div id="sub_content">
        <form id="form"  action="updateuser" method="post" th:object="${userBean}" onsubmit="return validateForm();">	
			<h2 class="col-md-6 offset-md-2 mb-5 mt-4">User Registration</h2>
			<p class="col-md-6 offset-md-2 mb-5 mt-4" id="dataError" style="color:red">${emailError}</p>
            <input type="hidden" name="hiddenField" th:field="*{action}" value="${userBean.action}" >

            <div class="row mb-4">
                <div class="col-md-2"></div>
                
                <label for="id" class="col-md-2 col-form-label">UserId</label>
                 <div class="col-md-4">
                 	<input type="text" class="form-control" th:field="*{userId}" th:readonly="true"></input> 
					<!--  <label th:if="${#fields.hasErrors('bookTitle')}" th:errors="*{bookTitle}" style="color: red;">Error</label>-->
                 </div>
				
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="username" th:field="*{userName}" value="" onblur="validateUsername();" onfocus="clearUsernameError();" >
                	<div id="username-error" style="color:red;"></div>
                </div>
                
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label">Email</label>
                <div class="col-md-4">
                    <input type="email" class="form-control" id="email" th:field="*{userEmail}" value="" onblur="validateEmail();" onfocus="clearEmailError();" >
                	<div id="email-error" style="color:red;"></div>
                </div>              
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="Passowrd" class="col-md-2 col-form-label">Passowrd</label>
                <div class="col-md-4">                 
                    <div class="input-group">
					    <input type="password" class="form-control" id="pass" th:field="*{userPass}" value="" onblur="validatePassword();" onfocus="clearPasswordError();" >
					    <span class="input-group-text" style="width:40px;">
			                <i class="bi bi-eye-slash" id="togglePassword" style="margin-left:1px;"></i>
			            </span>
					</div>
                	
                	<div id="pass-error" style="color:red;"></div>
                </div>               
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="confirmPassword" class="col-md-2 col-form-label">Confirm Passowrd</label>
                <div class="col-md-4">
                	<div class="input-group">
					    <input type="password" class="form-control" id="confirmPassword" th:field="*{confirmPass}" value="" onblur="validateConfirmPassword();" onfocus="clearConfirmPasswordError();" >
					    <span class="input-group-text" style="width:40px;">
					        <i class="bi bi-eye-slash" id="toggleConfirm" style="margin-left:1px;"></i>
					    </span>
					</div>
                	<div id="confirmPassword-error" style="color:red;"></div>
                	
                	
                </div>                                
            </div>
           <div class="row mb-4">
			    <div class="col-md-2"></div>
			    <label for="userRole" class="col-md-2 col-form-label">User Role</label>
			    <div class="col-md-4">
			        <select class="form-select" aria-label="Education" id="userRole" th:field="*{userRole}">
			            <option value="Admin" th:selected="${userBean.userRole == 'Admin'}">Admin</option>
			            <option value="User" th:selected="${userBean.userRole == 'User'}">User</option>			            
			        </select>
			    </div>
			</div>
            	
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-6">
                    <button type="submit"  class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Add</button>         
            		<a href="showuser" class="btn btn-secondary col-md-2">Back</a>
            	</div>
            </div>
            </form>
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
            
            <script>
			    function showEncryptedPassword() {
			        var plainPassword = prompt("Enter the plain password:");
			        if (plainPassword !== null) {
			            // Perform client-side encryption (not recommended for production)
			            var encryptedPassword = btoa(plainPassword); // Base64 encoding for simplicity
			            document.getElementById('password').value = encryptedPassword;
			        }
			    }
			</script>
</body>

</html>

    


    
