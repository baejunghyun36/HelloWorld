import 'package:flutter/material.dart';
import 'package:app/widgets/logo_widget/logo_widget.dart';

class BeforeLogin extends StatefulWidget {
  const BeforeLogin({Key? key}) : super(key: key);

  @override
  State<BeforeLogin> createState() => _BeforeLoginState();
}

class _BeforeLoginState extends State<BeforeLogin> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFFFFFFFF),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Logo(width: 100, height: 200, fontSize: 50),
            TextButton(
              onPressed: mvLogin,
              child: Container(
                width: 300,
                height: 40,
                alignment: Alignment.center,
                child: Text(
                  "일반 로그인",
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
            TextButton(
              onPressed: loginWithGithub,
              child: Container(
                width: 300,
                height: 40,
                alignment: Alignment.center,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                      width: 25,
                      height: 25,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(100),
                        image: const DecorationImage(
                          image: NetworkImage(
                              "https://cdn.pixabay.com/photo/2022/01/30/13/33/github-6980894__480.png"),
                        ),
                      ),
                    ),
                    SizedBox(
                      width: 5,
                    ),
                    Text(
                      "Github으로 로그인",
                      style: TextStyle(
                        color: Color(0xFFFFFFFF),
                      ),
                    ),
                  ],
                ),
                decoration: BoxDecoration(
                  color: Color(0xFF000000),
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
              ],
            ),
          ],
        ),
      ),
    );
  }

  mvLogin() {
    Navigator.pushNamed(context, '/login');
  }

  loginWithGithub() {}

  mvFindPw() {
    Navigator.pushNamed(context, '/find-pw');
  }

  mvJoin() {
    Navigator.pushNamed(context, '/register-user');
  }
}
