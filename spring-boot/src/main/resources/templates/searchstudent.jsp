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
    <div class="col-md-1">
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
	    <form class="row g-3 mt-3 ms-2" th:action="@{/searchstudent}" method="get">
		    <!-- ... other form elements ... -->
		    <div class="col-auto">
		        <input type="search" class="form-control" placeholder="Search by ID OR Name" name="search">
		    </div>
		    <div class="col-auto">
		        <!-- Add the 'action' parameter to the form -->
		        <input type="hidden" name="action" value="Active">
		        <button type="submit" class="btn btn-primary mb-3" value="search">Search</button>
		    </div>
		    <div class="col-auto">
		        <button type="reset" class="btn btn-danger mb-3" value="clear">Reset</button>
		    </div>
		</form>
	      
	<div>
	
      <table class="table table-striped" id="stduentTable">
        <thead>
          <tr>
            <th scope="col">No</th>
            <th scope="col">Student ID</th>
            <th scope="col">Name</th>
            <!--  <th scope="col">Course Name</th>-->
            <th scope="col">Details</th>
          </tr>
        </thead>       
        
        <tbody>
		    <tr th:each="stu, iterStat : ${list}">
		        <th scope="row" th:text="${iterStat.index + 1}"></th>
		        <td th:text="${stu.studentId}" />
		        <td th:text="${stu.studentName}" />
		
		        <td>
		            <span th:each="course, loopStat : ${stu.attend}">
		                <span th:text="${course.name}"></span>
		                <span th:if="${!loopStat.last}">,</span>
		            </span>
		        </td>
		
		        <td>
		            <form th:action="@{/setupupdatestudent}" method="get">
		                <input type="hidden" name="id" th:value="${stu.studentId}" />
		                <button type="submit" class="btn btn-success">Update</button>
		            </form>
		        </td>
		
		        <td>
		            <form th:action="@{/deletestudent}" method="get">
		                <input type="hidden" name="id" th:value="${stu.studentId}" />
		                <button type="submit" class="btn btn-secondary mb-3" data-bs-toggle="modal"
		                    data-bs-target="#exampleModal">Delete</button>
		            </form>
		        </td>
		    </tr>
		</tbody>
      </table> 
      
      <h3 style="color: grey; text-align:50%;margin-top: 100px;margin-left:30%;" th:text="${msg}" ></h3>
    </div>
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



