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
    	
    	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    
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
    	
    <form class="row g-3 mt-3 ms-2" th:action="@{/searchuser}" method="get">
    <div class="col-auto">	        
        <input type="search" class="form-control" id="inputUserId" placeholder="Enter your search query" name="search">
    </div>
    <div class="col-auto">
        <input type="hidden" name="action" value="Active">
        <button type="submit" class="btn btn-primary mb-3" value="Search">Search</button>
    </div>
    <div class="col-auto">
        <a th:href="@{/setupuserbyadmin}">
            <button type="button" class="btn btn-secondary">Add</button>
        </a>
    </div>
    <div class="col-auto">
        <button type="reset" class="btn btn-danger mb-3" value="clear">Reset</button>
    </div>
</form>

<table class="table table-striped" id="stduentTable">
    <thead>
        <tr>
           
            <th scope="col">User ID</th>
            <th scope="col">User Name</th>
            <th scope="col">Details</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="user, iterStat : ${list}">
           
            <td th:text="${user.userId}"></td>
            <td th:text="${user.userName}"></td>
            <td>
                <form th:action="@{/setupupdateuser}" method="get">
                    <input type="hidden" name="id" th:value="${user.userId}" />
                    <button type="submit" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">Update</button>
                </form>
            </td>
            <td>
                <form th:action="@{/deleteuser}" method="get">
                    <input type="hidden" name="id" th:value="${user.userId}" />
                    <button type="submit" class="btn btn-secondary mb-3" data-bs-toggle="modal" data-bs-target="#exampleModal">Delete</button>
                </form>
            </td>
        </tr>
    </tbody>
</table>
      
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Student Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        
                         <h5 style="color: rgb(127, 209, 131);">Are you sure want to delete !</h5>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>
    
                    </div>
                </div>
            </div>
        </div>
        
    </div>
</div>
</div>

<script>
	function prepareSearch() {
	    const userId = document.getElementById("inputUserId").value;
	    const userName = document.getElementById("inputUserName").value;
	    const searchQuery = userId || userName; // Use the non-empty field
	
	    // Add percent symbol to search query if it doesn't contain it
	    const formattedSearchQuery = searchQuery.includes('%') ? searchQuery : searchQuery + '%';
	
	    document.getElementById("searchQuery").value = formattedSearchQuery;
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

    


    

