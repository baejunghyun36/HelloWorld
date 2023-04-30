import 'package:flutter/material.dart';
import 'package:app/widgets/logo_widget/logo_widget.dart';

class Header extends StatefulWidget {
  const Header({Key? key}) : super(key: key);

  @override
  State<Header> createState() => _HeaderState();
}

class _HeaderState extends State<Header> {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 60,
      padding: const EdgeInsets.symmetric(horizontal: 8),
      margin: const EdgeInsets.only(top: 25),
      // color: Theme.of(context).primaryColorLight,
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          TextButton(
            onPressed: mvNotice,
            child: Icon(
              Icons.notifications_active_outlined,
              color: Colors.grey,
              size: 30,
            ),
          ),
        ],
      ),
    );
  }

  mvNotice() {
    Navigator.pushNamed(context, '/notice');
  }
}
