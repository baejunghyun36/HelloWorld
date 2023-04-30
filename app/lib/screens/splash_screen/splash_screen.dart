import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({Key? key}) : super(key: key);

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    Timer(
        const Duration(seconds: 2),
            () => Navigator.pushReplacementNamed(
            context, '/before-login'
        )
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFFFFFFFF),
      body: Center(
          child:
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("HELLO", style: TextStyle(color: Theme.of(context).primaryColorLight, fontWeight: FontWeight.w900, fontSize: 70,),),
              Text("WORLD", style: TextStyle(color: Theme.of(context).secondaryHeaderColor, fontWeight: FontWeight.w900, fontSize: 70,),),
            ],
          ),
      ),
    );
  }
}