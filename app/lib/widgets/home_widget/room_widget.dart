import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class MyRoom extends StatefulWidget {
  const MyRoom({Key? key}) : super(key: key);

  @override
  State<MyRoom> createState() => _MyRoomState();
}

class _MyRoomState extends State<MyRoom> {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(20),
      child: SvgPicture.asset(
        'assets/images/background/room1.svg',
        height: 280,
      ),
    );
  }
}
