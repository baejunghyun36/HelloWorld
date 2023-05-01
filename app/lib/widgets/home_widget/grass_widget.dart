import 'package:flutter/material.dart';

class Grass extends StatefulWidget {
  const Grass({Key? key}) : super(key: key);

  @override
  State<Grass> createState() => _GrassState();
}

class _GrassState extends State<Grass> {
  dynamic grassInfo = [
    {
      'createDate': '2023-04-16',
      'boardList': [4, 12]
    },
    {
      'createDate': '2023-04-17',
      'boardList': [11, 43, 174, 233]
    },
    {'createDate': '2023-04-18', 'boardList': []},
    {
      'createDate': '2023-04-19',
      'boardList': [1]
    },
    {
      'createDate': '2023-04-20',
      'boardList': [112]
    },
    {
      'createDate': '2023-04-21',
      'boardList': [114, 224]
    },
    {
      'createDate': '2023-04-22',
      'boardList': [333, 222]
    },
    {
      'createDate': '2023-04-23',
      'boardList': [444, 767, 235, 1522]
    },
    {
      'createDate': '2023-04-24',
      'boardList': [2, 3]
    },
  ];
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(
        vertical: 5,
        horizontal: 10,
      ),
      width: 1000,
      height: 145,
      decoration: BoxDecoration(
        border: Border.all(
          color: Theme.of(context).secondaryHeaderColor,
        ),
        borderRadius: BorderRadius.circular(10),
      ),
      child: Container(
        padding: EdgeInsets.all(
          10,
        ),
        child: Stack(
          children: [
            for (int i = 0; i < grassInfo.length; i++)
              Positioned(
                top: (i % 7) * 18 * 1.0,
                left: (i ~/ 7) * 20 * 1.0,
                child: Container(
                  width: 15,
                  height: 15,
                  decoration: BoxDecoration(
                    color: Theme.of(context)
                        .primaryColor
                        .withOpacity(
                        grassInfo[i]['boardList'].length >= 4
                            ? 1.0
                            : grassInfo[i]['boardList'].length *
                            0.25),
                    border: Border.all(
                      color: Theme.of(context).secondaryHeaderColor.withOpacity(0.6),
                    ),
                    borderRadius: BorderRadius.circular(
                      2.5,
                    ),
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
