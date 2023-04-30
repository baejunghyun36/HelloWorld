import 'package:flutter/material.dart';

class Logo extends StatelessWidget {
  final double width;
  final double height;
  final double fontSize;

  const Logo({
    super.key,
    required this.width,
    required this.height,
    required this.fontSize,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              "HELLO",
              style: TextStyle(
                color: Theme.of(context).primaryColorLight,
                fontWeight: FontWeight.w900,
                fontSize: fontSize,
                decoration: TextDecoration.none,
              ),
            ),
            Text(
              "WORLD",
              style: TextStyle(
                color: Theme.of(context).secondaryHeaderColor,
                fontWeight: FontWeight.w900,
                fontSize: fontSize,
                decoration: TextDecoration.none,
              ),
            ),
          ],
        ),
        Image.asset('assets/images/Person.png', width: width, height: height),
      ],
    );
  }
}
