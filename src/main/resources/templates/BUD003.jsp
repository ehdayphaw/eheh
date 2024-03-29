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
		<form id="form"  action="addcourse" method="post" th:object="${courseBean}" onsubmit="return toSubmit()">
	        <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Course Registration</h2>
	        
	        <input type="hidden" name="action" th:field="*{action}" value="${courseBean.action}"/>
	        <div class="row mb-4">
	            <div class="col-md-2"></div>
	            <label for="id" class="col-md-2 col-form-label"> ID</label>
	            <div class="col-md-4">
	                <input type="text" class="form-control" id="id" th:field="*{id}" th:readonly="true">
	            	<div id="idError" style="color:red"></div>
	            </div>
	        </div>
	
	        <div class="row mb-4">
	            <div class="col-md-2"></div>
	            <label for="name" class="col-md-2 col-form-label">Name</label>
	            <div class="col-md-4">
	                <input type="text" class="form-control" id="name" th:field="*{name}">
	                <p style="color: red; " th:text="${nameError}" ></p> 
	            	<div id="nameError" style="color:red"></div>
	            </div>
	        </div>
	      
	       
	        <div class="row mb-4">
	            <div class="col-md-4"></div>
	
	            <div class="col-md-6">
	               <button type="submit" value="Save" class="btn btn-secondary col-md-2" data-bs-toggle="modal" data-bs-target="#exampleModal">Add</button>           
	                
	        </div>
	      </div>
	        </form>
	        <br>
	        
	        
	    </div>      
	</div>
</div>
<script>
    let courseName = document.getElementById("name");
    
    function toSubmit() {
        if (courseName.value === "") {
            loginValidate();
            return false;
        }
        return true;
    }

    courseName.onblur = function () {
        loginValidate();
    };
    courseName.onfocus = function () {
        document.getElementById("nameError").innerHTML = "";
        
    };

    function loginValidate() {
        if (courseName.value === "") {
            document.getElementById("nameError").innerHTML = "Course name is required";
        } 
    }
</script>
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