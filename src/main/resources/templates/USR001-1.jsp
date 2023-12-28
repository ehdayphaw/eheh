
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
<link th:href="@{/resources/css/test.css}" rel="stylesheet" />
       <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    
        <title>Course Registration</title>
</head>

<body class="login-page-body">
    
    
    <div class="container">
  
    <div id="sub_content">
		<form id="form"  action="register" method="post" th:object="${userBean}" onsubmit="return validateForm();">	
			<h2 class="col-md-6 offset-md-2 mb-5 mt-4">User Registration</h2>
			<h4 class="col-md-6 offset-md-2 mb-5 mt-4" id="dataError" th:text="${emailError}" style="color:red;"></h4>
            
            <input type="hidden" name="hiddenField" th:field="*{action}" value="${userBean.action}" >

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="id" class="col-md-2 col-form-label">UserId</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="id" th:field="*{userId}" th:readonly="true">
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
					    <i class="bi bi-eye-slash" id="togglePassword"></i>
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
					    <i class="bi bi-eye-slash" id="togglePassword"></i>
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
                    	<select class="form-select" aria-label="Education" id="userRole" th:field="${userBean.userRole}">
						    <option th:value="User" th:selected="${userBean.userRole == 'User'}">User</option>
						</select>
                	</div>
            	</div>
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-6">
                    <button type="submit"  class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Add</button>         
            		<a href="/" class="btn btn-secondary col-md-2">Back</a>
            	</div>
            </div>
            </form>
            
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
			<script>
					const togglePassword = document.querySelector('#togglePassword');
					const passInput = document.querySelector('#pass');
					togglePassword.addEventListener('click', () => {
					    const type = passInput.getAttribute('type') === 'password' ? 'text' : 'password';
					    passInput.setAttribute('type', type);
					    togglePassword.classList.toggle('bi-eye');
					    togglePassword.classList.toggle('bi-eye-slash');
					});
					
					function clearPasswordError() {
					    var passwordErrorElement = document.getElementById("pass-error");
					    passwordErrorElement.innerText = "";
					}
					
			</script>
			
			<script>
				const toggleConfirmPassword = document.querySelector('#toggleConfirm');
				const confirmPassInput = document.querySelector('#confirmPassword');
				toggleConfirmPassword.addEventListener('click', () => {
				    const type = confirmPassInput.getAttribute('type') === 'password' ? 'text' : 'password';
				    confirmPassInput.setAttribute('type', type);
				    toggleConfirmPassword.classList.toggle('bi-eye');
				    toggleConfirmPassword.classList.toggle('bi-eye-slash');
				});
				
				function clearConfirmPasswordError() {
				    var confirmPasswordErrorElement = document.getElementById("confirmPassword-error");
				    confirmPasswordErrorElement.innerText = "";
				}
			</script>
            <script>
			    function validateForm() {
			        var username = document.getElementById("username").value;
			        var email = document.getElementById("email").value;
			        var pass = document.getElementById("pass").value;
			        var confirmPassword = document.getElementById("confirmPassword").value;
			        
			        
			        var usernameErrorElement = document.getElementById("username-error");
			        var emailErrorElement = document.getElementById("email-error");
			        var passErrorElement = document.getElementById("pass-error");
			        var confirmPasswordErrorElement = document.getElementById("confirmPassword-error");
			        
			
			        var isValid = true;
			
			        // Reset error messages
			        usernameErrorElement.innerText = "";
			        emailErrorElement.innerText = "";
			        passErrorElement.innerText = "";
			        confirmPasswordErrorElement.innerText = "";
			        
			
			     	
			        // Validate username
			        if (username.trim() === "") {
			            usernameErrorElement.innerText = "Username is required.";
			            isValid = false;
			        }
			
			        // Validate email using a custom JavaScript check
			        if (email.trim() === "") {
			            emailErrorElement.innerText = "Email is required.";
			            isValid = false;
			        } else if (email.indexOf("@") === -1) {
			            emailErrorElement.innerText = "Email format is wrong.";
			            isValid = false;
			        }
			
			        // Validate password
			        if (pass.trim() === "") {
			            passErrorElement.innerText = "Password is required.";
			            isValid = false;
			        } else if (pass.length < 8) {
			            passErrorElement.innerText = "Password must be at least 8 characters.";
			            isValid = false;
			        }
			
			        // Validate confirmPassword
			        if (confirmPassword.trim() === "") {
			            confirmPasswordErrorElement.innerText = "Confirm Password is required.";
			            isValid = false;
			        } else if (pass !== confirmPassword) {
			            confirmPasswordErrorElement.innerText = "Passwords do not match.";
			            isValid = false;
			        }			        
			
			        return isValid;
			    }			    
			
			    // Add similar functions for onblur and onfocus validation for other fields as needed
			    function validateUsername() {
			        var username = document.getElementById("username").value;
			        var usernameErrorElement = document.getElementById("username-error");
			
			        // Reset the error message
			        usernameErrorElement.innerText = "";
			
			        if (username.trim() === "") {
			            usernameErrorElement.innerText = "Username is required.";
			        }
			    }
			
			    function clearUsernameError() {
			        var usernameErrorElement = document.getElementById("username-error");
			        usernameErrorElement.innerText = "";
			    }
			
			    function validateEmail() {
			        var email = document.getElementById("email").value;
			        var emailErrorElement = document.getElementById("email-error");
			
			        // Reset the error message
			        emailErrorElement.innerText = "";
			
			        if (email.trim() === "") {
			            emailErrorElement.innerText = "Email is required.";
			        } else if (email.indexOf("@") === -1) {
			            emailErrorElement.innerText = "Email format is wrong.";
			        }
			    }
			
			    function clearEmailError() {
			        var emailErrorElement = document.getElementById("email-error");
			        emailErrorElement.innerText = "";
			    }
			
			    function validatePassword() {
			        var password = document.getElementById("pass").value;
			        var passwordErrorElement = document.getElementById("pass-error");
			
			        // Reset the error message
			        passwordErrorElement.innerText = "";
			
			        if (password.trim() === "") {
			            passwordErrorElement.innerText = "Password is required.";
			        }
			    }
			
			    function clearPasswordError() {
			        var passwordErrorElement = document.getElementById("pass-error");
			        passwordErrorElement.innerText = "";
			    }
			
			    function validateConfirmPassword() {
			        var password = document.getElementById("pass").value;
			        var confirmPassword = document.getElementById("confirmPassword").value;
			        var confirmPasswordErrorElement = document.getElementById("confirmPassword-error");
			
			        // Reset the error message
			        confirmPasswordErrorElement.innerText = "";
			
			        if (confirmPassword.trim() === "") {
			            confirmPasswordErrorElement.innerText = "Confirm Password is required.";
			        } else if (password !== confirmPassword) {
			            confirmPasswordErrorElement.innerText = "Passwords do not match.";
			        }
			    }
			
			    function clearConfirmPasswordError() {
			        var confirmPasswordErrorElement = document.getElementById("confirmPassword-error");
			        confirmPasswordErrorElement.innerText = "";
			    }
			    
			 
			</script>
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