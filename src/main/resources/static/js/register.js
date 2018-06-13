// JavaScript Document

/*ͨ��ID��ȡHTML�����ͨ�÷�����ʹ��$���溯������*/
function $(elementId) {
    return document.getElementById(elementId);
}

/*��������ͨ��֤�û����ı���ʱ����ʾ�ı�����ʽ*/
function userNameFocus() {
    var userNameId = $("userNameId");
    userNameId.className = "import_prompt";
    userNameId.innerHTML = "1������ĸ�����֡��»��ߡ��㡢�������<br/>2��ֻ�������֡���ĸ��ͷ���β���ҳ���Ϊ4-18";
}

/*������뿪ͨ��֤�û����ı���ʱ����ʾ�ı�����ʽ*/
function userNameBlur() {
    var userName = $("userName");
    var userNameId = $("userNameId");
    var reg = /^[0-9a-zA-Z][0-9a-zA-Z_.-]{2,16}[0-9a-zA-Z]$/;
    if (userName.value == "") {
        userNameId.className = "error_prompt";
        userNameId.innerHTML = "ͨ��֤�û�������Ϊ�գ�������ͨ��֤�û���";
        return false;
    }
    if (reg.test(userName.value) == false) {
        userNameId.className = "error_prompt";
        userNameId.innerHTML = "1������ĸ�����֡��»��ߡ��㡢�������<br/>2��ֻ�������֡���ĸ��ͷ���β���ҳ���Ϊ4-18";
        return false;
    }
    userNameId.className = "ok_prompt";
    userNameId.innerHTML = "ͨ��֤�û���������ȷ";
    return true;
}

/*�������������ı���ʱ����ʾ�ı�����ʽ*/
function pwdFocus() {
    var pwdId = $("pwdId");
    pwdId.className = "import_prompt";
    pwdId.innerHTML = "���볤��Ϊ6-16";
}

/*������뿪�����ı���ʱ����ʾ�ı�����ʽ*/
function pwdBlur() {
    var pwd = $("pwd");
    var pwdId = $("pwdId");
    if (pwd.value == "") {
        pwdId.className = "error_prompt";
        pwdId.innerHTML = "���벻��Ϊ�գ�����������";
        return false;
    }
    if (pwd.value.length < 6 || pwd.value.length > 16) {
        pwdId.className = "error_prompt";
        pwdId.innerHTML = "���볤��Ϊ6-16";
        return false;
    }
    pwdId.className = "ok_prompt";
    pwdId.innerHTML = "����������ȷ";
    return true;
}


/*������뿪�ظ������ı���ʱ����ʾ�ı�����ʽ*/
function repwdBlur() {
    var repwd = $("repwd");
    var pwd = $("pwd");
    var repwdId = $("repwdId");
    if (repwd.value == "") {
        repwdId.className = "error_prompt";
        repwdId.innerHTML = "�ظ����벻��Ϊ�գ����ظ���������";
        return false;
    }
    if (repwd.value != pwd.value) {
        repwdId.className = "error_prompt";
        repwdId.innerHTML = "������������벻һ�£�����������";
        return false;
    }
    repwdId.className = "ok_prompt";
    repwdId.innerHTML = "��������������ȷ";
    return true;
}

/*���������ǳ��ı���ʱ����ʾ�ı�����ʽ*/
function nickNameFocus() {
    var nickNameId = $("nickNameId");
    nickNameId.className = "import_prompt";
    nickNameId.innerHTML = "1���������֡���ĸ�����֡��»����Լ�@!#$%&*�����ַ�<br/>2������Ϊ4��20���ַ�<br/>3��һ������ռ�����ַ�";
}

/*������뿪�ǳ��ı���ʱ����ʾ�ı�����ʽ*/
function nickNameBlur() {
    var nickName = $("nickName");
    var nickNameId = $("nickNameId");
    var k = 0;
    var reg = /^([\u4e00-\u9fa5]|\w|[@!#$%&*])+$/;   // ƥ���ǳ�
    var chinaReg = /[\u4e00-\u9fa5]/g;   //ƥ�������ַ�
    if (nickName.value == "") {
        nickNameId.className = "error_prompt";
        nickNameId.innerHTML = "�ǳƲ���Ϊ�գ��������ǳ�";
        return false;
    }
    if (reg.test(nickName.value) == false) {
        nickNameId.className = "error_prompt";
        nickNameId.innerHTML = "ֻ���ɺ��֡���ĸ�����֡��»����Լ�@!#$%&*�����ַ����";
        return false;
    }

    var len = nickName.value.replace(chinaReg, "ab").length;  //�������ַ�ת��Ϊ������ĸ���Լ����ַ�����
    if (len < 4 || len > 20) {
        nickNameId.className = "error_prompt";
        nickNameId.innerHTML = "1������Ϊ4��20���ַ�<br/>2��һ������ռ�����ַ�";
        return false;
    }

    nickNameId.className = "ok_prompt";
    nickNameId.innerHTML = "�ǳ�������ȷ";
    return true;
}

/*�������ڹ����ֻ����ı���ʱ����ʾ�ı�����ʽ*/
function telFocus() {
    var telId = $("telId");
    telId.className = "import_prompt";
    telId.innerHTML = "1���ֻ�������13��15��18��ͷ<br/>2���ֻ�������11λ�������";
}

/*������뿪�����ֻ����ı���ʱ����ʾ�ı�����ʽ*/
function telBlur() {
    var tel = $("tel");
    var telId = $("telId");
    var reg = /^(13|15|18)\d{9}$/;
    if (tel.value == "") {
        telId.className = "error_prompt";
        telId.innerHTML = "�����ֻ����벻��Ϊ�գ�����������ֻ�����";
        return false;
    }
    if (reg.test(tel.value) == false) {
        telId.className = "error_prompt";
        telId.innerHTML = "�����ֻ��������벻��ȷ������������";
        return false;
    }
    telId.className = "ok_prompt";
    telId.innerHTML = "�����ֻ�����������ȷ";
    return true;
}


/*�������ڱ��������ı���ʱ����ʾ�ı�����ʽ*/
function emailFocus() {
    var emailId = $("emailId");
    emailId.className = "import_prompt";
    emailId.innerHTML = "�����������õĵ�������";
}

/*������뿪���������ı���ʱ����ʾ�ı�����ʽ*/
function emailBlur() {
    var email = $("email");
    var emailId = $("emailId");
    var reg = /^\w+@\w+(\.[a-zA-Z]{2,3}){1,2}$/;
    if (email.value == "") {
        emailId.className = "error_prompt";
        emailId.innerHTML = "�������䲻��Ϊ�գ������뱣������";
        return false;
    }
    if (reg.test(email.value) == false) {
        emailId.className = "error_prompt";
        emailId.innerHTML = "���������ʽ����ȷ������������";
        return false;
    }
    emailId.className = "ok_prompt";
    emailId.innerHTML = "��������������ȷ";
    return true;
}

/*���ύʱ��֤�������������Ч��*/
function checkForm() {
    var flagUserName = userNameBlur();
    var flagPwd = pwdBlur();
    var flagRepwd = repwdBlur();
    var flagNickName = nickNameBlur();
    var flagTel = telBlur();
    var flagEmail = emailBlur();

    userNameBlur();
    pwdBlur();
    repwdBlur();
    nickNameBlur();
    telBlur();
    emailBlur();

    if (flagUserName == true && flagPwd == true && flagRepwd == true && flagNickName == true && flagTel == true & flagEmail == true) {
        return true;
    }
    else {
        return false;
    }

}



	