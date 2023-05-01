import 'package:flutter/material.dart';
import 'package:app/widgets/common/footer.dart';
import 'package:flutter/services.dart';
import 'package:app/widgets/mypage_widget/mypage_category_widget.dart';

class MyPage extends StatefulWidget {
  const MyPage({Key? key}) : super(key: key);

  @override
  State<MyPage> createState() => _MyPageState();
}

class _MyPageState extends State<MyPage> {
  @override
  Widget build(BuildContext context) {
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.manual,
        overlays: [SystemUiOverlay.bottom]);
    return Container(
      color: Colors.white,
      child: Column(
        children: [
          Stack(
            clipBehavior: Clip.none,
            children: [
              // 프로필 사진 Container
              Container(
                height: 200,
                width: 5000,
                color: Theme.of(context).primaryColorLight,
              ),
              Positioned(
                top: 150,
                left: 10,
                right: 10,
                child: Align(
                  alignment: Alignment.bottomCenter,
                  child: Container(
                    width: 280,
                    height: 120,
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(20),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.grey.withOpacity(0.6),
                          blurRadius: 4.0,
                          spreadRadius: 0.0,
                        ),
                      ],
                    ),
                  ),
                ),
              ),
              Positioned(
                top: 100,
                left: 50,
                right: 50,
                child: Align(
                  alignment: Alignment.bottomCenter,
                  child: Container(
                    width: 110,
                    height: 110,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(100),
                      border: Border.all(
                          color: Theme.of(context).primaryColorLight,
                          width: 2.5),
                      color: Colors.white,
                      image: DecorationImage(
                        image: AssetImage('assets/images/no_image.jpg'),
                        fit: BoxFit.cover,
                      ),
                    ),
                  ),
                ),
              ),
              Positioned(
                top: 225,
                left: 50,
                right: 50,
                child: Align(
                  alignment: Alignment.center,
                  child: Text(
                    "원송희",
                    style: TextStyle(
                      color: Theme.of(context).secondaryHeaderColor,
                      fontSize: 16,
                      fontWeight: FontWeight.w600,
                      decoration: TextDecoration.none,
                    ),
                  ),
                ),
              ),
            ],
          ),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const SizedBox(
                  height: 100,
                ),
                MyPageCategory(
                    category: "내 정보 수정",
                    textColor: Theme.of(context).secondaryHeaderColor),
                // 메뉴 카테고리
                MyPageCategory(
                    category: "통계 보기",
                    textColor: Theme.of(context).secondaryHeaderColor),
                MyPageCategory(
                    category: "일촌 보기",
                    textColor: Theme.of(context).secondaryHeaderColor),
                MyPageCategory(
                    category: "알림 설정",
                    textColor: Theme.of(context).secondaryHeaderColor),
                MyPageCategory(
                    category: "로그아웃",
                    textColor: Theme.of(context).secondaryHeaderColor),
                MyPageCategory(
                    category: "회원 탈퇴하기",
                    textColor: Theme.of(context).disabledColor),
              ],
            ),
          ),
          const Footer(),
        ],
      ),
    );
  }
}
