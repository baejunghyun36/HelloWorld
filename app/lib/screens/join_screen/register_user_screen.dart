import 'package:flutter/material.dart';
import 'package:app/widgets/logo_widget/logo_widget.dart';

class RegisterUser extends StatefulWidget {
  const RegisterUser({Key? key}) : super(key: key);

  @override
  State<RegisterUser> createState() => _RegisterUserState();
}

class _RegisterUserState extends State<RegisterUser> {
  var emailController = TextEditingController();
  var pwController = TextEditingController();
  var pw2Controller = TextEditingController();
  var nameController = TextEditingController();
  var nicknameController = TextEditingController();
  var phoneNumController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFFFFFFFF),
      body: Center(
        child: SafeArea(
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Logo(
                  width: 80,
                  height: 160,
                  fontSize: 40,
                ),
                Container(
                  width: 300,
                  // color: Colors.red,
                  child: Form(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          "이메일",
                          textAlign: TextAlign.start,
                          style: TextStyle(
                            fontSize: 12,
                            color: Theme
                                .of(context)
                                .primaryColorLight,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Container(
                          height: 30,
                          child: TextField(
                            controller: emailController,
                            decoration: InputDecoration(
                              border: OutlineInputBorder(
                                borderSide: BorderSide(
                                    color:
                                    Theme
                                        .of(context)
                                        .secondaryHeaderColor),
                                borderRadius: BorderRadius.circular(5),
                              ),
                              contentPadding: EdgeInsets.symmetric(
                                horizontal: 8,
                              ),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          "비밀번호",
                          textAlign: TextAlign.start,
                          style: TextStyle(
                            fontSize: 12,
                            color: Theme
                                .of(context)
                                .primaryColorLight,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Container(
                          height: 30,
                          child: TextField(
                            controller: pwController,
                            decoration: InputDecoration(
                              border: OutlineInputBorder(
                                borderSide: BorderSide(
                                    color:
                                    Theme
                                        .of(context)
                                        .secondaryHeaderColor),
                                borderRadius: BorderRadius.circular(5),
                              ),
                              contentPadding: EdgeInsets.symmetric(
                                horizontal: 8,
                              ),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          "비밀번호 확인",
                          textAlign: TextAlign.start,
                          style: TextStyle(
                            fontSize: 12,
                            color: Theme
                                .of(context)
                                .primaryColorLight,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Container(
                          height: 30,
                          child: TextField(
                            controller: pw2Controller,
                            decoration: InputDecoration(
                              border: OutlineInputBorder(
                                borderSide: BorderSide(
                                    color:
                                    Theme
                                        .of(context)
                                        .secondaryHeaderColor),
                                borderRadius: BorderRadius.circular(5),
                              ),
                              contentPadding: EdgeInsets.symmetric(
                                horizontal: 8,
                              ),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          "이름",
                          textAlign: TextAlign.start,
                          style: TextStyle(
                            fontSize: 12,
                            color: Theme
                                .of(context)
                                .primaryColorLight,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Container(
                          height: 30,
                          child: TextField(
                            controller: nameController,
                            decoration: InputDecoration(
                              border: OutlineInputBorder(
                                borderSide: BorderSide(
                                    color:
                                    Theme
                                        .of(context)
                                        .secondaryHeaderColor),
                                borderRadius: BorderRadius.circular(5),
                              ),
                              contentPadding: EdgeInsets.symmetric(
                                horizontal: 8,
                              ),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          "닉네임",
                          textAlign: TextAlign.start,
                          style: TextStyle(
                            fontSize: 12,
                            color: Theme
                                .of(context)
                                .primaryColorLight,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Container(
                          height: 30,
                          child: TextField(
                            controller: nicknameController,
                            decoration: InputDecoration(
                              border: OutlineInputBorder(
                                borderSide: BorderSide(
                                    color:
                                    Theme
                                        .of(context)
                                        .secondaryHeaderColor),
                                borderRadius: BorderRadius.circular(5),
                              ),
                              contentPadding: EdgeInsets.symmetric(
                                horizontal: 8,
                              ),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          "핸드폰 번호",
                          textAlign: TextAlign.start,
                          style: TextStyle(
                            fontSize: 12,
                            color: Theme
                                .of(context)
                                .primaryColorLight,
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Container(
                          height: 30,
                          child: TextField(
                            controller: phoneNumController,
                            decoration: InputDecoration(
                              border: OutlineInputBorder(
                                borderSide: BorderSide(
                                    color:
                                    Theme
                                        .of(context)
                                        .secondaryHeaderColor),
                                borderRadius: BorderRadius.circular(5),
                              ),
                              contentPadding: EdgeInsets.symmetric(
                                horizontal: 8,
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    TextButton(
                      onPressed: cancel,
                      child: Container(
                        width: 140,
                        height: 40,
                        alignment: Alignment.center,
                        child: Text(
                          "취소",
                          style: TextStyle(
                            color: Color(0xFFFFFFFF),
                          ),
                        ),
                        decoration: BoxDecoration(
                          color: Theme
                              .of(context)
                              .disabledColor,
                          borderRadius: BorderRadius.circular(7),
                        ),
                      ),
                    ),
                    TextButton(
                      onPressed: next,
                      child: Container(
                        width: 140,
                        height: 40,
                        alignment: Alignment.center,
                        child: Text(
                          "다음",
                          style: TextStyle(
                            color: Color(0xFFFFFFFF),
                          ),
                        ),
                        decoration: BoxDecoration(
                          color: Theme
                              .of(context)
                              .primaryColorLight,
                          borderRadius: BorderRadius.circular(7),
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  cancel() {
    Navigator.pushNamed(context, '/before-login');
  }

  next() {
    print(emailController.text);
    print(pwController.text);
    print(pw2Controller.text);
    print(nameController.text);
    print(nicknameController.text);
    print(phoneNumController.text);
    Navigator.pushNamed(context, '/register-character', arguments: {
    'email': emailController.text,
      'pw': pwController.text,
      'name': nameController.text,
      'nickname': nicknameController.text,
      'phoneNum': phoneNumController.text,

    },);
  }
}