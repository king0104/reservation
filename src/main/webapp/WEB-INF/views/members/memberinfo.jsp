<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>회원 가입폼 </title>
</head>
<body>
<div>
    <div>
        <h1>회원정보</h1>
        <p>로그인한 회원 정보를 표기합니다.</p>
    </div>

    <div>
        <label>id : </label>
        ${user.id}
    </div>
    <div>
        <label>이름 : </label>
        ${user.name}
    </div>
    <div>
        <label>암호 : </label>
        ${user.password}
    </div>
    <div>
        <label>이메일 : </label>
        ${user.email}
    </div>
    <div>
        <label>전화번호 : </label>
        ${user.phone}
    </div>
    <div>
        <label>등록일 : </label>
        ${user.createDate}
    </div>
    <div>
        <label>수정일 : </label>
        ${user.modifyDate}
    </div>

</div>
</body>