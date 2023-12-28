<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <link rel="stylesheet" href="test.css">
    <title>Student Registration</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">  
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
    <link th:href="@{/resources/css/test.css}" rel="stylesheet" />
    <style>
    form i {
            margin-left: -25px;
            cursor: pointer;
        }
    </style>
</head>
<body class="login-page-body">
    <div class="login-page">
        <div class="form">
            <div class="login">
                <div class="login-header">
                    <h1>Welcome!</h1>                   
                    <h4  id="dataError" th:text="${error}" style="color:red;"></h4>
                    <h4 id="inactiveError" th:text="${inactiveError}" style="color:red;"></h4>
                </div>
            </div>
            <form  action="login" method="post" th:object="${userBean}" onsubmit="return validateForm();">
            	<input type="text" th:field="*{userName}" id="name" onblur="validateUsername();" onfocus="clearUsernameError();" />
                <div id="nameError" style="color:red"></div>
                <input type="password" th:field="*{userPass}" id="password" onblur="validatePassword();" onfocus="clearPasswordError();" />
                <i class="bi bi-eye-slash" id="togglePassword"></i>
                <div id="passError" style="color:red"></div>
                <button type="submit" value="Login">login</button>   
                <p class="message">Not registered? <a href="registerlink">Create an account</a></p>            
            </form>
        </div>
    </div>
    
    <script>
			    function validateForm() {
			        var username = document.getElementById("name").value;
			        var pass = document.getElementById("password").value;			        			        			        
			        var usernameErrorElement = document.getElementById("nameError");
			        var passErrorElement = document.getElementById("passError");
			        var dataError = document.getElementById("dataError");
			        var inactiveErrorElement = document.getElementById("inactiveError");
			        
			
			        var isValid = true;
			
			        // Reset error messages
			        usernameErrorElement.innerText = "";
			        passErrorElement.innerText = "";
			        dataError.innerText = "";
			        inactiveErrorElement.innerText = "";
			        
			
			     	
			        // Validate username
			        if (username.trim() === "") {
			            usernameErrorElement.innerText = "Username is required.";
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
			
			
			    function validatePassword() {
			        var password = document.getElementById("password").value;
			        var passwordErrorElement = document.getElementById("passError");
			
			        // Reset the error message
			        passwordErrorElement.innerText = "";
			
			        if (password.trim() === "") {
			            passwordErrorElement.innerText = "Password is required.";
			        }
			    }
			
			    function clearPasswordError() {
			        var passwordErrorElement = document.getElementById("passError");
			        passwordErrorElement.innerText = "";
			    }
					 
			</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	const togglePassword = document.querySelector('#togglePassword');
	const passInput = document.querySelector('#password');
	togglePassword.addEventListener('click', () => {
	    const type = passInput.getAttribute('type') === 'password' ? 'text' : 'password';
	    passInput.setAttribute('type', type);
	    togglePassword.classList.toggle('bi-eye');
	    togglePassword.classList.toggle('bi-eye-slash');
	});
	
	function clearPasswordError() {
	    var passwordErrorElement = document.getElementById("passError");
	    passwordErrorElement.innerText = "";
	}
</script>
</body>
</html>
