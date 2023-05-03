import 'dart:async';

import 'package:flutter/material.dart';
import 'package:app/widgets/common/footer.dart';
import 'package:app/widgets/notice_widget/notice_container.dart';
import 'package:app/services/notice_api.dart';
import 'package:flutter_client_sse/flutter_client_sse.dart';

class Notice extends StatefulWidget {
  const Notice({Key? key}) : super(key: key);

  @override
  State<Notice> createState() => _NoticeState();
}

class _NoticeState extends State<Notice> {
  late dynamic notices;
  List<dynamic> temp = [];

  @override
  void initState() {
    super.initState();
    getNotice(
      success: (dynamic notice) {
        // print(SSEModel.fromData(notice));
        // StreamSubscription subscription = notice.listen((event) => print(event.data));
        // notice.listen((event) => print(event.data!));
        setState(() {
          notices = notice.listen((event) => temp.add(event.data!));
        });
        // notice.listen((event) {
        //   print(event.data!);
        //   setState(() {
        //     notices.add(event.data!);
        //   });
        // });
        // setState(() {
        //   notices = notice;
        // });
      },
      fail: (error) {
        print('알림 내역 호출 오류: $error');
      },
      // body: {"notifySeq": "6451e375cb61515a2c6a0819"},
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      child: Column(
        children: [
          SizedBox(
            height: 20,
          ),
          // for (var n in notices)
          //   Text("${n.toString()}", style: TextStyle(fontSize: 10),),
          Text("$temp",
              style: TextStyle(
                fontSize: 10,
              )),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                SizedBox(
                  height: 20,
                ),
                Text(
                  "알림",
                  style: TextStyle(
                    color: Theme.of(context).secondaryHeaderColor,
                    decoration: TextDecoration.none,
                    fontSize: 15,
                    fontWeight: FontWeight.normal,
                  ),
                ),
                SizedBox(
                  height: 15,
                ),
                Divider(
                  thickness: 1.5,
                  height: 0,
                ),
                Expanded(
                  child: SingleChildScrollView(
                    child: Column(
                      children: [
                        NoticeContainer(),
                        NoticeContainer(),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
          Footer(),
        ],
      ),
    );
  }
}
