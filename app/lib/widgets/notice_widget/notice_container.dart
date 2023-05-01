import 'package:flutter/material.dart';

class NoticeContainer extends StatefulWidget {
  const NoticeContainer({Key? key}) : super(key: key);

  @override
  State<NoticeContainer> createState() => _NoticeContainerState();
}

class _NoticeContainerState extends State<NoticeContainer> {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 70,
      width: 1000,
      margin: EdgeInsets.symmetric(
        horizontal: 10,
        vertical: 8,
      ),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(10),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.6),
            blurRadius: 4.0,
            spreadRadius: 0.0,
          ),
        ],
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Container(
            width: 50,
            height: 50,
            margin: EdgeInsets.symmetric(
              horizontal: 10,
            ),
            decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage('assets/images/no_image.jpg'),
                  fit: BoxFit.cover,
                ),
                borderRadius: BorderRadius.circular(100),
                border: Border.all(
                    color: Theme.of(context)
                        .secondaryHeaderColor
                        .withOpacity(0.3))),
          ),
          SizedBox(
            width: 5,
          ),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                "원송희 님이 댓글을 남겼습니다",
                style: TextStyle(
                  color: Theme.of(context).secondaryHeaderColor,
                  fontSize: 12,
                  fontWeight: FontWeight.normal,
                  decoration: TextDecoration.none,
                ),
              ),
              SizedBox(
                height: 10,
              ),
              Text(
                "2023-05-01 09:12",
                style: TextStyle(
                  color:
                      Theme.of(context).secondaryHeaderColor.withOpacity(0.5),
                  fontSize: 10,
                  fontWeight: FontWeight.normal,
                  decoration: TextDecoration.none,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
