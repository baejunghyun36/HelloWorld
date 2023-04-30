import 'package:flutter/material.dart';
import 'package:app/widgets/logo_widget/logo_widget.dart';
import 'package:pin_code_fields/pin_code_fields.dart';

class FindPassword extends StatefulWidget {
  const FindPassword({Key? key}) : super(key: key);

  @override
  State<FindPassword> createState() => _FindPasswordState();
}

class _FindPasswordState extends State<FindPassword> {
  String currentText = "";

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
                    ],
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                TextButton(
                  onPressed: authEmail,
                  child: Container(
                    width: 300,
                    height: 40,
                    alignment: Alignment.center,
                    child: Text(
                      "임시 비밀번호 발급받기",
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
                      onPressed: mvLogin,
                      child: Text(
                        "로그인",
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
        ),
      ),
    );
  }

  authEmail() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return Dialog(
          child: Container(
            height: 200,
            padding: EdgeInsets.symmetric(
              horizontal: 10,
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  "이메일로 전송된 10자리 코드를 입력해주세요",
                  textAlign: TextAlign.start,
                  style: TextStyle(
                    color: Theme.of(context).secondaryHeaderColor,
                    fontSize: 13,
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                PinCodeTextField(
                  pinTheme: PinTheme(
                    shape: PinCodeFieldShape.underline,
                    borderRadius: BorderRadius.circular(5),
                    fieldHeight: 30,
                    fieldWidth: 20,
                  ),
                  obscureText: true,
                  obscuringWidget: const Image(
                      image: AssetImage('assets/images/Person.png')),
                  blinkWhenObscuring: true,
                  animationType: AnimationType.fade,
                  appContext: context,
                  length: 10,
                  onChanged: (value) {
                    setState(
                      () {
                        currentText = value;
                      },
                    );
                  },
                  onCompleted: (v) {
                    // 여기서 인증 됐는지 안됐는지 판별해주기~~
                    if (v == "") {
                      print("인증되었습니다");
                    } else {
                      print("입력된 코드와 인증 코드가 다릅니다.");
                    }
                  },
                )
              ],
            ),
          ),
        );
      },
    );
  }


  mvLogin() {
    Navigator.pushNamed(context, '/before-login');
  }

  mvJoin() {
    Navigator.pushNamed(context, '/register-user');
  }
}
