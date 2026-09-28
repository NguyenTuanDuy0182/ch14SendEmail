<%@ include file="includes/header.html" %>
<h1>Join our email list</h1>
<p>To join our email list, enter your name and
    email address below.</p>
<p><i>${message}</i></p>
<form action="${pageContext.request.contextPath}/emailList" method="post">
    <input type="hidden" name="action" value="add">

    <label class="pad_top">Email:</label>
    <input type="email" name="email" value="${user.email}"
           required><br>
    <label class="pad_top">First Name:</label>
    <input type="text" name="firstName" value="${user.firstName}"
           required><br>

    <label class="pad_top">Last Name:</label>
    <input type="text" name="lastName" value="${user.lastName}"
           required><br>

    <label>&nbsp;</label>
    <input type="submit" value="Join Now" class="margin_left">
</form>
<%@ include file="includes/footer.jsp" %>
