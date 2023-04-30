import 'package:flutter/material.dart';
import 'package:app/widgets/logo_widget/logo_widget.dart';

class Login extends StatefulWidget {
  const Login({Key? key}) : super(key: key);

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> {
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
                  width: 100,
                  height: 200,
                  fontSize: 50,
                ),
                Container(
                  width: 300,
                  // color: Colors.red,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        "이메일",
                        textAlign: TextAlign.start,
                        style: TextStyle(
                          fontSize: 12,
                          color: Theme.of(context).primaryColorLight,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                      SizedBox(
                        height: 5,
                      ),
                      Container(
                        height: 30,
                        child: TextField(
                          decoration: InputDecoration(
                            border: OutlineInputBorder(
                              borderSide: BorderSide(
                                  color:
                                      Theme.of(context).secondaryHeaderColor),
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
                          color: Theme.of(context).primaryColorLight,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                      SizedBox(
                        height: 5,
                      ),
                      Container(
                        height: 30,
                        child: TextField(
                          decoration: InputDecoration(
                            border: OutlineInputBorder(
                              borderSide: BorderSide(
                                  color:
                                      Theme.of(context).secondaryHeaderColor),
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
                SizedBox(
                  height: 20,
                ),
                TextButton(
                  onPressed: login,
                  child: Container(
                    width: 300,
                    height: 40,
                    alignment: Alignment.center,
                    child: Text(
                      "로그인",
                      style: TextStyle(
                        color: Color(0xFFFFFFFF),
                      ),
                    ),
                    decoration: BoxDecoration(
                      color: Theme.of(context).disabledColor,
                      borderRadius: BorderRadius.circular(7),
                    ),
                  ),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    TextButton(
                      onPressed: mvFindPw,
                      child: Text(
                        "비밀번호 찾기",
                        style: TextStyle(
                          color: Theme.of(context).secondaryHeaderColor,
                          fontSize: 10,
                        ),
                      ),
                    ),
                    Text(
                      "|",
                      style: TextStyle(
                        color: Theme.of(context).secondaryHeaderColor,
                        fontSize: 10,
                      ),
                    ),
                    TextButton(
                      onPressed: mvJoin,
                      child: Text(
                        "회원가입",
                        style: TextStyle(
                          color: Theme.of(context).secondaryHeaderColor,
                          fontSize: 10,
                        ),
                      ),
                    ),
                    Text(
                      "|",
                      style: TextStyle(
                        color: Theme.of(context).secondaryHeaderColor,
                        fontSize: 10,
                      ),
                    ),
                    TextButton(
                      onPressed: loginWithGithub,
                      child: Text(
                        "Github으로 로그인",
                        style: TextStyle(
                          color: Theme.of(context).secondaryHeaderColor,
                          fontSize: 10,
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

  login() {
    Navigator.pushNamed(context, '/home');
  }

  loginWithGithub() {}

  mvFindPw() {
    Navigator.pushNamed(context, '/find-pw');
  }

  mvJoin() {
    Navigator.pushNamed(context, '/register-user');
  }
}
